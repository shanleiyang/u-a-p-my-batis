package com.nrjh.test.service;

import java.util.List;

public interface ITestService {

	public List<Test> getAll();
	
	public int save(Test test);
	
	public List<Test> selectMyTest();
}
