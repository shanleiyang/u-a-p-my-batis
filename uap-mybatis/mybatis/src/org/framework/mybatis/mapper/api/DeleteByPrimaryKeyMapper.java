package org.framework.mybatis.mapper.api;

import org.apache.ibatis.annotations.DeleteProvider;
import org.framework.mybatis.mapper.provider.EntityDeleteProvider;

public abstract interface DeleteByPrimaryKeyMapper<T> {
	@DeleteProvider(type = EntityDeleteProvider.class, method = "dynamicSQL")
	public abstract int delete(Object paramObject);
}
