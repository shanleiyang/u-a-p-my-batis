package com.nrjh.test.service;

import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "TEST")
public class Test {

	@Id
	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
}
