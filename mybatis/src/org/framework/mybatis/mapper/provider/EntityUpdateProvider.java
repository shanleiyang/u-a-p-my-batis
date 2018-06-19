package org.framework.mybatis.mapper.provider;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.scripting.xmltags.MixedSqlNode;
import org.apache.ibatis.scripting.xmltags.SetSqlNode;
import org.apache.ibatis.scripting.xmltags.SqlNode;
import org.apache.ibatis.scripting.xmltags.StaticTextSqlNode;
import org.apache.ibatis.scripting.xmltags.WhereSqlNode;

import tk.mybatis.mapper.mapperhelper.EntityHelper;
import tk.mybatis.mapper.mapperhelper.EntityHelper.EntityColumn;
import tk.mybatis.mapper.mapperhelper.MapperHelper;
import tk.mybatis.mapper.mapperhelper.MapperTemplate;

public class EntityUpdateProvider extends MapperTemplate {
	public EntityUpdateProvider(Class<?> mapperClass, MapperHelper mapperHelper) {
		super(mapperClass, mapperHelper);
	}

	private String getDeleteSql(EntityHelper.EntityTable table, boolean isOracle) {
		EntityHelper.EntityColumn pkColumn = null;
		for (EntityHelper.EntityColumn column : table.getEntityClassColumns()) {
			if (column.isId()) {
				pkColumn = column;
				break;
			}
		}

		StringBuilder sql = new StringBuilder();
		sql.append("delete from ");
		sql.append(table.getName());
		sql.append(" where ");
		sql.append(pkColumn.getColumn());
		sql.append(" = ");
		sql.append("#{item.").append(pkColumn.getProperty()).append("}");
		if (isOracle) {
			sql.append(";");
		}
		return sql.toString();
	}

	private String getInsertSql(EntityHelper.EntityTable table, boolean isOracle) {
		StringBuilder sql = new StringBuilder();
		sql.append("insert into ");
		sql.append(table.getName());
		sql.append("(");
		boolean first = true;
		for (EntityHelper.EntityColumn column : table.getEntityClassColumns()) {
			if ((column.isId()) && (column.isUuid())) {
				continue;
			}
			if (!(first)) {
				sql.append(",");
			}
			sql.append(column.getColumn());
			first = false;
		}
		sql.append(") ").append("values ").append("(");
		first = true;
		for (EntityHelper.EntityColumn column : table.getEntityClassColumns()) {
			if ((column.isId()) && (column.isUuid())) {
				continue;
			}
			if (!(first)) {
				sql.append(",");
			}
			sql.append("#{item.").append(column.getProperty()).append("}");
			first = false;
		}
		sql.append(")");
		if (isOracle) {
			sql.append(";");
		}
		return sql.toString();
	}

	private String getUpdateSql(EntityHelper.EntityTable table, boolean isOracle) {
		StringBuilder sql = new StringBuilder();
		sql.append("update ");
		sql.append(table.getName());
		sql.append(" set ");

		boolean first = true;
		EntityHelper.EntityColumn pkColumn = null;
		for (EntityHelper.EntityColumn column : table.getEntityClassColumns()) {
			if ((!(first)) && (!(column.isId()))) {
				sql.append(",");
			}
			if (column.isId()) {
				pkColumn = column;
			} else {
				sql.append(column.getColumn());
				sql.append("=");
				sql.append("#{item.").append(column.getProperty()).append("}");
				first = false;
			}
		}
		sql.append(" where ");
		sql.append(pkColumn.getColumn());
		sql.append(" = ");
		sql.append("#{item.").append(pkColumn.getProperty()).append("}");
		if (isOracle) {
			sql.append(";");
		}
		return sql.toString();
	}

	public String save(MappedStatement ms) {
		boolean isOracle = false;

		if ((ms.getConfiguration().getDatabaseId() != null)
				&& ("oracle".equals(ms.getConfiguration().getDatabaseId()
						.toLowerCase()))) {
			isOracle = true;
		}

		Class entityClass = getSelectReturnType(ms);
		EntityHelper.EntityTable table = EntityHelper
				.getEntityTable(entityClass);

		StringBuilder sql = new StringBuilder();

		if (isOracle)
			sql.append("<foreach collection=\"list\" item=\"item\" open=\"begin\" close=\"end;\" >");
		else {
			sql.append("<foreach collection=\"list\" item=\"item\" separator=\";\" >");
		}

		sql.append("<if test=\"item.state == 1\"> ");
		sql.append(getInsertSql(table, isOracle));
		sql.append("</if>");

		sql.append("<if test=\"item.state == 2\"> ");
		sql.append(getDeleteSql(table, isOracle));
		sql.append("</if>");

		sql.append("<if test=\"item.state == 3\"> ");
		sql.append(getUpdateSql(table, isOracle));
		sql.append("</if>");
		sql.append("</foreach>");
		return sql.toString();
	}

	public SqlNode update(MappedStatement ms) {
		Class entityClass = getSelectReturnType(ms);
		List sqlNodes = new LinkedList();

		sqlNodes.add(new StaticTextSqlNode("UPDATE " + tableName(entityClass)));

		Set<EntityColumn> columnList = EntityHelper.getColumns(entityClass);
		List ifNodes = new LinkedList();
		for (EntityHelper.EntityColumn column : columnList) {
			if (!(column.isId())) {
				ifNodes.add(new StaticTextSqlNode(column.getColumn() + " = #{"
						+ column.getProperty() + "}, "));
			}
		}
		sqlNodes.add(new SetSqlNode(ms.getConfiguration(), new MixedSqlNode(
				ifNodes)));

		columnList = EntityHelper.getPKColumns(entityClass);
		List whereNodes = new LinkedList();
		boolean bool1 = true;

		for (EntityHelper.EntityColumn column : columnList) {
			whereNodes.add(getColumnEqualsProperty(column, bool1));
			bool1 = false;
		}
		sqlNodes.add(new WhereSqlNode(ms.getConfiguration(), new MixedSqlNode(
				whereNodes)));
		return new MixedSqlNode(sqlNodes);
	}
}