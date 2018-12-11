package org.framework.datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class ShardingDataSource extends AbstractRoutingDataSource {

	@Override
	protected Object determineCurrentLookupKey() {
		return DataSourceContextHolder.getDataSourceType();
	}

}
