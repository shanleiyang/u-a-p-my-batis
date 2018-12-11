package com.nrjh.framework.taglib;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nrjh.bigdata.sqlmapping.service.ICommonSqlMappingService;
import com.nrjh.bigdata.utils.CommonSqlMappingUtil;
import com.nrjh.framework.util.BspContextFactory;
import com.sgcc.uap.utils.ComponentFactory;
/**
 * 根据sql 查询单位信息
 * @author LL
 *
 */
public class OrgListUtil {
	private static ICommonSqlMappingService sqlMappingService = (ICommonSqlMappingService)ComponentFactory.getBean("commonSqlMappingService");

	public static List getOrgList(String tagNo) {
		Map<String,String> sqlMap = new HashMap<String,String>();
		String sqlString = CommonSqlMappingUtil.getSqlValue(tagNo);
		sqlMap.put("provinceNo", BspContextFactory.getComCode());
		sqlMap.put("sql", sqlString);
		List dataList = sqlMappingService.executeSql(sqlMap);
		return dataList;
	}
}
