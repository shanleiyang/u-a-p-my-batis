package org.framework.mybatis.mapper.api;

import java.util.List;

import org.apache.ibatis.annotations.SelectProvider;
import org.framework.mybatis.mapper.provider.EntityGetProvider;

public abstract interface GetAllMapper<T> {
	@SelectProvider(type = EntityGetProvider.class, method = "dynamicSQL")
	public abstract List<T> getAll();
}
