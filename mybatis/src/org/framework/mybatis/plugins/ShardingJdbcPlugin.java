package org.framework.mybatis.plugins;

import java.util.Properties;

import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

@Intercepts({
		@Signature(type = Executor.class, // 目标类
		method = "query", // 目标方法
		args = { MappedStatement.class, Object.class, RowBounds.class,
				ResultHandler.class, CacheKey.class, BoundSql.class }),
		@Signature(type = Executor.class, // 目标类
		method = "query", // 目标方法
		args = { MappedStatement.class, Object.class, RowBounds.class,
				ResultHandler.class }), @Signature(type = Executor.class, // 目标类
		method = "update", // 目标方法
		args = { MappedStatement.class, Object.class }),
		@Signature(type = Executor.class, // 目标类
		method = "flushStatements", // 目标方法
		args = {}) 
	})
public class ShardingJdbcPlugin implements Interceptor {

	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		// do something ...... 方法拦截前执行代码块
		Object result = invocation.proceed();
		// do something .......方法拦截后执行代码块
		return result;
	}

	@Override
	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	@Override
	public void setProperties(Properties arg0) {
		// TODO Auto-generated method stub

	}

}
