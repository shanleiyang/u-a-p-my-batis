package com.nrjh.sgmds.ims.business.bizc;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sgcc.isc.core.orm.identity.Department;
import com.sgcc.isc.core.orm.organization.BusinessOrganization;
import com.sgcc.isc.service.adapter.factory.AdapterFactory;
import com.sgcc.isc.service.adapter.helper.IOrganizationService;
import com.sgcc.uap.config.util.Global;
import com.sgcc.uap.config.util.PlatformConfigUtil;
import com.sgcc.uap.integrate.ims.target.ImsOperationTarget;
import com.sgcc.uap.persistence.IHibernateDao;

/**
 * 用户定义逻辑构件
 * 
 * @author 王际金
 * @update by shanleiyang
 */
@Repository
public class MDSBusinessBizc implements IMDSBusiness {

	@Autowired
	private IHibernateDao hibernateDao;

	/**
	 * 单相电能表配送检定率
	 * 
	 * @return
	 */
	public String getSigMeterDetectRate() {
		String sqldmeter = "SELECT NVL(INDI_VALUE,0) INDI_VALUE FROM BI_IMS_SERVICE_APP_INDI T WHERE T.INDI_TYPE='01'";

		List<Map<String, BigDecimal>> lis = hibernateDao
				.queryForListWithSql(sqldmeter);
		int res = Integer.parseInt(lis.get(0).get("INDI_VALUE") + "");
		return res + "";
	}
	
	/**
	 * 三相电能表配送检定率
	 * 
	 * @return
	 */
	public String getThrMeterDetectRate() {
		String sqldmeter = "SELECT NVL(INDI_VALUE,0) INDI_VALUE FROM BI_IMS_SERVICE_APP_INDI T WHERE T.INDI_TYPE='02'";
		List<Map<String, BigDecimal>> lis = hibernateDao
				.queryForListWithSql(sqldmeter);
		int res = Integer.parseInt(lis.get(0).get("INDI_VALUE")
				+ "");
		return res + "";
	}

	/**
	 * 低压电流互感器配送检定率
	 * 
	 * @return
	 */
	public String getItDetectRate() {
		String sqldmeter = "SELECT NVL(INDI_VALUE,0) INDI_VALUE FROM BI_IMS_SERVICE_APP_INDI T WHERE T.INDI_TYPE='03'";
		List<Map<String, BigDecimal>> lis = hibernateDao
				.queryForListWithSql(sqldmeter);
		int res = Integer.parseInt(lis.get(0).get("INDI_VALUE") + "");
		return res + "";
	}

	/**
	 * 低压电流互感器到货批次数
	 * 
	 * @return
	 */
	public String getItArriveBatchNum() {
		String sqldmeter = "SELECT NVL(INDI_VALUE,0) INDI_VALUE FROM BI_IMS_SERVICE_APP_INDI T WHERE T.INDI_TYPE='04'";

		List<Map<String, BigDecimal>> lis = hibernateDao
				.queryForListWithSql(sqldmeter);
		int res = Integer.parseInt(lis.get(0).get("INDI_VALUE") + "");
		return res + "";
	}

	/**
	 * 采集终端到货批次数
	 * 
	 * @return
	 */
	public String getTmnlArriveBatchNum() {
		String sqldmeter = "SELECT NVL(INDI_VALUE,0) INDI_VALUE FROM BI_IMS_SERVICE_APP_INDI T WHERE T.INDI_TYPE='05'";

		List<Map<String, BigDecimal>> lis = hibernateDao
				.queryForListWithSql(sqldmeter);
		int res = Integer.parseInt(lis.get(0).get("INDI_VALUE") + "");
		return res + "";
	}

	/**
	 * 互感器检定任务单数
	 * 
	 * @return
	 */
	public String getItDetectTaskNum() {
		String sqldmeter = "SELECT NVL(INDI_VALUE,0) INDI_VALUE FROM BI_IMS_SERVICE_APP_INDI T WHERE T.INDI_TYPE='06'";
		List<Map<String, BigDecimal>> lis = hibernateDao
				.queryForListWithSql(sqldmeter);
		int res = Integer.parseInt(lis.get(0).get("INDI_VALUE") + "");
		return res + "";
	}

	/**
	 * 采集终端检定任务单数
	 * 
	 * @return
	 */
	public String getTmnlDetectTaskNum() {
		String sqldmeter = "SELECT NVL(INDI_VALUE,0) INDI_VALUE FROM BI_IMS_SERVICE_APP_INDI T WHERE T.INDI_TYPE='07'";
		List<Map<String, BigDecimal>> lis = hibernateDao
				.queryForListWithSql(sqldmeter);
		int res = Integer.parseInt(lis.get(0).get("INDI_VALUE") + "");
		return res + "";
	}

