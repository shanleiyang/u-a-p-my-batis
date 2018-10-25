package com.nrjh.test.service;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

import org.framework.abstracter.AbstractEntity;
import org.framework.mybatis.annotation.UUID;

@Table(name = "TEST")
public class Test extends AbstractEntity<String> {

	@Id
	private String id;
	
	@Column(name = "NAME")
	private String name;

	public String getId() {
		return id;
	}

	@UUID(30)
	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
