package org.framework.mybatis.plugins;

import java.sql.Connection;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.framework.datasource.DataSourceContextHolder;

import com.nrjh.framework.util.BspContextFactory;

@Intercepts({
		@Signature(type = StatementHandler.class, method = "prepare", args = { Connection.class }) })
public class ShardingJdbcPlugin implements Interceptor {
	private static final Log log = LogFactory.getLog(ShardingJdbcPlugin.class);
	// 需要强制去默认库执行的表，使用","半角逗号分隔
	private static String[] defaultDbTables = {};

	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		Object target = invocation.getTarget();
		StatementHandler statementHandler = (StatementHandler)target;
		BoundSql boundSql = statementHandler.getBoundSql();
		String sql = boundSql.getSql().toLowerCase();
		// 单位编码
		String comCode = BspContextFactory.getComCode();
		// 在执行sql之前，设置分库编码
		DataSourceContextHolder.setDataSourceType(comCode);
		log.info(String.format("##ShardingJdbcPlugin.intercept 分库编码: %s", comCode));
		// sql需要去默认库执行
		if(isGoDefaultDbTable(sql)) {
			DataSourceContextHolder.clear();
			log.info(String.format("##ShardingJdbcPlugin.intercept sql需要到默认库执行. %s", sql));
		}
		try{
			return invocation.proceed();
		} finally {
			// 执行sql之后，清除本地线程中的分库编码
			DataSourceContextHolder.clear();
		}
	}

	@Override
	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	@Override
	public void setProperties(Properties param) {
		defaultDbTables = String.valueOf(param.getProperty("defaultDbTables")).split(",");
	}
	
	/**
	 * 验证是否需要到默认库执行
	 * @return
	 */
	public Boolean isGoDefaultDbTable(String sql) {
		for(String table : defaultDbTables) {
			if(sql.contains(table.toLowerCase())) {
				return true;
			}
		}
		return false;
	}

}
