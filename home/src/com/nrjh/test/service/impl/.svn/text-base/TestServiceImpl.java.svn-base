package com.nrjh.test.service.impl;

import java.util.List;
import java.util.Map;

import org.framework.abstracter.AbstractService;
import org.framework.mybatis.mapper.EntityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nrjh.test.dao.TestMapper;
import com.nrjh.test.service.ITestService;
import com.nrjh.test.service.Test;

@Service
public class TestServiceImpl extends AbstractService<Test> implements ITestService {
	@Autowired
	private TestMapper testMapper;
	@Override
	public EntityMapper getDaoMapper() {
		return testMapper;
	}
	
	public List<Test> query(Map params) {
		return testMapper.query(params);
	}
	
	public List<Test> getAll(){
		return testMapper.getAll();
	}
	
	public List<Test> selectMyTest(){
		return testMapper.selectMyTest();
	}

}
