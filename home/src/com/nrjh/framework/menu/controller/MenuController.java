package com.nrjh.framework.menu.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nrjh.framework.menu.service.IMenuService;
import com.nrjh.framework.menu.service.Menu;

@Controller
@RequestMapping("/manage/menu")
public class MenuController {
	private final static Log log = LogFactory.getLog(MenuController.class);
	
	@Autowired
	private IMenuService menuService;
	
	/**
	 * 获取用户菜单
	 * @param request
	 * @return
	 */
	@RequestMapping("/getMenus")
	@ResponseBody
	public List<Menu> getMenu(HttpServletRequest request) {
		HttpSession session = request.getSession();
		Map userMap=(Map)session.getAttribute("user");
		if(userMap == null || !userMap.containsKey("id")) {
			log.error("##MenuController.getMenu 请先登录系统");
			return new ArrayList<Menu>();
		}
		
		List<Menu> menus = menuService.getIscMenu(String.valueOf(userMap.get("id")));
		
		return menus;
	}
}
