package org.framework.mybatis.mapper.api;

import org.apache.ibatis.annotations.UpdateProvider;
import org.framework.mybatis.mapper.provider.EntityUpdateMysqlProvider;

public abstract interface UpdateSelectiveMysqlMapper<T> {
	@UpdateProvider(type = EntityUpdateMysqlProvider.class, method = "dynamicSQL")
	public abstract int updateSelectiveMysql(T param);
}
