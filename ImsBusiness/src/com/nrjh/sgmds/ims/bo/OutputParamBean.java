package com.nrjh.sgmds.ims.bo;

import java.util.List;

/***
 * 
 * @author 王际金
 * 
 */

public class OutputParamBean {

	private String ldapid; // 填写统一目录用户名，若此用户没有目录用户名则暂填写自己系统中的登录名
	private String corporation; // 省公司名称或国家电网公司总部，若人员为省公司本部人员，则下面的SUBCOMPANY填写XXX省电力公司本部，省公司人员的情况下，BUREAU可不填，但标签必需保留。若为国家电网公司总部人员，则此处填写“国家电网公司总部”，下面的SUBCOMPANY和BUREAU均可不填，但这2个标签必需保留。
	private String subcompany; // --地市公司名称，若人员为地市公司本部人员，则下面的BUREAU填写XXX供电公司本部。
	private String bureau; // 县级单位名称
	private String department; // 部门名称
	private String name; // 用户名，不是账号，而是用户姓名
	private String isldapid; // 上述LDAPID是否为目录用户名，是填1，否填0
	private InputParamBean inputParamBean;
	private String status;// 执行状态
	private String message; // 执行的结果提示
	private String reason; // 若status的值为failure，reason节点才存在
	private String[] corporationId; // 地域代码
	private String apiName; // 指标名称
	private String apiValue;// 指标
	private String businessUserRegNum;// 注册用户数
	private String businessSystemOnlineNum;// 在线用户数
	private String businessDayLoginNum;// 日登录人数
	private String businessVisitCount;// 累计访问人次
	private String businessSystemSessionNum;// 回话连接数
	private String businessDataTableSpace;// 业务系统所占表空间
	private String businessSystemDBTime;// 数据库平均相应时长

	private List<OutputParamBean> businessSystemLoginRoll;// 日登录人员名单
	private List<OutputParamBean> businessSystemOnlineRoll;// 在线人员名单

	private String businessUserRegNumStr;// 注册用户数
	private String businessSystemOnlineNumStr;// 在线用户数
	private String businessDayLoginNumStr;// 日登录人数
	private String businessVisitCountStr;// 累计访问人次
	private String businessSystemSessionNumStr;// 回话连接数
	private String businessDataTableSpaceStr;// 业务系统所占表空间
	private String businessSystemDBTimeStr;// 数据库平均相应时长

	private String businessSystemLoginRollStr;// 日登录人员名单
	private String businessSystemOnlineRollStr;// 在线人员名单

	private String businessSystemResponseTimeStr;// 系统响应时长
	private String businessSystemResponseTime;// 系统响应时长
	private String businessSystemRunningTime;// 系统健康运行时长
	private String businessSystemRunningTimeStr;// 系统健康运行时长

	private String sigMeterDetectRate;// 单相电能表配送检定率
	private String thrMeterDetectRate;// 三相电能表配送检定率
	private String itDetectRate;// 低压电流互感器配送检定率
	private String itArriveBatchNum;// 低压电流互感器到货批次数
	private String sigMeterDetectRateStr;// 单相电能表配送检定率
	private String thrMeterDetectRateStr;// 三相电能表配送检定率
	private String itDetectRateStr;// 低压电流互感器配送检定率
	private String itArriveBatchNumStr;// 低压电流互感器到货批次数
	private String tmnlArriveBatchNum;// 采集终端到货批次数
	private String tmnlArriveBatchNumStr;// 采集终端到货批次数

