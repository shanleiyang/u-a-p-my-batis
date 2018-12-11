package com.nrjh.sgmds.ims.util;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.DocumentHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.nrjh.sgmds.ims.bo.InputParamBean;
import com.nrjh.sgmds.ims.bo.OutputParamBean;

/***
 * 
 * @author 王际金
 * 
 */
public class XMLUtil {
	private static Log log = LogFactory.getLog(XMLUtil.class);
	/***
	 * 读取接收XML参数
	 * 
	 * @param xml
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static InputParamBean readInputXML(String xml) {
		InputParamBean inputParamBean = new InputParamBean();
		List apis = new ArrayList();
		try {

			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(new InputSource(new StringReader(xml)));

			NodeList corList = doc.getElementsByTagName("CorporationCode");
			NodeList timList = doc.getElementsByTagName("Time");
			NodeList apiList = doc.getElementsByTagName("api");
			String corporaTion = corList.item(0).getFirstChild().getNodeValue();
			String time = timList.item(0).getFirstChild().getNodeValue();

			if (corporaTion != null) {
				String[] corporaTions = corporaTion.split(",");
				inputParamBean.setCorporation(corporaTions);
			}
			if (time != null) {
				inputParamBean.setTime(time);
				String getTime = timList.item(0).getFirstChild().getNodeValue();
				StringBuffer baoWen = new StringBuffer("\n*************探测时间:");
				baoWen.append(getTime).append("**************************************\n");
				baoWen.append("接收的报文内容：\n").append(xml);
				log.debug(baoWen.toString());
				System.out.println(baoWen.toString());
				for (int i = 0; i < apiList.getLength(); i++) {
					Node api = apiList.item(i);
					Element elem = (Element) api;
					apis.add(elem.getAttribute("name"));
				}
				inputParamBean.setIndexName(apis);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.debug("接收报文出错:"+e.getMessage());
		}
		return inputParamBean;
	}

	/***
	 * 根据接收参数和数据返回XML格式的字符串
	 * 
	 * @param bean
	 * @return
	 */
	@SuppressWarnings("unused")
	public static String returnXML(OutputParamBean bean) {
		org.dom4j.Document doc = DocumentHelper.createDocument();
		try {
			doc.setXMLEncoding("UTF-8");// encoding="gb2312" 要求
			// List list = bean.getInputParamBean().getIndexName();
			// 判断是否成功
			if (bean.getStatus() != null && bean.getStatus().equals("failure")) {
				org.dom4j.Element rootEle = doc.addElement("return");
				org.dom4j.Element succEle = rootEle.addElement("status");
				succEle.addText(bean.getStatus());
				org.dom4j.Element detailEle = rootEle.addElement("message");
				detailEle.addText(bean.getMessage());

				org.dom4j.Element resolEle = rootEle.addElement("reason");
				resolEle.addText(bean.getReason());
				
			} else {
				// 对所传字段进行校验并拼接xml字段（名单单独调用）
				if ((bean.getBusinessSystemLoginRollStr() == null && bean.getBusinessSystemOnlineRollStr() == null)) {
					org.dom4j.Element rootEle = doc.addElement("return");

					org.dom4j.Element succEle = rootEle.addElement("status");
					succEle.addText("success");
					org.dom4j.Element detailEle = rootEle.addElement("message");
					detailEle.addText(bean == null ? "IMS系统错误" : "执行成功");
					// InputParamBean input=bean.getInputParamBean();
					// bean.setInputParamBean(new InputParamBean());
					if (bean == null) {
						org.dom4j.Element resolEle = rootEle.addElement("reason");
						resolEle.addText("查询数据为空");
					}// else{
						// bean.setInputParamBean(input);
					// }
					for (int i = 0; i < bean.getInputParamBean().getCorporation().length; i++) {
						org.dom4j.Element corpEle = rootEle.addElement("Corporation");
						corpEle.addAttribute("id", bean.getInputParamBean().getCorporation()[i]);
						if (bean.getBusinessUserRegNumStr() != null) {// 注册用户数
							org.dom4j.Element apiEle = corpEle.addElement("api");
							apiEle.addAttribute("name", "BusinessUserRegNum");
							org.dom4j.Element valueEle = apiEle.addElement("value");
							valueEle.setText(bean.getBusinessUserRegNum() == null ? "" : bean.getBusinessUserRegNum());
						}
						if (bean.getBusinessSystemOnlineNumStr() != null) {// 在线用户数
							org.dom4j.Element apiEle = corpEle.addElement("api");
							apiEle.addAttribute("name", "BusinessSystemOnlineNum");
							org.dom4j.Element valueEle = apiEle.addElement("value");
							valueEle.setText(bean.getBusinessSystemOnlineNum() == null ? "" : bean.getBusinessSystemOnlineNum());
						}
						if (bean.getBusinessDayLoginNumStr() != null) {// 日登录人数
							org.dom4j.Element apiEle = corpEle.addElement("api");
							apiEle.addAttribute("name", "BusinessDayLoginNum");
							org.dom4j.Element valueEle = apiEle.addElement("value");
							valueEle.setText(bean.getBusinessDayLoginNum() == null ? "" : bean.getBusinessDayLoginNum());
						}
						if (bean.getBusinessVisitCountStr() != null) {// 累计访问人次
							org.dom4j.Element apiEle = corpEle.addElement("api");
							apiEle.addAttribute("name", "BusinessVisitCount");
							org.dom4j.Element valueEle = apiEle.addElement("value");
							valueEle.setText(bean.getBusinessVisitCount() == null ? "" : bean.getBusinessVisitCount());
						}

						if (bean.getBusinessSystemResponseTimeStr() != null) {// 系统响应时间
							org.dom4j.Element apiEle = corpEle.addElement("api");
							apiEle.addAttribute("name", "BusinessSystemResponseTime");
							org.dom4j.Element valueEle = apiEle.addElement("value");
							valueEle.setText(bean.getBusinessSystemResponseTime() == null ? "" : bean.getBusinessSystemResponseTime());
						}

						if (bean.getBusinessSystemSessionNumStr() != null) {// 会话连接数
							org.dom4j.Element apiEle = corpEle.addElement("api");
							apiEle.addAttribute("name", "BusinessSystemSessionNum");
							org.dom4j.Element valueEle = apiEle.addElement("value");
							valueEle.setText(bean.getBusinessSystemSessionNum() == null ? "" : bean.getBusinessSystemSessionNum());
						}
						if (bean.getBusinessDataTableSpaceStr() != null) {// 业务系统表空间使用情况
							org.dom4j.Element apiEle = corpEle.addElement("api");
							apiEle.addAttribute("name", "BusinessDataTableSpace");
							org.dom4j.Element valueEle = apiEle.addElement("value");
							valueEle.setText(bean.getBusinessDataTableSpace() == null ? "" : bean.getBusinessDataTableSpace());
						}
						if (bean.getBusinessSystemDBTimeStr() != null) {// 数据库平均响应时长
							org.dom4j.Element apiEle = corpEle.addElement("api");
							apiEle.addAttribute("name", "BusinessSystemDBTime");
							org.dom4j.Element valueEle = apiEle.addElement("value");
							valueEle.setText(bean.getBusinessSystemDBTime() == null ? "" : bean.getBusinessSystemDBTime());
						}
						if (bean.getBusinessSystemRunningTimeStr() != null) {// 系统健康运行时长
							org.dom4j.Element apiEle = corpEle.addElement("api");
							apiEle.addAttribute("name", "BusinessSystemRunningTime");
							org.dom4j.Element valueEle = apiEle.addElement("value");
							valueEle.setText(bean.getBusinessSystemRunningTime() == null ? "" : bean.getBusinessSystemRunningTime());
						}

						if (bean.getSigMeterDetectRateStr() != null) {// 单相电能表配送检定率
							org.dom4j.Element apiEle = corpEle.addElement("api");
							apiEle.addAttribute("name", "SigMeterDetectRate");
							org.dom4j.Element valueEle = apiEle.addElement("value");
							valueEle.setText(bean.getSigMeterDetectRate() == null ? "" : bean.getSigMeterDetectRate());

						}
						if (bean.getThrMeterDetectRateStr() != null) {// 三相电能表配送检定率
							org.dom4j.Element apiEle = corpEle.addElement("api");
							apiEle.addAttribute("name", "ThrMeterDetectRate");
							org.dom4j.Element valueEle = apiEle.addElement("value");
							valueEle.setText(bean.getThrMeterDetectRate() == null ? "" : bean.getThrMeterDetectRate());

						}
						if (bean.getItDetectRateStr() != null) {// 低压电流互感器配送检定率
							org.dom4j.Element apiEle = corpEle.addElement("api");
							apiEle.addAttribute("name", "ItDetectRate");
							org.dom4j.Element valueEle = apiEle.addElement("value");
							valueEle.setText(bean.getItDetectRate() == null ? "" : bean.getItDetectRate());

						}
						if (bean.getItArriveBatchNumStr() != null) {// 低压电流互感器到货批次数
							org.dom4j.Element apiEle = corpEle.addElement("api");
							apiEle.addAttribute("name", "ItArriveBatchNum");
							org.dom4j.Element valueEle = apiEle.addElement("value");
							valueEle.setText(bean.getItArriveBatchNum() == null ? "" : bean.getItArriveBatchNum());

						}
						if (bean.getTmnlArriveBatchNumStr() != null) {// 采集终端到货批次数
							org.dom4j.Element apiEle = corpEle.addElement("api");
							apiEle.addAttribute("name", "TmnlArriveBatchNum");
							org.dom4j.Element valueEle = apiEle.addElement("value");
							valueEle.setText(bean.getTmnlArriveBatchNum() == null ? "" : bean.getTmnlArriveBatchNum());

						}
						if (bean.getItDetectTaskNumStr() != null) {// 互感器检定任务单数
							org.dom4j.Element apiEle = corpEle.addElement("api");
							apiEle.addAttribute("name", "ItDetectTaskNum");
							org.dom4j.Element valueEle = apiEle.addElement("value");
							valueEle.setText(bean.getItDetectTaskNum() == null ? "" : bean.getItDetectTaskNum());
						}
						if (bean.getTmnlDetectTaskNumStr() != null) {// 采集终端检定任务单数
							org.dom4j.Element apiEle = corpEle.addElement("api");
							apiEle.addAttribute("name", "TmnlDetectTaskNum");
							org.dom4j.Element valueEle = apiEle.addElement("value");
							valueEle.setText(bean.getTmnlDetectTaskNum() == null ? "" : bean.getTmnlDetectTaskNum());
						}
						if (bean.getMeterDistTaskNumStr() != null) {// 电能表配送任务单数
							org.dom4j.Element apiEle = corpEle.addElement("api");
							apiEle.addAttribute("name", "MeterDistTaskNum");
							org.dom4j.Element valueEle = apiEle.addElement("value");
							valueEle.setText(bean.getMeterDistTaskNum() == null ? "" : bean.getMeterDistTaskNum());
						}
						if (bean.getItDistTaskNumStr() != null) {// 互感器配送任务单数
							org.dom4j.Element apiEle = corpEle.addElement("api");
							apiEle.addAttribute("name", "ItDistTaskNum");
							org.dom4j.Element valueEle = apiEle.addElement("value");
							valueEle.setText(bean.getItDistTaskNum() == null ? "" : bean.getItDistTaskNum());
						}
						if (bean.getTmnlDistTaskNumStr() != null) {// 采集终端配送任务单数
							org.dom4j.Element apiEle = corpEle.addElement("api");
							apiEle.addAttribute("name", "TmnlDistTaskNum");
							org.dom4j.Element valueEle = apiEle.addElement("value");
							valueEle.setText(bean.getTmnlDistTaskNum() == null ? "" : bean.getTmnlDistTaskNum());
						}
					}
				} else {
					// 登录人员名单和在线人员名单为单独调用不与其他指标一同调用
					if (bean.getBusinessSystemLoginRoll() != null) {// 日登录人员名单
						org.dom4j.Element rootEle = doc.addElement("LOGINUSER");
						for (int i = 0; i < bean.getBusinessSystemLoginRoll().size(); i++) {
							OutputParamBean chilBean = new OutputParamBean();
							chilBean = bean.getBusinessSystemLoginRoll().get(i);
							org.dom4j.Element userInfoEle = rootEle.addElement("USERINFO");

							org.dom4j.Element ldaEle = userInfoEle.addElement("LDAPID");
							ldaEle.addText(chilBean.getLdapid() == null ? "" : chilBean.getLdapid());

							org.dom4j.Element corrEle = userInfoEle.addElement("CORPORATION");
							corrEle.setText(bean.getBusinessSystemLoginRoll().get(i).getCorporation());

							org.dom4j.Element subComEle = userInfoEle.addElement("SUBCOMPANY");
							subComEle.addText(chilBean.getSubcompany() == null ? "" : chilBean.getSubcompany());

							org.dom4j.Element bureEle = userInfoEle.addElement("BUREAU");
							bureEle.addText(chilBean.getBureau() == null ? "" : chilBean.getBureau());

							org.dom4j.Element deparEle = userInfoEle.addElement("DEPARTMENT");
							deparEle.addText(chilBean.getDepartment() == null ? "" : chilBean.getDepartment());

							org.dom4j.Element nameEle = userInfoEle.addElement("NAME");
							nameEle.setText(chilBean.getName() == null ? "" : chilBean.getName());
							org.dom4j.Element isLdapIdEle = userInfoEle.addElement("ISLDAPID");
							isLdapIdEle.addText(chilBean.getIsldapid() == null ? "" : chilBean.getIsldapid());

						}

					} else if (bean.getBusinessSystemOnlineRoll() != null) {// 在线人员名单
						org.dom4j.Element rootEle = doc.addElement("LOGINUSER");
						System.out.println(bean.getBusinessSystemOnlineRoll().size());
						for (int i = 0; i < bean.getBusinessSystemOnlineRoll().size(); i++) {
							OutputParamBean chilBean = new OutputParamBean();
							chilBean = bean.getBusinessSystemOnlineRoll().get(i);
							org.dom4j.Element userInfoEle = rootEle.addElement("USERINFO");

							org.dom4j.Element ldaEle = userInfoEle.addElement("LDAPID");
							ldaEle.addText(chilBean.getLdapid() == null ? "" : chilBean.getLdapid());

							org.dom4j.Element corrEle = userInfoEle.addElement("CORPORATION");
							corrEle.setText(chilBean.getCorporation() == null ? "" : chilBean.getCorporation());

							org.dom4j.Element subComEle = userInfoEle.addElement("SUBCOMPANY");
							subComEle.addText(chilBean.getSubcompany() == null ? "" : chilBean.getSubcompany());

							org.dom4j.Element bureEle = userInfoEle.addElement("BUREAU");
							bureEle.addText(chilBean.getBureau() == null ? "" : chilBean.getBureau());

							org.dom4j.Element deparEle = userInfoEle.addElement("DEPARTMENT");
							deparEle.addText(chilBean.getDepartment() == null ? "" : chilBean.getDepartment());

							org.dom4j.Element nameEle = userInfoEle.addElement("NAME");
							nameEle.setText(chilBean.getName() == null ? "" : chilBean.getName());

							org.dom4j.Element isLdapIdEle = userInfoEle.addElement("ISLDAPID");
							isLdapIdEle.addText(chilBean.getIsldapid() == null ? "" : chilBean.getIsldapid());
						}
					} else {
						org.dom4j.Element rootEle = doc.addElement("LOGINUSER");
						rootEle.setText("");
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.debug("返回的报文时出错:"+e.getMessage());
		}
		System.out.println("\n返回的报文内容:\n"+doc.asXML());
		log.debug("\n返回的报文内容:\n"+doc.asXML());
		return doc.asXML();
	}
}