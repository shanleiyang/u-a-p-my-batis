package com.nrjh.framework.menu.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.nrjh.framework.Constants;
import com.nrjh.framework.menu.service.IMenuService;
import com.nrjh.framework.menu.service.Menu;
import com.sgcc.isc.core.orm.complex.FunctionNode;
import com.sgcc.isc.core.orm.resource.Function;
import com.sgcc.isc.service.adapter.factory.AdapterFactory;
import com.sgcc.uap.config.util.PlatformConfigUtil;

@Service("menuService")
public class MenuService implements IMenuService {
	
	private static Log log = LogFactory.getLog(MenuService.class);

	/**
	 * 根据用户id获取对应isc菜单
	 */
	public List<Menu> getIscMenu(String userId){
		//获取ISC系统中的菜单结构
        List<Menu> list = new ArrayList<Menu>();
		try {
			//获取在指定业务应用下的第一层的菜单结构
	        List<Function> firstFuncsList = AdapterFactory.getResourceService().getFirstLayerFuncs(PlatformConfigUtil.getString(Constants.ISC_APPID),Constants.ISC_MENU_TYPE,new HashMap<String, String>(),new String[0]);
	        //根据第一层菜单ID递归地获取当前用户在指定功能下的功能子树（包括当前节点）（每个一级菜单只检索500条）
	        for(Function func:firstFuncsList){
	        	FunctionNode fTree =  AdapterFactory.getResourceService().getFuncTreeByFuncId(userId, func.getId(), Constants.ISC_MENU_TYPE);
	        	if(fTree!=null) {
	        		list.add(getChildMenus(fTree));
	        	}
	        }
		} catch (Exception e) {
			log.error("##MenuService.getIscMenu 获取isc菜单失败:{}",e.fillInStackTrace());
			log.error(e);
		}
        
        return list;
	}
	
	/**
	 * 获取菜单及子菜单
	 * @param fNode
	 * @return
	 */
	private Menu getChildMenus(FunctionNode fNode) {
		Menu menu = new Menu();
		Function func = fNode.getCurrentNode();
		menu.setId(func.getId());
		menu.setName(func.getName());
		menu.setBusiCode(func.getBusiCode());
		menu.setUrl(func.getUrl());
		menu.setFuncType(func.getFuncType());
		menu.setFuncCategory(func.getFuncCategory());
		
		if(fNode.getNextNode() != null && !fNode.getNextNode().isEmpty()) {
			List<Menu> childMenus = new ArrayList<Menu>();
			List<FunctionNode> nextNodes = fNode.getNextNode();
			for(FunctionNode node : nextNodes) {
				childMenus.add(getChildMenus(node));
			}
			
			menu.setChildMenus(childMenus);
		}
		
		return menu;
	}
	
	/**
	 * 根据当前登录用户的id获取isc系统中对应的资源信息
	 * @return
	 */
	public List<FunctionNode> getResourceList(String userId){
		List<FunctionNode> ownlist = new ArrayList<FunctionNode>();
		try {
			//获取在指定业务应用下的第一层的菜单结构
			List<Function> firstList = AdapterFactory.getResourceService().getFirstLayerFuncs(PlatformConfigUtil.getString(Constants.ISC_APPID),"",new HashMap<String, String>(),new String[0]);
			//根据第一层菜单ID递归地获取当前用户在指定功能下的功能子树（包括当前节点）（每个一级菜单只检索500条）
			for(Function func:firstList){
				FunctionNode fTree =  AdapterFactory.getResourceService().getFuncTreeByFuncId(userId, func.getId(), "");
				if(fTree!=null)
					getChildFunctionNode(fTree, ownlist);
			}
		} catch (Exception e) {
			log.error("##MenuService.getResourceList 获取isc资源失败:{}",e);
			log.error(e.getMessage(), e);
		}
		if(log.isDebugEnabled()){
			log.debug("##MenuService.getResourceList 获取isc资源数量:"+ownlist.size());
		}
		return ownlist;
	}
	
	/**
	 * 获取所有资源及子资源
	 * @param fNode
	 * @return
	 */
	private void getChildFunctionNode(FunctionNode fNode,List<FunctionNode> allList) {
		allList.add(fNode);
		if(fNode.getNextNode() != null && !fNode.getNextNode().isEmpty()) {
			List<FunctionNode> nextNodes = fNode.getNextNode();
			for(FunctionNode node : nextNodes) {
				getChildFunctionNode(node, allList);
			}
		}
		
	}
}
