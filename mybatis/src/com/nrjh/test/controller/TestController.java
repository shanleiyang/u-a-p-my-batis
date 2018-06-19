package com.nrjh.test.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.nrjh.test.service.ITestService;
import com.nrjh.test.service.Test;

@Controller
@RequestMapping(value = "/test")
public class TestController {
	@Autowired
	private ITestService testService;
	
	@RequestMapping("/query")
	public ModelAndView query(){
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("data", testService.getAll());
		return new ModelAndView("test.jsp",model);
	}
	
	@RequestMapping("/selecttest")
	@ResponseBody
	public List<Test> selectMyTest() {
		return testService.selectMyTest();
	}
	
	@RequestMapping("/save")
	@ResponseBody
	public int save(){
		Test test = new Test();
		test.setId("6");
		return testService.save(test);
	}

}
