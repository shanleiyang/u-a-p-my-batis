package com.nrjh.bigdata.sqlmapping.service;

import java.util.List;
import java.util.Map;

import org.framework.abstracter.InterfaceService;

public interface ICommonSqlMappingService extends InterfaceService<CommonSqlMapping> {
	/**
	 * 根据sql语句查询
	 * @param map {"sql":xxx,param1:xx,param2:xx,...}
	 * @return
	 */
	public List<Map> executeSql(Map map);
	/**
	 * 查询全部记录
	 * @return
	 */
	public List<CommonSqlMapping> getAll();
}
