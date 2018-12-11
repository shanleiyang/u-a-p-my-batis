package com.nrjh.bigdata.sqlmapping.dao;

import java.util.List;
import java.util.Map;

import org.framework.mybatis.mapper.EntityMapper;

import com.nrjh.bigdata.sqlmapping.service.CommonSqlMapping;

public interface CommonSqlMappingMapper extends EntityMapper<CommonSqlMapping> {
	/**
	 * 根据sql语句查询
	 * @param map {"sql":xxx,param1:xx,param2:xx,...}
	 * @return
	 */
	public List<Map> executeSql(Map map);
}
