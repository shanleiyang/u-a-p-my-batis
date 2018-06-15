package org.framework.mybatis;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.executor.ErrorContext;
import org.apache.ibatis.executor.ExecutorException;
import org.apache.ibatis.executor.statement.BaseStatementHandler;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ParameterMode;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.property.PropertyTokenizer;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.type.TypeHandler;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.framework.mybatis.parser.Parser;
import org.framework.mybatis.parser.impl.AbstractParser;

@Intercepts({ @org.apache.ibatis.plugin.Signature(type = org.apache.ibatis.executor.statement.StatementHandler.class, method = "prepare", args = { Connection.class }) })
public class PageInterceptor implements Interceptor {
	private static Log log = LogFactory.getLog(PageInterceptor.class);

	private static String getParameterValue(Object obj) {
		String value = null;
		if (obj instanceof String) {
			value = "'" + obj.toString() + "'";
		} else if (obj instanceof Date) {
			DateFormat formatter = DateFormat.getDateTimeInstance(2, 2,
					Locale.CHINA);
			value = "'" + formatter.format(new Date()) + "'";
		} else if (obj != null) {
			value = obj.toString();
		} else {
			value = "";
		}

		return value;
	}

	public static boolean isEmpty(String dialect) {
		return ((dialect == null) || (dialect.trim().equals("")));
	}

	public static boolean notEmpty(String dialect) {
		return ((dialect != null) && (!(dialect.trim().equals(""))));
	}

