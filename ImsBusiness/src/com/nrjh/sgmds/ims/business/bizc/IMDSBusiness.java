package com.nrjh.sgmds.ims.business.bizc;

import java.util.List;


/**
 *  @author 王际金
 *
 */
public interface IMDSBusiness {

	/**
	 * 获取注册用户数
	 * 
	 * @return
	 */
	public String getRegisterUserNum();
	
	/**
	 * 获取在线用户数
	 * 
	 * @return
	 */
	public String getOnLineUserNum();
	
	/**
	 * 获取日登录人数
	 * 
	 * @return
	 */
	public String getTodayLoginUserNum();
	
	/**
	 * 获取累计访问人次
	 * 
	 * @return
	 */
	public String getVisitUserNum();
	
	/**
	 * 获取日登录人员名单
	 * 
	 * @return
	 */
	public List<?> getRegisterUserInfo();
	
	/**
	 * 在线人员名单
	 * 
	 * @return
	 */
	public List<?> getOnLineUserInfo();
	
	/**
	 * 获取回话连接数
	 * 
	 * @return
	 */
	public String getBusinessSystemSessionNum();
	
	/**
	 * 获取业务系统所用表空间大小（单位：M）
	 * 
	 * @return
	 */
	public String geBusinessDataTableSpace();
	
	/**
	 * 获取数据库平均响应时长
	 * 
	 * @return
	 */
	public String getBusinessSystemDBTime();
	
	/**
	 * 单相电能表配送检定率
	 * 
	 * @return
	 */
	public String getSigMeterDetectRate();
	
	/**
	 * 三相电能表配送检定率 
	 * 
	 * @return
	 */
	public String getThrMeterDetectRate();
	
	/**
	 * 低压电流互感器配送检定率
	 * 
	 * @return
	 */
	public String getItDetectRate();
	
	/**
	 * 低压电流互感器到货批次数
	 * 
	 * @return
	 */
	public String getItArriveBatchNum();
	
	/**
	 * 采集终端到货批次数
	 * 
	 * @return
	 */
	public String getTmnlArriveBatchNum();
	
	/**
	 * 互感器检定任务单数
	 * 
	 * @return
	 */
	public String getItDetectTaskNum();
	
	/**
	 * 采集终端检定任务单数
	 * 
	 * @return
	 */
	public String getTmnlDetectTaskNum();
	
	/**
	 * 电能表配送任务单数
	 * 
	 * @return
	 */
	public String getMeterDistTaskNum();
	
	/**
	 * 互感器配送任务单数
	 * 
	 * @return
	 */
	public String getItDistTaskNum();
	
	/**
	 * 采集终端配送任务单数
	 * 
	 * @return
	 */
	public String getTmnlDistTaskNum();
	
	/**
	 * 获取地域代码
	 * 
	 * @return
	 */
	public String getCorporationCode();
	
	/**
	 * 获取系统响应时间
	 * 
	 * @return
	 */
	public String getBusinessSystemResponseTime();
	
	/**
	 * 获取系统健康运行时长
	 * 
	 * @return
	 */
	public String getBusinessSystemRunningTime();
	
	
	public String getIscRunQuota(int quotaType);
	

}
