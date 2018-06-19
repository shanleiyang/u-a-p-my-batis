package com.nrjh.test.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nrjh.test.dao.TestMapper;
import com.nrjh.test.service.ITestService;
import com.nrjh.test.service.Test;

@Service
public class TestServiceImpl implements ITestService {
	@Autowired
	private TestMapper testMapper;
	
	public List<Test> getAll(){
		return testMapper.getAll();
	}
	
	public int save(Test test){
		int rslt = testMapper.insert(test);
		//int a = 1/0;
		return rslt;
	}
	
	public List<Test> selectMyTest(){
		return testMapper.selectMyTest();
	}
}
