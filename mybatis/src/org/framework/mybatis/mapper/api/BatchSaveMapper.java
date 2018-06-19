package org.framework.mybatis.mapper.api;

import java.util.List;

import org.apache.ibatis.annotations.UpdateProvider;
import org.framework.mybatis.mapper.provider.EntityUpdateProvider;

public abstract interface BatchSaveMapper<T> {
	@UpdateProvider(type = EntityUpdateProvider.class, method = "dynamicSQL")
	public abstract int save(List<T> paramList);
}
