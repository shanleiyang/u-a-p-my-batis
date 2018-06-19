package org.framework.mybatis;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.executor.resultset.DefaultResultSetHandler;
import org.apache.ibatis.executor.statement.BaseStatementHandler;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;

@Intercepts({
		@org.apache.ibatis.plugin.Signature(type = org.apache.ibatis.executor.statement.StatementHandler.class, method = "prepare", args = { java.sql.Connection.class }),
		@org.apache.ibatis.plugin.Signature(method = "handleResultSets", type = org.apache.ibatis.executor.resultset.ResultSetHandler.class, args = { Statement.class }) })
public class RestlutMapInterceptor implements Interceptor {
	private static Log log = LogFactory.getLog(RestlutMapInterceptor.class);

	private void closeResultSet(ResultSet resultSet) {
		try {
			if (resultSet != null)
				resultSet.close();
		} catch (SQLException localSQLException) {
		}
	}

	private Object handleResultSet(ResultSet resultSet) {
		if (resultSet != null) {
			List resultList = new ArrayList();
			try {
				ResultSetMetaData rsmd = resultSet.getMetaData();
				int columnCount = rsmd.getColumnCount();

				while (resultSet.next()) {
					Map map = new HashMap();

					for (int i = 1; i <= columnCount; ++i) {
						Object value = resultSet.getObject(rsmd
								.getColumnName(i));
						if (value != null) {
							if ("upper".equals(ResultMapUtil.getUpperOrLower()))
								map.put(rsmd.getColumnName(i).toUpperCase(),
										value);
							else {
								map.put(rsmd.getColumnName(i).toLowerCase(),
										value);
							}
						}
					}

					resultList.add(map);
				}
			} catch (SQLException e) {
				log.error("Mybatis返回值Map统一大小处理出错！{}", e);
				e.printStackTrace();
			} finally {
				closeResultSet(resultSet);
			}
			return resultList;
		}
		return null;
	}

	public Object intercept(Invocation ivk) throws Throwable {
		Object target = ivk.getTarget();

		if ((ResultMapUtil.getUpperOrLower() != null)
				&& (target instanceof RoutingStatementHandler)) {
			RoutingStatementHandler statementHandler = (RoutingStatementHandler) target;
			BaseStatementHandler delegate = (BaseStatementHandler) ReflectHelper
					.getValueByFieldName(statementHandler, "delegate");
			MappedStatement mappedStatement = (MappedStatement) ReflectHelper
					.getValueByFieldName(delegate, "mappedStatement");

			List rms = mappedStatement.getResultMaps();
			ResultMap rm = ((rms != null) && (rms.size() > 0)) ? (ResultMap) rms
					.get(0) : null;
			String type = ((rm != null) && (rm.getType() != null)) ? rm
					.getType().getName() : "";
			if (("java.util.HashMap".equals(type))
					|| ("java.util.Map".equals(type))) {
				ResultMapUtil.threadInfo.set(Boolean.valueOf(true));
			}

		}

		if ((ResultMapUtil.getUpperOrLower() != null)
				&& (target instanceof DefaultResultSetHandler)
				&& (ResultMapUtil.threadInfo.get() != null)
				&& (((Boolean) ResultMapUtil.threadInfo.get()).booleanValue())) {
			Statement stmt = (Statement) ivk.getArgs()[0];

			ResultMapUtil.threadInfo.remove();
			return handleResultSet(stmt.getResultSet());
		}

		ResultMapUtil.threadInfo.remove();

		return ivk.proceed();
	}

	public Object plugin(Object obj) {
		return Plugin.wrap(obj, this);
	}

	public void setProperties(Properties properties) {
		ResultMapUtil.setUpperOrLower(null);
		if (properties.get("upperOrLower") != null) {
			String upperOrLower = (String) properties.get("upperOrLower");
			if ("upper".equals(upperOrLower.toLowerCase()))
				ResultMapUtil.setUpperOrLower("upper");
			else if ("lower".equals(upperOrLower.toLowerCase()))
				ResultMapUtil.setUpperOrLower("lower");
			else
				log.warn("Mybatis返回值Map统一大小写配置属性{" + upperOrLower
						+ "}为无效值，属性为 upper 或 lower!");
		}
	}
}