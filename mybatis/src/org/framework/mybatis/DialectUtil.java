package org.framework.mybatis;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DialectUtil {
	private static final Log logger = LogFactory.getLog(DialectUtil.class);

	public static String getDBTypeByDBId(String _databaseId) {
		if (logger.isDebugEnabled()) {
			logger.debug(String.format("数据库类型为：%s",  _databaseId));
		}
		if ((_databaseId != null) && (!("".equals(_databaseId)))) {
			if (_databaseId.toLowerCase().contains("db2"))
				return "db2";
			if (_databaseId.toLowerCase().contains("oracle"))
				return "oracle";
			if (_databaseId.toLowerCase().contains("mysql"))
				return "mysql";
			if (_databaseId.toLowerCase().contains("hdb"))
				return "hana";
			if (_databaseId.toLowerCase().contains("sql server"))
				return "sqlserver";
			if (_databaseId.toLowerCase().contains("postgresql"))
				return "postgresql";
		} else {
			logger.warn("未获取到数据库类型");
			return "db2";
		}
		return "db2";
	}
}
