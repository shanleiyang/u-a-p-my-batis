package org.framework.mybatis.mapper.api;

import java.util.List;

import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Options;
import org.framework.mybatis.mapper.provider.BatchProvider;

public abstract interface BatchInsertMapper<T> {
	@Options(useGeneratedKeys = false)
	@InsertProvider(type = BatchProvider.class, method = "dynamicSQL")
	public abstract int batchInsert(List<T> paramList);
}
