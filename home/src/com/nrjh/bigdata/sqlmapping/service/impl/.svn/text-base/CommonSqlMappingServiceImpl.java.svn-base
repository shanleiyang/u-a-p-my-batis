package com.nrjh.bigdata.sqlmapping.service.impl;

import java.util.List;
import java.util.Map;

import org.framework.abstracter.AbstractService;
import org.framework.mybatis.mapper.EntityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nrjh.bigdata.sqlmapping.dao.CommonSqlMappingMapper;
import com.nrjh.bigdata.sqlmapping.service.CommonSqlMapping;
import com.nrjh.bigdata.sqlmapping.service.ICommonSqlMappingService;

@Service("commonSqlMappingService")
public class CommonSqlMappingServiceImpl extends AbstractService<CommonSqlMapping> implements ICommonSqlMappingService {

	@Autowired
	private CommonSqlMappingMapper commonSqlMappingMapper;
	
	@Override
	public EntityMapper getDaoMapper() {
		return commonSqlMappingMapper;
	}
	/**
	 * 根据sql语句查询
	 * @param map {"sql":xxx,param1:xx,param2:xx,...}
	 * @return
	 */
	public List<Map> executeSql(Map map){
		return commonSqlMappingMapper.executeSql(map);
	}
	/**
	 * 查询全部记录
	 * @return
	 */
	public List<CommonSqlMapping> getAll(){
		return getDaoMapper().getAll();
	}
}
