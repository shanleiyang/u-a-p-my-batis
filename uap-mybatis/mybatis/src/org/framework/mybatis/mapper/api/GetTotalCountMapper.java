package org.framework.mybatis.mapper.api;

import org.apache.ibatis.annotations.SelectProvider;
import org.framework.mybatis.mapper.provider.EntityGetProvider;

public abstract interface GetTotalCountMapper<T> {
	@SelectProvider(type = EntityGetProvider.class, method = "dynamicSQL")
	public abstract int getTotalCount();
}
