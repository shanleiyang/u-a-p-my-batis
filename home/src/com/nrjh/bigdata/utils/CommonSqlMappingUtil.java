package com.nrjh.bigdata.utils;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.nrjh.bigdata.sqlmapping.service.CommonSqlMapping;
import com.nrjh.bigdata.sqlmapping.service.ICommonSqlMappingService;
import com.nrjh.bigdata.utils.cache.CacheProvider;
import com.nrjh.bigdata.utils.cache.impl.MapCacheProvider;
import com.sgcc.uap.utils.ComponentFactory;
/**
 * 将查询页面数据需要执行的SQL持久到缓存中，
 * 并提供根据"控件编号"获取"控件查询SQL映射"表中的"查询SQL"的方法
 * @author wuhuawei
 * @date 2018-10-31
 */
public class CommonSqlMappingUtil {
	private static Log log = LogFactory.getLog(CommonSqlMappingUtil.class);
	private static  CacheProvider sqlMapCache = MapCacheProvider.getInstance();
	/**
	 * 此静态程序块，确保第一次调用时，加载数据，并持久到缓存中。
	 */
	static {
		//初始化查询页面数据需要执行的SQL到系统缓存中
		init();
	}

	/**
	 * 初始化查询页面数据需要执行的SQL到系统缓存中
	 * 将COMMON_SQL_MAPPING表中维护的QUERY_SQL缓存到系统缓存中
	 */
	public static void init() {
		try {
			List<CommonSqlMapping> tmpList = new ArrayList<CommonSqlMapping>();
			ICommonSqlMappingService sqlMappingService = (ICommonSqlMappingService)ComponentFactory.getBean("commonSqlMappingService");
			tmpList = sqlMappingService.getAll();
			sqlMapCache.clear();
			for (int i = 0; i < tmpList.size(); i++) {
				CommonSqlMapping commonSqlMapping = tmpList.get(i);
				String tagnokey = commonSqlMapping.getTagNo();
				String querysqlvalue = commonSqlMapping.getQuerySql();
				if (tagnokey==null || "".equals(tagnokey)) {
					continue;
				} else {				
					sqlMapCache.put(tagnokey, querysqlvalue);
				}	
			}
			if(log.isDebugEnabled()){
				log.debug("##初始化查询页面数据需要执行的SQL到系统缓存中，缓存的记录条数为："+tmpList.size());
			}
		} catch (Exception e) {
			log.error("##------------将需要执行的SQL持久到缓存中时失败-------------{}", e);
		}
	}

	/**
	 * 根据"控件编号"获取"控件查询SQL映射"表中的"查询SQL"
	 * @param _tagnokey 控件编号
	 */
	public static String getSqlValue(String _tagnokey) {
		String _querysqlvalue = null;
		if (_tagnokey == null || "".equals(_tagnokey)) {
			// 如果_tagnokey为空则不符合业务规则，不做处理
			return null;
		} else if (sqlMapCache.size() == 0) {
			return null;
		} else {			
			_querysqlvalue = (String) sqlMapCache.get(_tagnokey);
		}
		
		return _querysqlvalue;
	}

}
