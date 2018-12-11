package com.nrjh.bigdata.utils.ibatis;

import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.scripting.xmltags.DynamicContext;
import org.apache.ibatis.scripting.xmltags.SqlNode;
import org.apache.ibatis.session.Configuration;

public class MyDynamicSqlSource implements SqlSource {

	private Configuration configuration;
	private SqlNode rootSqlNode;

	public MyDynamicSqlSource(Configuration configuration, SqlNode rootSqlNode) {
		this.configuration = configuration;
		this.rootSqlNode = rootSqlNode;
	}

	@Override
	public BoundSql getBoundSql(Object parameterObject) {
		DynamicContext context = new DynamicContext(configuration,
				parameterObject);
		rootSqlNode.apply(context);

		return new BoundSql(configuration, context.getSql(), null, null);
	}
}
