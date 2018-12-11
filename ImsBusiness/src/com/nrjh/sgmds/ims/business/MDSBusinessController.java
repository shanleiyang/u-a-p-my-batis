package com.nrjh.sgmds.ims.business;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nrjh.sgmds.ims.bo.InputParamBean;
import com.nrjh.sgmds.ims.bo.OutputParamBean;
import com.nrjh.sgmds.ims.business.bizc.IMDSBusiness;
import com.nrjh.sgmds.ims.util.XMLUtil;
@Controller
@RequestMapping("/iscresourse") 
public class MDSBusinessController {
	private static Log log = LogFactory.getLog(MDSBusinessController.class);
	private static ThreadPoolExecutor taskPool = (ThreadPoolExecutor) Executors.newFixedThreadPool(20);
	
    @Autowired
    private IMDSBusiness MDSBusinessBizc;
    
	//在调用日登陆人员名单或在线用户名单时，先要调用此方法，验证数据是否准备完好
	@RequestMapping("/getStatus")
	public @ResponseBody
	String getStatus(@RequestParam(value = "xml") String xml)
	{
		String status="0";
		InputParamBean inputParamBean = new InputParamBean();
		inputParamBean = XMLUtil.readInputXML(xml);
		List apivalues = inputParamBean.getIndexName();
		for (int i = 0; i < apivalues.size(); i++) {
			String apivalue = apivalues.get(i).toString();
			if (apivalue.equals("BusinessSystemLoginRoll") || apivalue.equals("BusinessSystemOnlineRoll")) {// 日登录人员名单或者在线用户名单
				status="1";
				break;
			}
		}
		return status;
	}
	
