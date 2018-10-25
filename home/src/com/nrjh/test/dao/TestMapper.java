package com.nrjh.test.dao;

import java.util.List;

import org.framework.mybatis.mapper.EntityMapper;

import com.nrjh.test.service.Test;

public interface TestMapper extends EntityMapper<Test> {

	public List<Test> selectMyTest();
}
