package org.framework.mybatis.mapper.api;

import org.apache.ibatis.annotations.UpdateProvider;
import org.framework.mybatis.mapper.provider.EntityUpdateProvider;

public abstract interface UpdateByPrimaryKeyMapper<T> {
	@UpdateProvider(type = EntityUpdateProvider.class, method = "dynamicSQL")
	public abstract int update(T paramT);
}