	@RequestMapping("/getKPIValue")
	public @ResponseBody
	String  getKPIValue(@RequestParam(value = "xml") String xml)
	{
		int sta = 0;
		OutputParamBean outPutParamBean = new OutputParamBean();
		InputParamBean inputParamBean = new InputParamBean();
		inputParamBean = XMLUtil.readInputXML(xml);

		List apivalues = inputParamBean.getIndexName();
		outPutParamBean.setInputParamBean(inputParamBean);
		Map<String, List> map = new HashMap<String, List>();
		String corporationCode = MDSBusinessBizc.getCorporationCode();
		String[] croCode = inputParamBean.getCorporation();
		for (int i = 0; i < croCode.length; i++) {
			if (corporationCode.equals(croCode[i])) {
				sta = 1;
				break;
			}
		}
		if (sta == 0) {
			outPutParamBean.setStatus("failure");
			outPutParamBean.setMessage("查询失败");
			outPutParamBean.setReason("地域代码不存在");
//			log.error("--------地域代码不存在---------");
			return XMLUtil.returnXML(outPutParamBean);
		} else {
			//记录正在执行的任务
			Map<String, Future<String>> futures = new HashMap<String, Future<String>>();
			
			for (int i = 0; i < apivalues.size(); i++) {
				String apivalue = apivalues.get(i).toString();
				Future<String> taskFuture = taskPool.submit(new MyTask(MDSBusinessBizc, apivalue));
				futures.put(apivalue, taskFuture);
			}
			
			Iterator<String> keyIts = futures.keySet().iterator();
			while(keyIts.hasNext()) {
				String apivalue = keyIts.next();
				if (apivalue.equals("BusinessUserRegNum")) {// 注册用户数
					outPutParamBean.setBusinessUserRegNumStr("BusinessUserRegNum");
					String userNum = null;
					try {
						userNum = futures.get(apivalue).get(1, TimeUnit.SECONDS);
					} catch (Exception e) {
						log.error("##BusinessUserRegNum ", e);
						userNum = "0";
					}
					if (userNum != null && !"".equals(userNum)) {
						outPutParamBean.setBusinessUserRegNum(userNum);
					}
				}else if (apivalue.equals("BusinessSystemOnlineNum")) {// 在线用户数
					outPutParamBean
							.setBusinessSystemOnlineNumStr("BusinessSystemOnlineNum");
					String onLineUserNum = null;
					try {
						onLineUserNum = futures.get(apivalue).get(200, TimeUnit.MILLISECONDS);
					} catch (Exception e) {
						log.error("##BusinessSystemOnlineNum ", e);
						onLineUserNum = "0";
					}
					if (onLineUserNum != null && !"".equals(onLineUserNum)) {
						outPutParamBean
								.setBusinessSystemOnlineNum(onLineUserNum);
					}
				}else if (apivalue.equals("BusinessDayLoginNum")) {// 日登录人数
					outPutParamBean
							.setBusinessDayLoginNumStr("BusinessDayLoginNum");
					String dayLoginNum = null;
					try {
						dayLoginNum = futures.get(apivalue).get(200, TimeUnit.MILLISECONDS);
					} catch (Exception e) {
						log.error("##BusinessDayLoginNum ", e);
						dayLoginNum = "0";
					}
					if (dayLoginNum != null && !"".equals(dayLoginNum)) {
						outPutParamBean.setBusinessDayLoginNum(dayLoginNum);
					}
				}else if (apivalue.equals("BusinessVisitCount")) {// 累计访问人次
					outPutParamBean
							.setBusinessVisitCountStr("BusinessVisitCount");
					String visitCount = null;
					try {
						visitCount = futures.get(apivalue).get(200, TimeUnit.MILLISECONDS);
					} catch (Exception e) {
						log.error("##BusinessVisitCount ", e);
						visitCount = "0";
					}
					if (visitCount != null && !"".equals(visitCount)) {
						outPutParamBean.setBusinessVisitCount(visitCount);
					}
				}else if (apivalue.equals("BusinessSystemResponseTime")) {// 系统服务响应时长
					String resBonse = null;
					try {
						resBonse = futures.get(apivalue).get(500, TimeUnit.MILLISECONDS);
					} catch (Exception e) {
						log.error("##BusinessSystemResponseTime ", e);
						resBonse = "0";
					}
					outPutParamBean
							.setBusinessSystemResponseTimeStr("BusinessSystemResponseTime");
					outPutParamBean.setBusinessSystemResponseTime(resBonse);
				}else if (apivalue.equals("BusinessSystemRunningTime")) {// 系统健康运行时长
					String runTime = null;
					try {
						runTime = futures.get(apivalue).get(500, TimeUnit.MILLISECONDS);
					} catch (Exception e) {
						log.error("##BusinessSystemRunningTime ", e);
						runTime = "0";
					}
					outPutParamBean.setBusinessSystemRunningTimeStr("businessSystemRunningTime");
					outPutParamBean.setBusinessSystemRunningTime(runTime);
				}else if (apivalue.equals("BusinessSystemSessionNum")) {// 会话连接数
					outPutParamBean
							.setBusinessSystemSessionNumStr("BusinessSystemSessionNum");
					String systemSessionNum = null;
					try {
						systemSessionNum = futures.get(apivalue).get(200, TimeUnit.MILLISECONDS);
					} catch (Exception e) {
						log.error("##BusinessSystemSessionNum ", e);
						systemSessionNum = "0";
					}
					if (systemSessionNum != null
							&& !"".equals(systemSessionNum)) {
						outPutParamBean
								.setBusinessSystemSessionNum(systemSessionNum);
					}
				}else if (apivalue.equals("BusinessDataTableSpace")) {// 业务系统表空间使用情况
					outPutParamBean
							.setBusinessDataTableSpaceStr("BusinessDataTableSpace");
					String dataTableSpace = null;
					try {
						dataTableSpace = futures.get(apivalue).get(1, TimeUnit.SECONDS);
					} catch (Exception e) {
						log.error("##BusinessDataTableSpace ", e);
						dataTableSpace = "0";
					}
					if (dataTableSpace != null && !"".equals(dataTableSpace)) {
						outPutParamBean
								.setBusinessDataTableSpace(dataTableSpace);
					}
				}else if (apivalue.equals("BusinessSystemDBTime")) {// 数据库平均响应时长
					outPutParamBean.setBusinessSystemDBTimeStr("BusinessSystemDBTime");
					String sysDBtime = null;
					try {
						sysDBtime = futures.get(apivalue).get(500, TimeUnit.MILLISECONDS);
					} catch (Exception e) {
						log.error("##BusinessSystemDBTime ", e);
						sysDBtime = "0";
					}
					if (sysDBtime != null && !"".equals(sysDBtime)) {
						outPutParamBean.setBusinessSystemDBTime(sysDBtime);
					}
				}else if (apivalue.equals("SigMeterDetectRate")) {// 单相电能表配送检定率
					outPutParamBean
							.setSigMeterDetectRateStr("SigMeterDetectRate");
					String res = null;
					try {
						res = futures.get(apivalue).get(200, TimeUnit.MILLISECONDS);
					} catch (Exception e) {
						log.error("##SigMeterDetectRate ", e);
						res = "0";
					}
					if (res != null && !"".equals(res)) {
						outPutParamBean.setSigMeterDetectRate(res);
					} else {
						outPutParamBean.setSigMeterDetectRate("0");
					}
				}else if (apivalue.equals("ThrMeterDetectRate")) {// 三相电能表配送检定率
					outPutParamBean
							.setThrMeterDetectRateStr("ThrMeterDetectRate");
					String res = null;
					try {
						res = futures.get(apivalue).get(200, TimeUnit.MILLISECONDS);
					} catch (Exception e) {
						log.error("##ThrMeterDetectRate ", e);
						res = "0";
					}
					if (res != null && !"".equals(res)) {
						outPutParamBean.setThrMeterDetectRate(res);
					} else {
						outPutParamBean.setThrMeterDetectRate("0");
					}
				}else if (apivalue.equals("ItDetectRate")) {// 低压电流互感器配送检定率
					outPutParamBean.setItDetectRateStr("ItDetectRate");
					String res = null;
					try {
						res = futures.get(apivalue).get(200, TimeUnit.MILLISECONDS);
					} catch (Exception e) {
						log.error("##ItDetectRate ", e);
						res = "0";
					}
					if (res != null && !"".equals(res)) {
						outPutParamBean.setItDetectRate(res);
					} else {
						outPutParamBean.setItDetectRate("0");
					}
				}else if (apivalue.equals("ItArriveBatchNum")) {// 低压电流互感器到货批次数
					outPutParamBean.setItArriveBatchNumStr("ItArriveBatchNum");
					String res = null;
					try {
						res = futures.get(apivalue).get(200, TimeUnit.MILLISECONDS);
					} catch (Exception e) {
						log.error("##ItArriveBatchNum ", e);
						res = "0";
					}
					if (res != null && !"".equals(res)) {
						outPutParamBean.setItArriveBatchNum(res);
					} else {
						outPutParamBean.setItArriveBatchNum("0");
					}
				}else if (apivalue.equals("TmnlArriveBatchNum")) {// 电能表检定任务单数
					outPutParamBean
							.setTmnlArriveBatchNumStr("TmnlArriveBatchNum");
					String res = null;
					try {
						res = futures.get(apivalue).get(200, TimeUnit.MILLISECONDS);
					} catch (Exception e) {
						log.error("##TmnlArriveBatchNum ", e);
						res = "0";
					}
					if (res != null && !"".equals(res)) {
						outPutParamBean.setTmnlArriveBatchNum(res);
					} else {
						outPutParamBean.setTmnlArriveBatchNum("0");
					}
				}else if (apivalue.equals("ItDetectTaskNum")) {// 互感器检定任务单数
					outPutParamBean.setItDetectTaskNumStr("ItDetectTaskNum");
					String res = null;
					try {
						res = futures.get(apivalue).get(200, TimeUnit.MILLISECONDS);
					} catch (Exception e) {
						log.error("##ItDetectTaskNum ", e);
						res = "0";
					}
					if (res != null && !"".equals(res)) {
						outPutParamBean.setItDetectTaskNum(res);
					} else {
						outPutParamBean.setItDetectTaskNum("0");
					}
				}else if (apivalue.equals("TmnlDetectTaskNum")) {// 采集终端检定任务单数
					outPutParamBean
							.setTmnlDetectTaskNumStr("TmnlDetectTaskNum");
					String res = null;
					try {
						res = futures.get(apivalue).get(200, TimeUnit.MILLISECONDS);
					} catch (Exception e) {
						log.error("##TmnlDetectTaskNum ", e);
						res = "0";
					}
					if (res != null && !"".equals(res)) {
						outPutParamBean.setTmnlDetectTaskNum(res);
					} else {
						outPutParamBean.setTmnlDetectTaskNum("0");
					}
				}else if (apivalue.equals("MeterDistTaskNum")) {// 电能表配送任务单数
					outPutParamBean.setMeterDistTaskNumStr("MeterDistTaskNum");
					String res = null;
					try {
						res = futures.get(apivalue).get(200, TimeUnit.MILLISECONDS);
					} catch (Exception e) {
						log.error("##MeterDistTaskNum ", e);
						res = "0";
					}
					if (res != null && !"".equals(res)) {
						outPutParamBean.setMeterDistTaskNum(res);
					} else {
						outPutParamBean.setMeterDistTaskNum("0");
					}
				}else if (apivalue.equals("ItDistTaskNum")) {// 互感器配送任务单数
					outPutParamBean.setItDistTaskNumStr("ItDistTaskNum");
					String res = null;
					try {
						res = futures.get(apivalue).get(200, TimeUnit.MILLISECONDS);
					} catch (Exception e) {
						log.error("##ItDistTaskNum ", e);
						res = "0";
					}
					if (res != null && !"".equals(res)) {
						outPutParamBean.setItDistTaskNum(res);
					} else {
						outPutParamBean.setItDistTaskNum("0");
					}
				}else if (apivalue.equals("TmnlDistTaskNum")) {// 采集终端配送任务单数
					outPutParamBean.setTmnlDistTaskNumStr("TmnlDistTaskNum");
					String res = null;
					try {
						res = futures.get(apivalue).get(200, TimeUnit.MILLISECONDS);
					} catch (Exception e) {
						log.error("##TmnlDistTaskNum ", e);
						res = "0";
					}
					if (res != null && !"".equals(res)) {
						outPutParamBean.setTmnlDistTaskNum(res);
					} else {
						outPutParamBean.setTmnlDistTaskNum("0");
					}
				}
			}
			
			return XMLUtil.returnXML(outPutParamBean);
		}
	}
	
