package org.framework.mybatis.mapper.provider;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.scripting.xmltags.IfSqlNode;
import org.apache.ibatis.scripting.xmltags.MixedSqlNode;
import org.apache.ibatis.scripting.xmltags.SqlNode;
import org.apache.ibatis.scripting.xmltags.StaticTextSqlNode;
import org.apache.ibatis.scripting.xmltags.TrimSqlNode;
import org.apache.ibatis.scripting.xmltags.VarDeclSqlNode;

import tk.mybatis.mapper.mapperhelper.EntityHelper;
import tk.mybatis.mapper.mapperhelper.EntityHelper.EntityColumn;
import tk.mybatis.mapper.mapperhelper.MapperHelper;
import tk.mybatis.mapper.mapperhelper.MapperTemplate;

public class EntityInsertProvider extends MapperTemplate {
	public EntityInsertProvider(Class<?> mapperClass, MapperHelper mapperHelper) {
		super(mapperClass, mapperHelper);
	}

	public SqlNode insert(MappedStatement ms) {
		Class entityClass = getSelectReturnType(ms);
		List sqlNodes = new LinkedList();

		sqlNodes.add(new StaticTextSqlNode("INSERT INTO "
				+ tableName(entityClass)));

		Set<EntityColumn> columnList = EntityHelper.getColumns(entityClass);
		List ifNodes = new LinkedList();

		Boolean hasIdentityKey = Boolean.valueOf(false);

		for (EntityHelper.EntityColumn column : columnList) {
			if ((column.getSequenceName() != null)
					&& (column.getSequenceName().length() > 0)) {
				ifNodes.add(new StaticTextSqlNode(column.getColumn() + ","));
			} else if (column.isIdentity()) {
				ifNodes.add(new VarDeclSqlNode(column.getProperty() + "_cache",
						column.getProperty()));
				if (hasIdentityKey.booleanValue()) {
					if ((column.getGenerator() != null)
							&& (column.getGenerator().equals("JDBC"))) {
						continue;
					}
					throw new RuntimeException(ms.getId()
							+ "对应的实体类"
							+ entityClass.getCanonicalName()
							+ "中包含多个MySql的自动增长列,最多只能有一个!");
				}

				newSelectKeyMappedStatement(ms, column);
				hasIdentityKey = Boolean.valueOf(true);

				ifNodes.add(new StaticTextSqlNode(column.getColumn() + ","));
			} else if (column.isUuid()) {
				ifNodes.add(new VarDeclSqlNode(column.getProperty() + "_bind",
						getUUID()));
				ifNodes.add(new StaticTextSqlNode(column.getColumn() + ","));
			} else {
				ifNodes.add(getIfNotNull(column,
						new StaticTextSqlNode(column.getColumn() + ",")));
			}
		}

		sqlNodes.add(new TrimSqlNode(ms.getConfiguration(), new MixedSqlNode(
				ifNodes), "(", null, ")", ","));

		ifNodes = new LinkedList();

		for (EntityHelper.EntityColumn column : columnList) {
			if (column.isIdentity())
				ifNodes.add(new IfSqlNode(new StaticTextSqlNode("#{"
						+ column.getProperty() + "_cache },"), column
						.getProperty() + "_cache != null "));
			else {
				ifNodes.add(new IfSqlNode(new StaticTextSqlNode("#{"
						+ column.getProperty() + "},"), column.getProperty()
						+ " != null "));
			}
			if ((column.getSequenceName() != null)
					&& (column.getSequenceName().length() > 0))
				ifNodes.add(getIfIsNull(column, new StaticTextSqlNode(
						getSeqNextVal(column) + " ,")));
			else if (column.isIdentity())
				ifNodes.add(getIfCacheIsNull(column, new StaticTextSqlNode("#{"
						+ column.getProperty() + " },")));
			else if (column.isUuid()) {
				ifNodes.add(getIfIsNull(column, new StaticTextSqlNode("#{"
						+ column.getProperty() + "_bind },")));
			}
		}

		sqlNodes.add(new TrimSqlNode(ms.getConfiguration(), new MixedSqlNode(
				ifNodes), "VALUES (", null, ")", ","));
		return new MixedSqlNode(sqlNodes);
	}
}