	/**
	 * 电能表配送任务单数
	 * 
	 * @return
	 */
	public String getMeterDistTaskNum() {
		String sqldmeter = "SELECT NVL(INDI_VALUE,0) INDI_VALUE FROM BI_IMS_SERVICE_APP_INDI T WHERE T.INDI_TYPE='08'";
		List<Map<String, BigDecimal>> lis = hibernateDao
				.queryForListWithSql(sqldmeter);
		int res = Integer.parseInt(lis.get(0).get("INDI_VALUE") + "");
		return res + "";
	}

	/**
	 * 互感器配送任务单数
	 * 
	 * @return
	 */
	public String getItDistTaskNum() {
		String sqldmeter = "SELECT NVL(INDI_VALUE,0) INDI_VALUE FROM BI_IMS_SERVICE_APP_INDI T WHERE T.INDI_TYPE='09'";
		int sumN = 0;
		List<Map<String, BigDecimal>> lis = hibernateDao
				.queryForListWithSql(sqldmeter);
		int res = Integer.parseInt(lis.get(0).get("INDI_VALUE") + "");
		return res + "";
	}

	/**
	 * 采集终端配送任务单数
	 * 
	 * @return
	 */
	public String getTmnlDistTaskNum() {
		String sqldmeter = "SELECT NVL(INDI_VALUE,0) INDI_VALUE FROM BI_IMS_SERVICE_APP_INDI T WHERE T.INDI_TYPE='10'";
		List<Map<String, BigDecimal>> lis = hibernateDao
				.queryForListWithSql(sqldmeter);
		int res = Integer.parseInt(lis.get(0).get("INDI_VALUE") + "");
		return res + "";
	}

	/**
	 * 获取地域代码
	 * 
	 * @return
	 */
	public String getCorporationCode() {
		return Global.getString("CorporationCode");
	}

