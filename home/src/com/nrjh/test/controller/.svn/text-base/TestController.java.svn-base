package com.nrjh.test.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.framework.abstracter.AbstractController;
import org.framework.abstracter.InterfaceService;
import org.framework.mybatis.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nrjh.test.service.ITestService;
import com.nrjh.test.service.Test;

@Controller
@RequestMapping(value = "/test")
public class TestController extends AbstractController<Test>{
	@Autowired
	private ITestService testService;
	
	@Override
	public String getEditJsp() {
		return "test/edit.jsp";
	}

	@Override
	public String getQueryJsp() {
		return "test/testquery.jsp";
	}

	@Override
	public InterfaceService<Test> getService() {
		return testService;
	}
	
	/**
     * 列表跳转页面
     * 
     * @return 列表页面
     */
    @RequestMapping("/1")
    public String queryTest() {
        return getQueryJsp();
    }
    
    /**
     * 分页查询
     *
     * @param parameters key分别为：
     *              <code>orderfield<code>[排序列]
     *              <code>orderdir<code>[排序方向desc或asc] 
     *              <code>start<code>[起始行] 
     *              <code>limit<code>[每页显示条数] 
     *				<code>id<code>[ID]
     *				<code>name<code>[姓名]
     *              
     * @return Map key分别为：
     *              <code>total<code>[总记录条数] 
     *              <code>data<code>[数据列表]
     * 
     */
    @RequestMapping("/query1")
    @ResponseBody
    public Map query1(@RequestBody Map parameters) {
    	Map<String, Object> testMap = new HashMap<String, Object>();
        List<Test> testList = testService.query(parameters);
        
        testMap.put("data", testList);
        // 获取总记录条数
        int total = PageUtil.getTotalCount();
        testMap.put("total", total != -1 ? total : testList.size());
        return testMap;
    }
	
	@RequestMapping("/selecttest")
	@ResponseBody
	public List<Test> selectMyTest() {
		return testService.selectMyTest();
	}
	
	@RequestMapping("/save1")
	@ResponseBody
	public String save(Test test){
		testService.save(test);
		
		return "redirect:/rest/test";
	}

}
