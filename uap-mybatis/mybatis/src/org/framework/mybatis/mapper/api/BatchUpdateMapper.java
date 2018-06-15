package org.framework.mybatis.mapper.api;

import java.util.List;

import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.UpdateProvider;
import org.framework.mybatis.mapper.provider.BatchProvider;

public abstract interface BatchUpdateMapper<T> {
	@Options(useGeneratedKeys = false)
	@UpdateProvider(type = BatchProvider.class, method = "dynamicSQL")
	public abstract int batchUpdate(List<T> paramList);
}