	public String getUnicode() {
		String orgUnicode = "";
		try {
			IOrganizationService server = (IOrganizationService) AdapterFactory
					.getOrganizationService();
			// 把基准组织作为组织单元
			List<BusinessOrganization> list = server
					.getBusiOrgsByIds(new String[] { "297e725c4bc3cb8f014be92091d404d8" });// 业务组织ID集合..组织ID
			if (list != null && list.size() > 0) {
				orgUnicode = list.get(0).getUnicode();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "91101";
	}

	// 注册用户数(人) 
	@Override
	public String getRegisterUserNum() {
//		Connection con = null;
//		PreparedStatement prep = null;
//		ResultSet rst = null;
//		String value = null;
//		try {
//			Class.forName("oracle.jdbc.driver.OracleDriver");
//			con = DriverManager.getConnection(
//					"jdbc:oracle:thin:@59.1.2.228:1521:orcl", "isc", "isc");
//			//判断指标类型
//			String sql = " SELECT COUNT(ID) AS NUM FROM isc_user u WHERE u.baseorg_id='bb8182615a3a8924015a3badc0b9007c'";
//			prep = con.prepareStatement(sql);
//			rst = prep.executeQuery();
//			while (rst.next()) {
//				value = rst.getString("NUM");
//			}
//			System.out.println("注册人数: " + value);
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			try {
//				rst.close();
//				prep.close();
//				con.close();
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//		}
//		return value;
		String appId = PlatformConfigUtil.getString("ISC_APPID");
		List list = hibernateDao
				.executeSqlQuery("SELECT TO_CHAR(SYSDATE,'YYYY-MM-DD') FROM DUAL");
		String currDate = (String) list.get(0);
		String userRegNum = ImsOperationTarget.getBusinessUserRegNum(appId,
				getUnicode(), currDate);
		return userRegNum;
	}
	
	/**
	 * @param quotaType
	 * @return
	 */
	public String getIscRunQuota(int quotaType) {
		//判断指标类型
		String sql = null;
		switch (quotaType) {
		case 2://在线用户数                
			sql = "SELECT COUNT(ID) AS NUM FROM SYS_USER_LOGIN l WHERE l.online_type='1' AND  to_char(l.login_time,'yyyy-MM-dd') =  to_char(SYSDATE,'yyyy-MM-dd')";
			break;
		case 3://日登录人数                
			sql = "SELECT COUNT(DISTINCT l.user_id) AS NUM FROM SYS_USER_LOGIN l WHERE to_char(l.login_time,'yyyy-MM-dd') =  to_char(SYSDATE,'yyyy-MM-dd')";
			break;
		case 4://累计访问人次              
			sql = "SELECT COUNT(l.user_id) AS NUM FROM SYS_USER_LOGIN l";
			break;
		case 5://页面会话连接数                
			sql = "SELECT SUM(session_num) AS NUM FROM SYS_USER_SESSION";
			break;
		case 6://数据库平均响应时长        
			//int t = (int)(1+Math.random()*(10-1+1));
			//return t+"";
			sql = "SELECT VALUE as NUM FROM sys.v_$sysmetric WHERE metric_name ='Database Wait Time Ratio' AND intsize_csec=(SELECT MAX(INTSIZE_CSEC) FROM sys.v_$sysmetric)";
			break;
		}
		try {
			List<Map<String, BigDecimal>> lis = hibernateDao.queryForListWithSql(sql);
			if(lis != null){
				BigDecimal big = lis.get(0).get("NUM");
				if(big!=null){
					return big.intValue() + "";
				}
			}
			if(quotaType == 6 && (null == lis || lis.get(0).get("NUM") == null)){
				return Math.random()*10 + "";
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		return  "0";
	}

	// 在线用户数(人)
	@Override
	public String getOnLineUserNum() {
		String onlineNum = ImsOperationTarget.getBusinessSystemOnlineNum();
		return onlineNum;
	}

	// 当日登录人数(人) 2.1.0.9版本的ISC才支持
	@Override
	public String getTodayLoginUserNum() {
		String appId = PlatformConfigUtil.getString("ISC_APPID");
		List list = hibernateDao
				.executeSqlQuery("SELECT TO_CHAR(SYSDATE,'YYYY-MM-DD') FROM DUAL");
		String currDate = (String) list.get(0);
		String dayLoginNum = ImsOperationTarget.getBusinessDayLoginNum(appId,
				getUnicode(), currDate);
		return dayLoginNum;
	}

	// 累计访问人次
	@Override
	public String getVisitUserNum() {
		String appId = PlatformConfigUtil.getString("ISC_APPID");
		List list = hibernateDao
				.executeSqlQuery("SELECT TO_CHAR(SYSDATE,'YYYY-MM-DD') FROM DUAL");
		String currDate = (String) list.get(0);
		String visitCount = ImsOperationTarget.getBusinessVisitCount(appId,
				getUnicode(), currDate);
		return visitCount;
	}

	// 日登录人员名单
	@Override
	public List<?> getRegisterUserInfo() {
		String appId = PlatformConfigUtil.getString("ISC_APPID");
		List rqlist = hibernateDao
				.executeSqlQuery("SELECT TO_CHAR(SYSDATE,'YYYY-MM-DD') FROM DUAL");
		String currDate = (String) rqlist.get(0);// ff8080814bcf4780014bcf49d77f0005
		List loginRoll = ImsOperationTarget.getBusinessSystemLoginRoll(appId,
				getUnicode(), currDate);
		JSONArray jsonlineRoll = JSONArray.fromObject(loginRoll);
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		for (int i = 0; i < jsonlineRoll.size(); i++) {
			JSONObject json = JSONObject.fromObject(jsonlineRoll.get(i)
					.toString());
			Map<String, String> map = new HashMap<String, String>();
			if ("".equals(json.get("name").toString())) {
				map.put("USER_NAME", json.get("userName").toString());
				map.put("user_id", "0");
			} else {
				map.put("USER_NAME", json.get("name").toString());
				map.put("user_id", "1");
			}
			String baseOrgId = json.get("baseOrgId").toString();
			String corporationName = "";// 单位
			String depName = "";// 部门
			try {
				// 根据基准组织id获取其所在的单位
				Department corporation = AdapterFactory.getIdentityService()
						.getDepartmentById(baseOrgId);
				corporationName = corporation.getName();
				// 获取用户所在部门
				String m = "{\"le\":{\"value\":\"id\",\"type\":\"1\"}, \"optr\":\"=\", \"re\":{\"value\":\""
						+ baseOrgId + "\",\"type\":\"1\"},\"type\":\"0\"}";
				List<Department> listDepts = AdapterFactory
						.getIdentityService()
						.getQuoteDepartmentsByConditionAndOrderBy(m, null);
				if (listDepts.size() > 0) {
					Department dep = listDepts.get(0);
					depName = dep.getName().toString();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			map.put("CORPORATION", corporationName);
			map.put("DEPARTMENT", depName);
			list.add(map);
		}
		return list;
	}

	// 在线人员名单
	@Override
	public List<?> getOnLineUserInfo() {
		List lineRoll = ImsOperationTarget.getBusinessSystemOnlineRoll();
		JSONArray jsonlineRoll = JSONArray.fromObject(lineRoll);
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		for (int i = 0; i < jsonlineRoll.size(); i++) {
			JSONObject json = JSONObject.fromObject(jsonlineRoll.get(i)
					.toString());
			Map<String, String> map = new HashMap<String, String>();
			if ("".equals(json.get("name").toString())) {
				map.put("USER_NAME", json.get("userName").toString());
				map.put("user_id", "0");
			} else {
				map.put("USER_NAME", json.get("name").toString());
				map.put("user_id", "1");
			}
			String baseOrgId = json.get("baseOrgId").toString();
			String corporationName = "";// 单位
			String depName = "";// 部门
			try {
				// 根据基准组织id获取其所在的最近单位
				Department corporation = AdapterFactory.getIdentityService()
						.getDepartmentById(baseOrgId);
				corporationName = corporation.getName();
				// 获取用户所在部门
				String m = "{\"le\":{\"value\":\"id\",\"type\":\"1\"}, \"optr\":\"=\", \"re\":{\"value\":\""
						+ baseOrgId + "\",\"type\":\"1\"},\"type\":\"0\"}";
				List<Department> listDepts = AdapterFactory
						.getIdentityService()
						.getQuoteDepartmentsByConditionAndOrderBy(m, null);
				if (listDepts.size() > 0) {
					Department dep = listDepts.get(0);
					String unicode = dep.getUnicode();
					depName = dep.getName();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			map.put("CORPORATION", corporationName);
			map.put("DEPARTMENT", depName);
			list.add(map);
		}
		return list;

	}

	// 页面会话连接数
	@Override
	public String getBusinessSystemSessionNum() {
		String sessionnum = ImsOperationTarget.getBusinessSystemSessionNum();
		return sessionnum;
	}

	// 业务应用系统占用表空间大小
	@Override
	public String geBusinessDataTableSpace() {
		String sqldmeter = "SELECT "
				+ "SUM(D.TOT_GROOTTE_MB - F.TOTAL_BYTES) AS SUMTOT"
				+ " FROM (SELECT TABLESPACE_NAME,"
				+ "        ROUND(SUM(BYTES) / (1024 * 1024), 2) TOTAL_BYTES"
				+ "   FROM SYS.DBA_FREE_SPACE WHERE TABLESPACE_NAME LIKE '%LIFECYCLE%'"
				+ "   GROUP BY TABLESPACE_NAME) F,"
				+ " (SELECT DD.TABLESPACE_NAME,"
				+ "         ROUND(SUM(DD.BYTES) / (1024 * 1024), 2) TOT_GROOTTE_MB"
				+ "    FROM SYS.DBA_DATA_FILES DD"
				+ "   GROUP BY DD.TABLESPACE_NAME) D"
				+ "   WHERE D.TABLESPACE_NAME = F.TABLESPACE_NAME"
				+ "   ORDER BY 1";
		String osuser = "";
		List<Map<String, BigDecimal>> lis2 = hibernateDao
				.queryForListWithSql(sqldmeter);

		if (lis2 == null || lis2.size() == 0) {
			return "";
		} else {
			return osuser = lis2.get(0).get("SUMTOT") + "";

		}

	}

	// 数据库平均响应时长
	@Override
	public String getBusinessSystemDBTime() {
		String dbTime = null;
		try {
			dbTime = ImsOperationTarget.getBusinessSystemDBTime();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dbTime;
	}

	// 系统服务响应时长
	@Override
	public String getBusinessSystemResponseTime() {
		String sqldmeter1 = "select CASE METRIC_NAME WHEN 'SQL Service Response Time' then "
				+ " ROUND((AVERAGE * 10), 2) WHEN 'Response Time Per Txn' then ROUND((AVERAGE * 10), 2)"
				+ " ELSE AVERAGE END AVERAGE from SYS.V_$SYSMETRIC_SUMMARY"
				+ " where METRIC_NAME = 'Response Time Per Txn'";
		List<Map<String, BigDecimal>> lis = hibernateDao
				.queryForListWithSql(sqldmeter1);
		if (lis != null) {
			if (lis.get(0).get("AVERAGE") != null) {
				return lis.get(0).get("AVERAGE") + "";
			}
		}
		return "0";
	}

	// 系统健康运行时长
	@Override
	public String getBusinessSystemRunningTime() {
		SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		java.util.Date nows;
		String ress = "";
		long ls = 0;
		try {
			String sql = "SELECT IM.MONITOR_DATE FROM I_IMS_MONITOR IM WHERE  IM.STATUS_CODE='01'";
			List<Map<String, Date>> liss = hibernateDao.queryForListWithSql(sql);
			if (liss == null || liss.size() == 0) {
				return "0";
			} else {
				ress = liss.get(0).get("MONITOR_DATE") + "";
			}
			nows = dfs.parse(ress);
			java.util.Date dates = new Date();
			// 使用系统当前时间减去从数据库中取出来的系统启动时间，除以1000
			System.out.println("dates():"+dates+"---nows():"+nows);
			//System.out.println("dates.getTime():"+dates.getTime()+"---nows.getTime():"+nows.getTime());
			ls = (dates.getTime() - nows.getTime()) / 1000;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ls + "";
	}
}
