package org.framework.mybatis.parser.impl;

import org.framework.mybatis.parser.Parser;
import org.framework.mybatis.parser.SqlParser;

public abstract class AbstractParser implements Parser {

	public static final SqlParser sqlParser = new SqlParser();

	public static Parser newParser(String dbtype) {
		Parser parser = new MysqlParser();
		return parser;
	}

	public String getCountSql(String sql) {
		return sqlParser.getSmartCountSql(sql);
	}

}
