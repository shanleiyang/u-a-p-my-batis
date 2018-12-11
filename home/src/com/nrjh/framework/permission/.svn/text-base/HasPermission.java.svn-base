package com.nrjh.framework.permission;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.nrjh.framework.Constants;
import com.nrjh.framework.menu.service.IMenuService;
import com.nrjh.framework.util.RequestHolder;
import com.sgcc.isc.core.orm.complex.FunctionNode;
import com.sgcc.isc.core.orm.resource.Function;
import com.sgcc.uap.utils.ComponentFactory;

public class HasPermission {
	private static final Log log = LogFactory.getLog(HasPermission.class);

	/**
	 * 校验当前用户是否拥有url权限
	 * @param url
	 * @return
	 */
	public static boolean hasPermission(String url) {
		if(getPermissionResource(url) != null) {
			return true;
		}
		return false;
	}
	
	/**
	 * 校验当前用户是否拥有url权限
	 * @param url
	 * @param request
	 * @return
	 */
	public static boolean hasPermission(String url, HttpServletRequest request) {
		if(getPermissionResource(url, request) != null) {
			return true;
		}
		return false;
	}
	
	/**
	 * 根据url获取对应的资源信息，如果未获取到则返回null
	 * @param url
	 * @return
	 */
	public static FunctionNode getPermissionResource(String url){
		HttpServletRequest request = RequestHolder.getRequest();
		return getPermissionResource(url, request);
	}
	
	/**
	 * 根据url获取对应的资源信息，如果未获取到则返回null
	 * @param url
	 * @param request
	 * @return
	 */
	public static FunctionNode getPermissionResource(String url, HttpServletRequest request){
		if(url==null||"".equals(url))
			return null;
		else if("0".equals(url))
			return new FunctionNode();
		HttpSession session = request.getSession();
		Map userMap=(Map)session.getAttribute("user");
		if(userMap==null || userMap.isEmpty()){
			log.error("##HasPermission.getPermissionResource 未获取到用户信息");
			return null;
		}
		List<FunctionNode> resList = null;
		if(session.getAttribute(Constants.USER_RESOURCES) != null) {
			resList = (List<FunctionNode>)session.getAttribute(Constants.USER_RESOURCES);
		} else {
			IMenuService menuService = (IMenuService)ComponentFactory.getBean("menuService");
			resList = menuService.getResourceList(String.valueOf(userMap.get("id")));
			//放到session中
			session.setAttribute(Constants.USER_RESOURCES, resList);
		}
		if(resList != null) {
			for(FunctionNode rb:resList){
				Function func = rb.getCurrentNode();
				if(func.getUrl()!=null && !"".equals(func.getUrl())){
					String rburl = func.getUrl();
					if(rburl.contains(url) || url.contains(rburl))
						return rb;
				}
			}
		}
		return null;
	}
}
