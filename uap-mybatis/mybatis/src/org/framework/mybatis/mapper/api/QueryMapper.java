package org.framework.mybatis.mapper.api;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.SelectProvider;
import org.framework.mybatis.mapper.provider.EntityGetProvider;

public abstract interface QueryMapper<T> {
	@SelectProvider(type = EntityGetProvider.class, method = "dynamicSQL")
	public abstract List<T> query(Map paramMap);
}