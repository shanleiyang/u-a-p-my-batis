package org.framework.mybatis.mapper.provider;

import org.apache.ibatis.mapping.MappedStatement;

import tk.mybatis.mapper.mapperhelper.EntityHelper;
import tk.mybatis.mapper.mapperhelper.MapperHelper;
import tk.mybatis.mapper.mapperhelper.MapperTemplate;

public class BatchProvider extends MapperTemplate {
	public BatchProvider(Class<?> mapperClass, MapperHelper mapperHelper) {
		super(mapperClass, mapperHelper);
	}

	public String batchDelete(MappedStatement ms) {
		Class entityClass = getSelectReturnType(ms);
		EntityHelper.EntityTable table = EntityHelper
				.getEntityTable(entityClass);

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
		sql.append(" in ");
		sql.append("<foreach collection=\"array\" index=\"index\" item=\"item\" open=\"(\" close=\")\" separator=\",\" >");
		sql.append("#{item}");
		sql.append("</foreach>");
		return sql.toString();
	}

	public String batchInsert(MappedStatement ms) {
		Class entityClass = getSelectReturnType(ms);
		EntityHelper.EntityTable table = EntityHelper
				.getEntityTable(entityClass);

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
		sql.append(") ");

		if ((ms.getConfiguration().getDatabaseId() != null)
				&& ("oracle".equals(ms.getConfiguration().getDatabaseId()
						.toLowerCase()))) {
			return dealInsertOracle(table, sql);
		}
		sql.append("values ");
		sql.append("<foreach collection=\"list\" item=\"item\" separator=\",\" >");
		sql.append("(");
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
		sql.append("</foreach>");
		return sql.toString();
	}

	public String batchUpdate(MappedStatement ms) {
		Class entityClass = getSelectReturnType(ms);
		EntityHelper.EntityTable table = EntityHelper
				.getEntityTable(entityClass);

		StringBuilder sql = new StringBuilder();

		if ((ms.getConfiguration().getDatabaseId() != null)
				&& ("oracle".equals(ms.getConfiguration().getDatabaseId()
						.toLowerCase())))
			sql.append("<foreach collection=\"list\" item=\"item\" open=\"begin\" close=\"end;\" >");
		else {
			sql.append("<foreach collection=\"list\" item=\"item\" separator=\";\" >");
		}
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
		sql.append("=");
		sql.append("#{item.").append(pkColumn.getProperty()).append("}");

		if ((ms.getConfiguration().getDatabaseId() != null)
				&& ("oracle".equals(ms.getConfiguration().getDatabaseId()
						.toLowerCase()))) {
			sql.append(";");
		}
		sql.append("</foreach>");
		return sql.toString();
	}

	private String dealInsertOracle(EntityHelper.EntityTable table,
			StringBuilder sql) {
		sql.append("<foreach collection=\"list\" item=\"item\" separator=\"union all\" >");
		sql.append("(select ");
		boolean first = true;
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
		sql.append(" from dual)");
		sql.append("</foreach>");
		return sql.toString();
	}
}
