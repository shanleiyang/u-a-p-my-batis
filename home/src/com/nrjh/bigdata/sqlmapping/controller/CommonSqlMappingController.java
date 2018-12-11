package com.nrjh.bigdata.sqlmapping.controller;


import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.framework.abstracter.AbstractController;
import org.framework.abstracter.InterfaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nrjh.bigdata.sqlmapping.service.CommonSqlMapping;
import com.nrjh.bigdata.sqlmapping.service.ICommonSqlMappingService;
import com.nrjh.bigdata.utils.CommonSqlMappingUtil;
/**
 * 操作控件sql映射表，实现增、删、改、查功能
 */
@Controller
@RequestMapping(value = "/bigdata/sqlMapping")
public class CommonSqlMappingController extends AbstractController<CommonSqlMapping> {
	private static Log log = LogFactory.getLog(CommonSqlMappingController.class);
	@Autowired
	private ICommonSqlMappingService commonSqlMappingService;

	@Override
	public String getEditJsp() {
		// TODO Auto-generated method stub
		return "bigdata/sqlmapping/sqlmappingedit.jsp";
	}

	@Override
	public String getQueryJsp() {
		// TODO Auto-generated method stub
		return "bigdata/sqlmapping/sqlmappingquery.jsp";
	}

	@Override
	public InterfaceService<CommonSqlMapping> getService() {
		return commonSqlMappingService;
	}
	/**
	 * 批量删除记录
	 * @param ids
	 * @return
	 */
	@RequestMapping({"/batchDelete"})
	  public String batchDelete(@RequestParam(value="id")String[] ids)
	  {
	    if ((ids != null) && (ids.length > 0)) {
	      getService().batchDelete(ids);
	    }
	    return String.format("redirect:/rest%s", new Object[] { this.defaultUrl });
	  }
	/**
	 * 刷新缓存
	 */
	@RequestMapping({"/refreshCache"})
	@ResponseBody
	public Map refreshCache() {
		if(log.isDebugEnabled()){
			log.debug("##刷新缓存开始");
		}
		CommonSqlMappingUtil.init();
		if(log.isDebugEnabled()){
			log.debug("##刷新缓存结束");
		}
		Map rsltMap = new HashMap();
		rsltMap.put("success", true);
		return rsltMap;
	}
}