	public static String showSql(Configuration configuration, BoundSql boundSql) {
		Object parameterObject = boundSql.getParameterObject();
		List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
		String sql = boundSql.getSql().replaceAll("[\\s]+", " ");
		if ((parameterMappings.size() > 0) && (parameterObject != null)) {
			TypeHandlerRegistry typeHandlerRegistry = configuration
					.getTypeHandlerRegistry();
			if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
				sql = sql.replaceFirst("\\?",
						getParameterValue(parameterObject));
			} else {
				MetaObject metaObject = configuration
						.newMetaObject(parameterObject);
				for (ParameterMapping parameterMapping : parameterMappings) {
					String propertyName = parameterMapping.getProperty();
					if (metaObject.hasGetter(propertyName)) {
						Object obj = metaObject.getValue(propertyName);
						sql = sql.replaceFirst("\\?", getParameterValue(obj));
					} else if (boundSql.hasAdditionalParameter(propertyName)) {
						Object obj = boundSql
								.getAdditionalParameter(propertyName);
						sql = sql.replaceFirst("\\?", getParameterValue(obj));
					}
				}
			}
		}
		return sql;
	}

	private boolean totalCount = false;

	private String generatePageSql(String sql, Map map, String dialect) {
		if (notEmpty(dialect)) {
			StringBuffer pageSql = new StringBuffer();
			int start;
			if (map.get("start") instanceof Integer)
				start = ((Integer) map.get("start")).intValue();
			else
				start = Integer.parseInt((String) map.get("start"));
			int end;
			if (map.get("limit") instanceof Integer)
				end = ((Integer) map.get("limit")).intValue();
			else {
				end = Integer.parseInt((String) map.get("limit"));
			}

			if ("mysql".equals(dialect)) {
				pageSql.append(sql);
				pageSql.append(" limit " + start + "," + end);
			} else if ("oracle".equals(dialect)) {
				pageSql.append("SELECT * FROM (SELECT TMP_TB.*,ROWNUM ROW_ID FROM (");
				pageSql.append(sql);

				if (sql.toUpperCase().indexOf("ORDER BY") == -1) {
					pageSql.append(" ORDER BY ROWNUM ASC");
				}
				pageSql.append(") TMP_TB WHERE ROWNUM<=");
				pageSql.append(start + end);
				pageSql.append(") WHERE ROW_ID>=");
				pageSql.append(start + 1);
			} else if ("db2".equals(dialect)) {
				pageSql.append("SELECT * FROM ( SELECT B.*, ROWNUMBER() OVER() AS RN FROM    (    ");
				pageSql.append(sql);
				pageSql.append(" ) AS B   ) AS A  ");
				pageSql.append(" WHERE A.RN BETWEEN " + (start + 1) + " AND "
						+ (start + end) + " ");
			} else if ("sqlserver".equals(dialect)) {
				sql = sql.toUpperCase();
				String tableName = "";
				int fIdx = sql.lastIndexOf("FROM") + 4;
				int wIdx = sql.lastIndexOf("WHERE");
				if (wIdx != -1)
					tableName = sql.substring(fIdx, wIdx).trim();
				else {
					tableName = sql.substring(fIdx).trim();
				}

				String pk = "select name from sysobjects where parent_obj=object_id('"
						+ tableName + "') and xtype='PK'";

				pageSql.append("SELECT * FROM ( SELECT B.*, ROW_NUMBER() OVER(ORDER BY (");
				pageSql.append(pk);
				pageSql.append(")) AS RN FROM    (    ");
				pageSql.append(sql);
				pageSql.append(" ) AS B   ) AS A  ");
				pageSql.append(" WHERE A.RN BETWEEN " + (start + 1) + " AND "
						+ (start + end) + " ");
			} else if ("hana".equals(dialect)) {
				pageSql.append("SELECT * FROM ( SELECT B.*, ROW_NUMBER() OVER() AS RN FROM    (    ");
				pageSql.append(sql);
				pageSql.append(" ) AS B   ) AS A  ");
				pageSql.append(" WHERE A.RN BETWEEN " + (start + 1) + " AND "
						+ (start + end) + " ");
			} else if ("postgresql".equals(dialect)) {
				pageSql.append(sql);
				pageSql.append(" limit " + end + " offset " + start);
			}
			return pageSql.toString();
		}
		return sql;
	}

	public Object intercept(Invocation ivk) throws Throwable {
		RoutingStatementHandler statementHandler = (RoutingStatementHandler) ivk
				.getTarget();
		BaseStatementHandler delegate = (BaseStatementHandler) ReflectHelper
				.getValueByFieldName(statementHandler, "delegate");
		MappedStatement mappedStatement = (MappedStatement) ReflectHelper
				.getValueByFieldName(delegate, "mappedStatement");

		List rms = mappedStatement.getResultMaps();
		ResultMap rm = ((rms != null) && (rms.size() > 0)) ? (ResultMap) rms
				.get(0) : null;
		String type = ((rm != null) && (rm.getType() != null)) ? rm.getType()
				.getName() : "";
		if (("java.util.HashMap".equals(type))
				|| ("java.util.Map".equals(type))) {
			ResultMapUtil.threadInfo.set(Boolean.valueOf(true));
		}

		BoundSql boundSql = delegate.getBoundSql();

		if (ivk.getTarget() instanceof RoutingStatementHandler) {
			String sqlTypeName = mappedStatement.getSqlCommandType().name();
			String sql = boundSql.getSql();

			if ((sqlTypeName.equals("SELECT"))
					&& (!(sql.startsWith("SELECT COUNT(*) FROM")))) {
				Object parameterObject = boundSql.getParameterObject();

				if ((parameterObject instanceof Map)
						&& (((Map) parameterObject).containsKey("limit"))) {
					Map paraMap = (Map) parameterObject;
					int pageSize = -1;
					if (paraMap.get("limit") instanceof Integer)
						pageSize = ((Integer) paraMap.get("limit")).intValue();
					else {
						pageSize = Integer.parseInt((String) paraMap
								.get("limit"));
					}

					if (pageSize != -1) {
						Connection connection = (Connection) ivk.getArgs()[0];

						String dbtype = DialectUtil.getDBTypeByDBId(connection
								.getMetaData().getDatabaseProductName());

						Boolean dataTotal = null;

						if (paraMap.get("dataTotal") != null) {
							if (paraMap.get("dataTotal") instanceof Boolean)
								dataTotal = (Boolean) paraMap.get("dataTotal");
							else {
								dataTotal = Boolean.valueOf(Boolean
										.parseBoolean((String) paraMap
												.get("dataTotal")));
							}
						}

						if (dataTotal != null) {
							if (dataTotal.booleanValue()) {
								if (("sqlserver".equals(dbtype))
										&& (sql.toUpperCase()
												.contains("ORDER BY"))) {
									sql = sql.trim();
									sql = "select top 100 percent"
											+ sql.substring(6);
								}

//								String countSql = "select count(0) from ("
//										+ sql + ")   tmp_count";
								Parser parser = AbstractParser.newParser(dbtype);
								String countSql = parser.getCountSql(sql);
								
								PreparedStatement countStmt = connection
										.prepareStatement(countSql);
								setParameters(countStmt, mappedStatement,
										boundSql, parameterObject);
								ResultSet rs = countStmt.executeQuery();
								int count = 0;
								if (rs.next()) {
									count = rs.getInt(1);
								}
								rs.close();
								countStmt.close();

								PageUtil.setTotalCount(Integer.valueOf(count));
							}
						} else if (this.totalCount) {
							if (("sqlserver".equals(dbtype))
									&& (sql.toUpperCase().contains("ORDER BY"))) {
								sql = sql.trim();
								sql = "select top 100 percent"
										+ sql.substring(6);
							}

//							String countSql = "select count(0) from (" + sql
//									+ ")   tmp_count";
							Parser parser = AbstractParser.newParser(dbtype);
							String countSql = parser.getCountSql(sql);
							
							PreparedStatement countStmt = connection
									.prepareStatement(countSql);
							setParameters(countStmt, mappedStatement, boundSql,
									parameterObject);
							ResultSet rs = countStmt.executeQuery();
							int count = 0;
							if (rs.next()) {
								count = rs.getInt(1);
							}
							rs.close();
							countStmt.close();

							PageUtil.setTotalCount(Integer.valueOf(count));
						}
						String pageSql = generatePageSql(sql, paraMap, dbtype);
						ReflectHelper.setValueByFieldName(boundSql, "sql",
								pageSql);
					}
				}
			}
		}

		return ivk.proceed();
	}

	public Object plugin(Object arg0) {
		return Plugin.wrap(arg0, this);
	}

	private void setParameters(PreparedStatement ps,
			MappedStatement mappedStatement, BoundSql boundSql,
			Object parameterObject) throws SQLException {
		ErrorContext.instance().activity("setting parameters")
				.object(mappedStatement.getParameterMap().getId());
		List parameterMappings = boundSql.getParameterMappings();
		if (parameterMappings != null) {
			Configuration configuration = mappedStatement.getConfiguration();
			TypeHandlerRegistry typeHandlerRegistry = configuration
					.getTypeHandlerRegistry();
			MetaObject metaObject = (parameterObject == null) ? null
					: configuration.newMetaObject(parameterObject);
			for (int i = 0; i < parameterMappings.size(); ++i) {
				ParameterMapping parameterMapping = (ParameterMapping) parameterMappings
						.get(i);
				if (parameterMapping.getMode() == ParameterMode.OUT)
					continue;
				String propertyName = parameterMapping.getProperty();
				PropertyTokenizer prop = new PropertyTokenizer(propertyName);
				Object value;
				if (parameterObject == null) {
					value = null;
				} else {
					if (typeHandlerRegistry.hasTypeHandler(parameterObject
							.getClass())) {
						value = parameterObject;
					} else {
						if (boundSql.hasAdditionalParameter(propertyName)) {
							value = boundSql
									.getAdditionalParameter(propertyName);
						} else if ((propertyName.startsWith("__frch_"))
								&& (boundSql.hasAdditionalParameter(prop
										.getName()))) {
							value = boundSql.getAdditionalParameter(prop
									.getName());
							if (value != null)
								value = configuration.newMetaObject(value)
										.getValue(
												propertyName.substring(prop
														.getName().length()));
						} else {
							value = (metaObject == null) ? null : metaObject
									.getValue(propertyName);
						}
					}
				}
				TypeHandler typeHandler = parameterMapping.getTypeHandler();
				if (typeHandler == null) {
					log.error(super.getClass().getName()
							+ "(177):There was no TypeHandler found for parameter "
							+ propertyName + " of statement "
							+ mappedStatement.getId());
					throw new ExecutorException(
							"There was no TypeHandler found for parameter "
									+ propertyName + " of statement "
									+ mappedStatement.getId());
				}
				typeHandler.setParameter(ps, i + 1, value,
						parameterMapping.getJdbcType());
			}
		}
	}

	public void setProperties(Properties properties) {
		if (properties.get("totalCount") != null) {
			String _totalCount = (String) properties.get("totalCount");
			this.totalCount = Boolean.parseBoolean(_totalCount);
		}
	}
}