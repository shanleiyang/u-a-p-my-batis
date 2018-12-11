package com.nrjh.framework.util;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.nrjh.framework.Constants;
import com.sgcc.isc.core.orm.identity.User;
import com.sgcc.isc.core.orm.organization.BusinessOrganization;
import com.sgcc.isc.service.adapter.factory.AdapterFactory;
import com.sgcc.uap.config.util.PlatformConfigUtil;

/**
 * 获取当前登录用户上下文
 * @author shan
 */
public class BspContextFactory {
	private static Log log = LogFactory.getLog(BspContextFactory.class);
	
	/**
	 * 获得当前登录用户业务组织公司编号
	 */
	public static String getComCode(){
		return getBusiOrganDW().getUnicode();
	}
	/**
	 * 获得当前登录用户业务组织公司名称
	 */
	public static String getComName(){
		return getBusiOrganDW().getName();
	}
	/**
	 * 获取当前登录用户的所在业务组织单位信息 get
	 * @return
	 */
	public static BusinessOrganization getBusiOrganDW() {
		try {
			String userId = getUser().getId();
			//业务组织单元性质编码: DW 单位, BM 部门
			String orgType = "DW";
			BusinessOrganization biOrg = AdapterFactory.getOrganizationService().getUpperOrgBySystemIdAndUserId(PlatformConfigUtil.getString(Constants.ISC_APPID), userId, orgType);
			return biOrg;
		} catch (Exception e) {
			log.error("##BspContextFactory 获取当前登陆用户公司信息失败:"+e.toString());
		}
		return new BusinessOrganization();
	}
	/**
	 * 获取当前登录用户信息 get
	 * @return
	 */
	public static User getUser() {
		HttpServletRequest request = RequestHolder.getRequest();
		User user = (User)request.getSession().getAttribute("userObj");
		if(user==null)
			log.error("##BspContextFactory 从session中未取到当前用户信息");
		return user;
	}
	/**
	 * 根据用户登录名通过isc接口获取用户姓名
	 * @param userId
	 * @return
	 */
	public static String getUserName(String userId){
		String userName = null;//用户汉字名称
		try {
			//根据用户名通过isc接口服务获取用户信息
			List<User> user = AdapterFactory.getIdentityService().getUsersByLoginCode(userId);
			if(user != null){
				userName = user.get(0).getName();
			}
		} catch (Exception e) {
			log.error("##BspContextFactory 根据登录名获取用户信息失败"+e.getMessage());
		}
		return userName;
	}
}
