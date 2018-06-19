package org.framework.mybatis.mapper.api;

import org.apache.ibatis.annotations.InsertProvider;
import org.framework.mybatis.mapper.provider.EntityInsertProvider;

public abstract interface InsertMapper<T> {
	@InsertProvider(type = EntityInsertProvider.class, method = "dynamicSQL")
	public abstract int insert(T paramT);
}
