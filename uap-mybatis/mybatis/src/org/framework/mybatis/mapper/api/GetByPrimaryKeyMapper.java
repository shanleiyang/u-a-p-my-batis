package org.framework.mybatis.mapper.api;

import org.apache.ibatis.annotations.SelectProvider;
import org.framework.mybatis.mapper.provider.EntityGetProvider;

public abstract interface GetByPrimaryKeyMapper<T> {
	@SelectProvider(type = EntityGetProvider.class, method = "dynamicSQL")
	public abstract T get(Object paramObject);
}
