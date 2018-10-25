package com.nrjh.test.service;

import java.util.List;
import java.util.Map;

import org.framework.abstracter.InterfaceService;

public interface ITestService extends InterfaceService<Test> {

	public List<Test> query(Map params);
	
	public List<Test> getAll();
	
	public List<Test> selectMyTest();
}
