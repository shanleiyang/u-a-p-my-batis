package org.framework.mybatis.mapper.api;

import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.Options;
import org.framework.mybatis.mapper.provider.BatchProvider;

public abstract interface BatchDeleteMapper<T> {
	@Options(useGeneratedKeys = false)
	@DeleteProvider(type = BatchProvider.class, method = "dynamicSQL")
	public abstract int batchDelete(Object[] paramArrayOfObject);
}