	private String itDetectTaskNum;// 互感器检定任务单数
	private String tmnlDetectTaskNum;// 采集终端检定任务单数
	private String meterDistTaskNum;// 电能表配送任务单数
	private String itDistTaskNum;// 互感器配送任务单数
	private String tmnlDistTaskNum;// 采集终端配送任务单数

	
	private String itDetectTaskNumStr;// 互感器检定任务单数
	private String tmnlDetectTaskNumStr;// 采集终端检定任务单数
	private String meterDistTaskNumStr;// 电能表配送任务单数
	private String itDistTaskNumStr;// 互感器配送任务单数
	private String tmnlDistTaskNumStr;// 采集终端配送任务单数
	public String getLdapid() {
		return ldapid;
	}
	public void setLdapid(String ldapid) {
		this.ldapid = ldapid;
	}
	public String getCorporation() {
		return corporation;
	}
	public void setCorporation(String corporation) {
		this.corporation = corporation;
	}
	public String getSubcompany() {
		return subcompany;
	}
	public void setSubcompany(String subcompany) {
		this.subcompany = subcompany;
	}
	public String getBureau() {
		return bureau;
	}
	public void setBureau(String bureau) {
		this.bureau = bureau;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIsldapid() {
		return isldapid;
	}
	public void setIsldapid(String isldapid) {
		this.isldapid = isldapid;
	}
	public InputParamBean getInputParamBean() {
		return inputParamBean;
	}
	public void setInputParamBean(InputParamBean inputParamBean) {
		this.inputParamBean = inputParamBean;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String[] getCorporationId() {
		return corporationId;
	}
	public void setCorporationId(String[] corporationId) {
		this.corporationId = corporationId;
	}
	public String getApiName() {
		return apiName;
	}
	public void setApiName(String apiName) {
		this.apiName = apiName;
	}
	public String getApiValue() {
		return apiValue;
	}
	public void setApiValue(String apiValue) {
		this.apiValue = apiValue;
	}
	public String getBusinessUserRegNum() {
		return businessUserRegNum;
	}
	public void setBusinessUserRegNum(String businessUserRegNum) {
		this.businessUserRegNum = businessUserRegNum;
	}
	public String getBusinessSystemOnlineNum() {
		return businessSystemOnlineNum;
	}
	public void setBusinessSystemOnlineNum(String businessSystemOnlineNum) {
		this.businessSystemOnlineNum = businessSystemOnlineNum;
	}
	public String getBusinessDayLoginNum() {
		return businessDayLoginNum;
	}
	public void setBusinessDayLoginNum(String businessDayLoginNum) {
		this.businessDayLoginNum = businessDayLoginNum;
	}
	public String getBusinessVisitCount() {
		return businessVisitCount;
	}
	public void setBusinessVisitCount(String businessVisitCount) {
		this.businessVisitCount = businessVisitCount;
	}
	public String getBusinessSystemSessionNum() {
		return businessSystemSessionNum;
	}
	public void setBusinessSystemSessionNum(String businessSystemSessionNum) {
		this.businessSystemSessionNum = businessSystemSessionNum;
	}
	public String getBusinessDataTableSpace() {
		return businessDataTableSpace;
	}
	public void setBusinessDataTableSpace(String businessDataTableSpace) {
		this.businessDataTableSpace = businessDataTableSpace;
	}
	public String getBusinessSystemDBTime() {
		return businessSystemDBTime;
	}
	public void setBusinessSystemDBTime(String businessSystemDBTime) {
		this.businessSystemDBTime = businessSystemDBTime;
	}
	public List<OutputParamBean> getBusinessSystemLoginRoll() {
		return businessSystemLoginRoll;
	}
	public void setBusinessSystemLoginRoll(
			List<OutputParamBean> businessSystemLoginRoll) {
		this.businessSystemLoginRoll = businessSystemLoginRoll;
	}
	public List<OutputParamBean> getBusinessSystemOnlineRoll() {
		return businessSystemOnlineRoll;
	}
	public void setBusinessSystemOnlineRoll(
			List<OutputParamBean> businessSystemOnlineRoll) {
		this.businessSystemOnlineRoll = businessSystemOnlineRoll;
	}
	public String getBusinessUserRegNumStr() {
		return businessUserRegNumStr;
	}
	public void setBusinessUserRegNumStr(String businessUserRegNumStr) {
		this.businessUserRegNumStr = businessUserRegNumStr;
	}
	public String getBusinessSystemOnlineNumStr() {
		return businessSystemOnlineNumStr;
	}
	public void setBusinessSystemOnlineNumStr(String businessSystemOnlineNumStr) {
		this.businessSystemOnlineNumStr = businessSystemOnlineNumStr;
	}
	public String getBusinessDayLoginNumStr() {
		return businessDayLoginNumStr;
	}
	public void setBusinessDayLoginNumStr(String businessDayLoginNumStr) {
		this.businessDayLoginNumStr = businessDayLoginNumStr;
	}
	public String getBusinessVisitCountStr() {
		return businessVisitCountStr;
	}
	public void setBusinessVisitCountStr(String businessVisitCountStr) {
		this.businessVisitCountStr = businessVisitCountStr;
	}
	public String getBusinessSystemSessionNumStr() {
		return businessSystemSessionNumStr;
	}
	public void setBusinessSystemSessionNumStr(String businessSystemSessionNumStr) {
		this.businessSystemSessionNumStr = businessSystemSessionNumStr;
	}
	public String getBusinessDataTableSpaceStr() {
		return businessDataTableSpaceStr;
	}
	public void setBusinessDataTableSpaceStr(String businessDataTableSpaceStr) {
		this.businessDataTableSpaceStr = businessDataTableSpaceStr;
	}
	public String getBusinessSystemDBTimeStr() {
		return businessSystemDBTimeStr;
	}
	public void setBusinessSystemDBTimeStr(String businessSystemDBTimeStr) {
		this.businessSystemDBTimeStr = businessSystemDBTimeStr;
	}
	public String getBusinessSystemLoginRollStr() {
		return businessSystemLoginRollStr;
	}
	public void setBusinessSystemLoginRollStr(String businessSystemLoginRollStr) {
		this.businessSystemLoginRollStr = businessSystemLoginRollStr;
	}
	public String getBusinessSystemOnlineRollStr() {
		return businessSystemOnlineRollStr;
	}
	public void setBusinessSystemOnlineRollStr(String businessSystemOnlineRollStr) {
		this.businessSystemOnlineRollStr = businessSystemOnlineRollStr;
	}
	public String getBusinessSystemResponseTimeStr() {
		return businessSystemResponseTimeStr;
	}
	public void setBusinessSystemResponseTimeStr(
			String businessSystemResponseTimeStr) {
		this.businessSystemResponseTimeStr = businessSystemResponseTimeStr;
	}
	public String getBusinessSystemResponseTime() {
		return businessSystemResponseTime;
	}
	public void setBusinessSystemResponseTime(String businessSystemResponseTime) {
		this.businessSystemResponseTime = businessSystemResponseTime;
	}
	public String getBusinessSystemRunningTime() {
		return businessSystemRunningTime;
	}
	public void setBusinessSystemRunningTime(String businessSystemRunningTime) {
		this.businessSystemRunningTime = businessSystemRunningTime;
	}
	public String getBusinessSystemRunningTimeStr() {
		return businessSystemRunningTimeStr;
	}
	public void setBusinessSystemRunningTimeStr(String businessSystemRunningTimeStr) {
		this.businessSystemRunningTimeStr = businessSystemRunningTimeStr;
	}
	public String getSigMeterDetectRate() {
		return sigMeterDetectRate;
	}
	public void setSigMeterDetectRate(String sigMeterDetectRate) {
		this.sigMeterDetectRate = sigMeterDetectRate;
	}
	public String getThrMeterDetectRate() {
		return thrMeterDetectRate;
	}
	public void setThrMeterDetectRate(String thrMeterDetectRate) {
		this.thrMeterDetectRate = thrMeterDetectRate;
	}
	public String getItDetectRate() {
		return itDetectRate;
	}
	public void setItDetectRate(String itDetectRate) {
		this.itDetectRate = itDetectRate;
	}
	public String getItArriveBatchNum() {
		return itArriveBatchNum;
	}
	public void setItArriveBatchNum(String itArriveBatchNum) {
		this.itArriveBatchNum = itArriveBatchNum;
	}
	public String getSigMeterDetectRateStr() {
		return sigMeterDetectRateStr;
	}
	public void setSigMeterDetectRateStr(String sigMeterDetectRateStr) {
		this.sigMeterDetectRateStr = sigMeterDetectRateStr;
	}
	public String getThrMeterDetectRateStr() {
		return thrMeterDetectRateStr;
	}
	public void setThrMeterDetectRateStr(String thrMeterDetectRateStr) {
		this.thrMeterDetectRateStr = thrMeterDetectRateStr;
	}
	public String getItDetectRateStr() {
		return itDetectRateStr;
	}
	public void setItDetectRateStr(String itDetectRateStr) {
		this.itDetectRateStr = itDetectRateStr;
	}
	public String getItArriveBatchNumStr() {
		return itArriveBatchNumStr;
	}
	public void setItArriveBatchNumStr(String itArriveBatchNumStr) {
		this.itArriveBatchNumStr = itArriveBatchNumStr;
	}
	public String getTmnlArriveBatchNum() {
		return tmnlArriveBatchNum;
	}
	public void setTmnlArriveBatchNum(String tmnlArriveBatchNum) {
		this.tmnlArriveBatchNum = tmnlArriveBatchNum;
	}
	public String getTmnlArriveBatchNumStr() {
		return tmnlArriveBatchNumStr;
	}
	public void setTmnlArriveBatchNumStr(String tmnlArriveBatchNumStr) {
		this.tmnlArriveBatchNumStr = tmnlArriveBatchNumStr;
	}
	public String getItDetectTaskNum() {
		return itDetectTaskNum;
	}
	public void setItDetectTaskNum(String itDetectTaskNum) {
		this.itDetectTaskNum = itDetectTaskNum;
	}
	public String getTmnlDetectTaskNum() {
		return tmnlDetectTaskNum;
	}
	public void setTmnlDetectTaskNum(String tmnlDetectTaskNum) {
		this.tmnlDetectTaskNum = tmnlDetectTaskNum;
	}
	public String getMeterDistTaskNum() {
		return meterDistTaskNum;
	}
	public void setMeterDistTaskNum(String meterDistTaskNum) {
		this.meterDistTaskNum = meterDistTaskNum;
	}
	public String getItDistTaskNum() {
		return itDistTaskNum;
	}
	public void setItDistTaskNum(String itDistTaskNum) {
		this.itDistTaskNum = itDistTaskNum;
	}
	public String getTmnlDistTaskNum() {
		return tmnlDistTaskNum;
	}
	public void setTmnlDistTaskNum(String tmnlDistTaskNum) {
		this.tmnlDistTaskNum = tmnlDistTaskNum;
	}
	public String getItDetectTaskNumStr() {
		return itDetectTaskNumStr;
	}
	public void setItDetectTaskNumStr(String itDetectTaskNumStr) {
		this.itDetectTaskNumStr = itDetectTaskNumStr;
	}
	public String getTmnlDetectTaskNumStr() {
		return tmnlDetectTaskNumStr;
	}
	public void setTmnlDetectTaskNumStr(String tmnlDetectTaskNumStr) {
		this.tmnlDetectTaskNumStr = tmnlDetectTaskNumStr;
	}
	public String getMeterDistTaskNumStr() {
		return meterDistTaskNumStr;
	}
	public void setMeterDistTaskNumStr(String meterDistTaskNumStr) {
		this.meterDistTaskNumStr = meterDistTaskNumStr;
	}
	public String getItDistTaskNumStr() {
		return itDistTaskNumStr;
	}
	public void setItDistTaskNumStr(String itDistTaskNumStr) {
		this.itDistTaskNumStr = itDistTaskNumStr;
	}
	public String getTmnlDistTaskNumStr() {
		return tmnlDistTaskNumStr;
	}
	public void setTmnlDistTaskNumStr(String tmnlDistTaskNumStr) {
		this.tmnlDistTaskNumStr = tmnlDistTaskNumStr;
	}
	
}
