package com.nrjh.bigdata.sqlmapping.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.framework.abstracter.AbstractController;
import org.framework.abstracter.InterfaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nrjh.bigdata.sqlmapping.service.CommonSqlMapping;
import com.nrjh.bigdata.sqlmapping.service.ICommonSqlMappingService;
import com.nrjh.bigdata.utils.CommonSqlMappingUtil;
import com.nrjh.bigdata.utils.ibatis.SQLScriptBuilder;
import com.sgcc.uap.utils.JSONOperUtils;
/**
 * 获取展示控件所需数据
 * @author wuhuawei
 */
@Controller
@RequestMapping(value = "/bigdata/dataProvider")
public class DataProviderController extends AbstractController<CommonSqlMapping> {
	private static Log log = LogFactory.getLog(DataProviderController.class);
	private static SQLScriptBuilder sqlBuilder = new SQLScriptBuilder();
	@Autowired
	private ICommonSqlMappingService commonSqlMappingService;
	@Override
	public String getEditJsp() {
		return null;
	}
	@Override
	public String getQueryJsp() {
		return null;
	}
	@Override
	public InterfaceService<CommonSqlMapping> getService() {
		return commonSqlMappingService;
	}
	/**
	 * 获取展示控件所需数据
	 * 根据控件编号获取维护在控件映射表中的查询sql，根据参数执行sql查询记录
	 * @param parameters 格式{"_TAGNO":xxx,param1:xx,param2:xx,...}，_TAGNO为控件编号，不能为空
	 * @return
	 */
	@RequestMapping({"/getData"})
	@ResponseBody
	public Map getData(@RequestBody Map<String, String> parameters){
		//控件编号，根据其从缓存中获取sql语句，不能为空
		String tagNo = parameters.get("_TAGNO");
		if(log.isDebugEnabled()){
			log.debug("##传入参数："+JSONOperUtils.pojoToJSON(parameters));
		}
		Map rsltMap = new HashMap();
		if(tagNo==null || "".equals(tagNo)){
			rsltMap.put("success", false);
			rsltMap.put("msg", "前台传入参数出错");
			if(log.isDebugEnabled()){
				log.debug("##前台传入_TAGNO参数出错");
			}
			return rsltMap;
		}
		Map sqlMap = new HashMap();
		//根据"控件编号"获取"控件查询SQL映射"表中的"查询SQL"，从系统缓存中获取
		String sqlString = CommonSqlMappingUtil.getSqlValue(tagNo);
		// 支持mybatis xml形式的动态sql解析
		sqlString = sqlBuilder.parseScriptNode(sqlString, parameters);
		if(log.isDebugEnabled()){
			log.debug("##查询sql为："+sqlString);
		}
		sqlMap.put("sql", sqlString);
		sqlMap.putAll(parameters);
		try {
			List dataList = commonSqlMappingService.executeSql(sqlMap);
			rsltMap.put("success", true);
			rsltMap.put("data", dataList);
		} catch (Exception e) {
			log.error("##DataProviderController.getData 出错了.", e);
			rsltMap.put("success", false);
			rsltMap.put("msg", "执行查询时出错");
		}
		return rsltMap;
	}
}
