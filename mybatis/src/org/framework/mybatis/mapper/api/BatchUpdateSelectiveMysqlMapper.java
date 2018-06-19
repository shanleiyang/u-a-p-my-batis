package org.framework.mybatis.mapper.api;

import java.util.List;

import org.apache.ibatis.annotations.UpdateProvider;
import org.framework.mybatis.mapper.provider.EntityBatchMysqlProvider;

public abstract interface BatchUpdateSelectiveMysqlMapper<T> {
	@UpdateProvider(type = EntityBatchMysqlProvider.class, method = "dynamicSQL")
	public abstract int batchUpdateSelectiveMysql(List<T> paramList);
}