	class MyTask implements Callable<String> {
		private IMDSBusiness MDSBusinessBizc;
		private String apivalue;
		
		public MyTask(IMDSBusiness MDSBusinessBizc, String apivalue) {
			this.MDSBusinessBizc = MDSBusinessBizc;
			this.apivalue = apivalue;
		}
		
		@Override
		public String call() {
			String res = null;
			try {
				if (apivalue.equals("BusinessUserRegNum")) {// 注册用户数
					res = MDSBusinessBizc.getRegisterUserNum();
				}else if (apivalue.equals("BusinessSystemOnlineNum")) {// 在线用户数
					res = MDSBusinessBizc.getIscRunQuota(2);//getOnLineUserNum();
				}else if (apivalue.equals("BusinessDayLoginNum")) {// 日登录人数
					res = MDSBusinessBizc.getIscRunQuota(3);//getTodayLoginUserNum();
				}else if (apivalue.equals("BusinessVisitCount")) {// 累计访问人次
					res = MDSBusinessBizc.getIscRunQuota(4);//getVisitUserNum();
				}else if (apivalue.equals("BusinessSystemResponseTime")) {// 系统服务响应时长
					res = MDSBusinessBizc.getIscRunQuota(6);
				}else if (apivalue.equals("BusinessSystemRunningTime")) {// 系统健康运行时长
					res = MDSBusinessBizc.getBusinessSystemRunningTime();
				}else if (apivalue.equals("BusinessSystemSessionNum")) {// 会话连接数
					res = MDSBusinessBizc.getIscRunQuota(5);
				}else if (apivalue.equals("BusinessDataTableSpace")) {// 业务系统表空间使用情况
					res = MDSBusinessBizc
							.geBusinessDataTableSpace();
				}else if (apivalue.equals("BusinessSystemDBTime")) {// 数据库平均响应时长
					res = MDSBusinessBizc.getBusinessSystemResponseTime();
				}else if (apivalue.equals("SigMeterDetectRate")) {// 单相电能表配送检定率
					res = MDSBusinessBizc.getSigMeterDetectRate();
				}else if (apivalue.equals("ThrMeterDetectRate")) {// 三相电能表配送检定率
					res = MDSBusinessBizc.getThrMeterDetectRate();
				}else if (apivalue.equals("ItDetectRate")) {// 低压电流互感器配送检定率
					res = MDSBusinessBizc.getItDetectRate();
				}else if (apivalue.equals("ItArriveBatchNum")) {// 低压电流互感器到货批次数
					res = MDSBusinessBizc.getItArriveBatchNum();
				}else if (apivalue.equals("TmnlArriveBatchNum")) {// 电能表检定任务单数
					res = MDSBusinessBizc.getTmnlArriveBatchNum();
				}else if (apivalue.equals("ItDetectTaskNum")) {// 互感器检定任务单数
					res = MDSBusinessBizc.getItDetectTaskNum();
				}else if (apivalue.equals("TmnlDetectTaskNum")) {// 采集终端检定任务单数
					res = MDSBusinessBizc.getTmnlDetectTaskNum();
				}else if (apivalue.equals("MeterDistTaskNum")) {// 电能表配送任务单数
					res = MDSBusinessBizc.getMeterDistTaskNum();
				}else if (apivalue.equals("ItDistTaskNum")) {// 互感器配送任务单数
					res = MDSBusinessBizc.getItDistTaskNum();
				}else if (apivalue.equals("TmnlDistTaskNum")) {// 采集终端配送任务单数
					res = MDSBusinessBizc.getTmnlDistTaskNum();
				}
			} catch (Exception e) {
				log.error("##MyTask.call ", e);
			}
			
			return res;
		}
		
	}
}
