package com.nrjh.sgmds.ims.webService.impl;
import org.springframework.beans.factory.annotation.Autowired;

import com.nrjh.sgmds.ims.business.MDSBusinessController;
import com.nrjh.sgmds.ims.webService.MDSWebService;

public class MDSWebServiceImpl implements MDSWebService{
	@Autowired
	private MDSBusinessController mdsBusiness;
	
	public String getKPIValue(String xml) {
		return mdsBusiness.getKPIValue(xml);
	}
	
	public String getStatus(String xml) {
		return mdsBusiness.getStatus(xml);
	}
	
	public void getTest(String xml) {
		System.out.println(xml);;
	}

}
