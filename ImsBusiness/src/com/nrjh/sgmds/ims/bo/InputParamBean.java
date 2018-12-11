package com.nrjh.sgmds.ims.bo;

import java.util.List;

public class InputParamBean {

	/***
	 * 
	 *  @author Íõ¼Ê½ð
	 *
	 */
	private String[] corporation;
	private String time;
	private List<String> indexName;
	public String[] getCorporation() {
		return corporation;
	}
	public void setCorporation(String[] corporation) {
		this.corporation = corporation;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public List<String> getIndexName() {
		return indexName;
	}
	public void setIndexName(List<String> indexName) {
		this.indexName = indexName;
	}
	
}
