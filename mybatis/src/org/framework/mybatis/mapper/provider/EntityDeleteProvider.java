package org.framework.mybatis.mapper.provider;

import java.util.List;

import org.apache.ibatis.builder.StaticSqlSource;
import org.apache.ibatis.jdbc.SQL;
import org.apache.ibatis.mapping.MappedStatement;

import tk.mybatis.mapper.mapperhelper.EntityHelper;
import tk.mybatis.mapper.mapperhelper.MapperHelper;
import tk.mybatis.mapper.mapperhelper.MapperTemplate;

public class EntityDeleteProvider extends MapperTemplate {
	public EntityDeleteProvider(Class<?> mapperClass, MapperHelper mapperHelper) {
		super(mapperClass, mapperHelper);
	}

	public void delete(MappedStatement ms) {
		Class entityClass = getSelectReturnType(ms);
		List parameterMappings = getPrimaryKeyParameterMappings(ms);

		String sql = new SQL()
				.DELETE_FROM(EntityHelper.getEntityTable(entityClass).getName())
				.WHERE(EntityHelper.getPrimaryKeyWhere(entityClass))
				.toString();

		StaticSqlSource sqlSource = new StaticSqlSource(ms.getConfiguration(),
				sql, parameterMappings);

		setSqlSource(ms, sqlSource);
	}
}