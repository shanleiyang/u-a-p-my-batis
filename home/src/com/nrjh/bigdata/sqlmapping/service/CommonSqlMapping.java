package com.nrjh.bigdata.sqlmapping.service;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

import org.framework.abstracter.AbstractEntity;
import org.framework.abstracter.InsertAble;
import org.framework.abstracter.UpdateAble;
import org.framework.mybatis.annotation.UUID;
/**
 * 控件sql映射实体bean，以及与数据库中字段的对应关系
 */
@Table(name = "COMMON_SQL_MAPPING")
public class CommonSqlMapping extends AbstractEntity<String> implements Serializable, InsertAble, UpdateAble {

	/**
	 * version
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "MAPPING_ID")
	private String mappingId;
	
	@Column(name = "TAG_NO")
	private String tagNo;
	
	@Column(name = "TAG_NAME")
	private String tagName;
	
	@Column(name = "QUERY_SQL")
	private String querySql;
	
	@Column(name = "SQL_PARAMS_DESC")
	private String sqlParamsDesc;
	
	@Column(name = "CREATE_DATE")
	private Date createDate;
	
	@Column(name = "CREATOR_NO")
	private String creatorNo;
	
	@Column(name = "UPDATE_DATE")
	private Date updateDate;
	
	@Column(name = "UPDATER_NO")
	private String updaterNo;
	
	@Column(name = "REMARK")
	private String remark;

	public String getId() {
		return mappingId;
	}

	@UUID(30)
	public void setId(String mappingId) {
		this.mappingId = mappingId;
	}

	public String getTagNo() {
		return tagNo;
	}

	public void setTagNo(String tagNo) {
		this.tagNo = tagNo;
	}

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}
	/**
	 * 因tagName与vue.js中关键字冲突，页面表单中改用mappingName
	 * 获取tagName
	 * @return
	 */
	public String getMappingName() {
		return tagName;
	}
	/**
	 * 因tagName与vue.js中关键字冲突，页面表单中改用mappingName
	 * 设置tagName
	 */
	public void setMappingName(String mappingName) {
		this.tagName = mappingName;
	}

	public String getQuerySql() {
		return querySql;
	}

	public void setQuerySql(String querySql) {
		this.querySql = querySql;
	}

	public String getSqlParamsDesc() {
		return sqlParamsDesc;
	}

	public void setSqlParamsDesc(String sqlParamsDesc) {
		this.sqlParamsDesc = sqlParamsDesc;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getCreatorNo() {
		return creatorNo;
	}

	public void setCreatorNo(String creatorNo) {
		this.creatorNo = creatorNo;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getUpdaterNo() {
		return updaterNo;
	}

	public void setUpdaterNo(String updaterNo) {
		this.updaterNo = updaterNo;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	public void setInsertTime(Date date) {
		this.createDate = date;
	}

	@Override
	public void setUpdateTime(Date date) {
		this.updateDate = date;
	}
}
