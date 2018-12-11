package com.nrjh.framework.taglib;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class OrgSelectTag  extends TagSupport{
	private static Log log = LogFactory.getLog(OrgSelectTag.class);
	private String id;
	private String name;
	private String styleClass;
	private String style;
	private String onclick;
	private String onchange;
	private String childchange;
	private String isLink;
	private String childrenID;
	private String parentID;
	private String area;
	private static String cityTagNo = "getcityInfo";
	private static String countyTagNo = "getcountyInfo";
	@Override
	public int doStartTag() throws JspException {
		try {
			List orgList = new ArrayList();
			if(area==null||"".equals(area)||"CITY".equals(area)){
				orgList = OrgListUtil.getOrgList(cityTagNo);
			}else{
				orgList = OrgListUtil.getOrgList(countyTagNo);
			}
			StringBuffer ulHtml = new StringBuffer();
			StringBuffer liHtml = new StringBuffer();
			String qxJSonData="";
			if(orgList!=null&&orgList.size()>0){
				for(int i=0;i<orgList.size();i++){
					Map<String, String> dataMap = (Map<String, String>) orgList.get(i);
					if(i==0){
						ulHtml.append("<ul  class=\""+this.styleClass+"\">\n");
						ulHtml.append("<li class=\"select_box\" id=\"select_box_"+this.id+"\" >\n");
						ulHtml.append("<span id=\"orgspan_"+this.id+"\"></span>\n");
						if("true".equals(isLink)&&"COUNTY".equals(area)){
							ulHtml.append("<input type='hidden' id=\""+this.id+"\" value='' onchange='setName_"+this.id+"()' />\n");
						}else{
							ulHtml.append("<input type='hidden' id=\""+this.id+"\" value='"+dataMap.get("CODE")+"' onchange='setName_"+this.id+"()' />\n");
						}
						ulHtml.append("<ul class=\"son_ul\" id=\"sonul_"+this.id+"\" style=\"display:none;\">\n");
					}
					if("true".equals(isLink)&&"COUNTY".equals(area)){
						qxJSonData +=  "{name:'"+dataMap.get("NAME")+"',code:'"+dataMap.get("CODE")+"',parentcode:'"+dataMap.get("PARENTCODE")+"'},";
					}else{
						liHtml.append("<li data='"+dataMap.get("CODE")+"' ");
						liHtml.append("  onclick=\"selectOrg_"+this.id+"('"+dataMap.get("CODE")+"','"+dataMap.get("NAME")+"')\">"+dataMap.get("NAME")+"</li>\n");
					}
				}
				liHtml.append("</ul>\n");
				liHtml.append("</li>\n");
				liHtml.append("</ul>\n");
				liHtml.append("<script type=\"text/javascript\"> \n");
				if("true".equals(isLink)&&"COUNTY".equals(area)){//如果是联动，查出区县的上级单位编码
					liHtml.append(" var qxJSonData =["+qxJSonData.substring(0, qxJSonData.length()-1)+"];\n");
				}
				liHtml.append("$(function(){\n");
				liHtml.append("$(\"#select_box_"+this.id+"\").click(function(e){\n");
				liHtml.append("$('#sonul_"+this.id+"').toggle();\n");
				if(!"".equals(childrenID)&&childrenID!=null){
					liHtml.append("$('#sonul_"+this.childrenID+"').hide();\n");
				}else if(!"".equals(parentID)&&parentID!=null){
					liHtml.append("$('#sonul_"+this.parentID+"').hide();\n");
				}
				liHtml.append(" e.stopPropagation();\n });\n });\n");
				
				liHtml.append("  $(document).click(function(){\n");
				liHtml.append(" $('#sonul_"+this.id+"').hide();  });\n");
				
				liHtml.append("function selectOrg_"+this.id+"(OrgNo,orgName) {\n");
				if(childrenID!=null){//如果是联动，动态设置对应的下级区县
					liHtml.append(" setQXSelect(OrgNo);\n");
				}
				liHtml.append("$('#orgspan_"+this.id+"').text(orgName);\n");
				liHtml.append("$('#"+this.id+"').val(OrgNo);\n");
				liHtml.append("}\n");
				if("true".equals(isLink)&&"COUNTY".equals(area)){
					liHtml.append("function setQXSelect(OrgNo,selectOrg){\n");
					liHtml.append("$('#sonul_"+this.id+"').empty(); \n");
					liHtml.append("var isFirst = true; \n");
					liHtml.append("for(var i=0;i<qxJSonData.length;i++){\n");
					liHtml.append("  if(qxJSonData[i].parentcode==OrgNo){\n");
					liHtml.append("if(selectOrg!=''&&typeof(selectOrg)!='undefined'&&qxJSonData[i].code==selectOrg){\n");
					liHtml.append("    selectOrg_"+this.id+"(qxJSonData[i].code,qxJSonData[i].name);\n");
					liHtml.append("}\n");
					liHtml.append("else if(isFirst){ \n");
					liHtml.append(" isFirst=false;\n");
					liHtml.append(" selectOrg_"+this.id+"(qxJSonData[i].code,qxJSonData[i].name);\n");
					liHtml.append("}\n");
					liHtml.append("$('#sonul_"+this.id+"').append(\"");
					liHtml.append("<li data='\"+qxJSonData[i].code+\"' ");
					liHtml.append("  onclick=\\\"selectOrg_"+this.id+"('\"+qxJSonData[i].code+\"','\"+qxJSonData[i].name+\"')\\\">\"+qxJSonData[i].name+\"</li>\")\n");
					liHtml.append("}\n");
					liHtml.append("} \n");
					liHtml.append("}\n");
				}
				liHtml.append("function setName_"+this.id+"() {\n");
				if("CITY".equals(area)){//如果是联动，动态联动设置对应的下级区县
					liHtml.append(" var selectOrgName = $(\"li[data='\"+$('#"+this.id+"').val()+\"']\" ).text();\n");
					liHtml.append(" $('#orgspan_"+this.id+"').text(selectOrgName);\n");
					liHtml.append(" var OrgNo = $('#"+this.id+"').val();\n");
				}else if("COUNTY".equals(area)){
					liHtml.append(" var selectQXorg = $('#"+this.id+"').val();\n");	
					liHtml.append("if(selectQXorg){\n");
					liHtml.append(" setQXSelect($('#"+this.parentID+"').val(),selectQXorg);\n");
					liHtml.append("}else{\n");
					liHtml.append(" setQXSelect($('#"+this.parentID+"').val(),'');\n");
					liHtml.append("}\n");
				}
				liHtml.append("}\n");
				
				liHtml.append("</script>\n");
			}else{
				ulHtml.append("<ul  class=\""+this.styleClass+"\">\n");
				ulHtml.append("<li class=\"select_box\">\n");
				ulHtml.append("<span id=\"orgspan_"+this.id+"\"></span>\n");
				ulHtml.append("<input type='hidden' id=\""+this.id+"\" value='' />\n");
				ulHtml.append("</li>\n");
				ulHtml.append("</ul>\n");
			}
			ulHtml.append(liHtml);
			this.pageContext.getOut().print(ulHtml.toString());
		} catch (Exception e) {
			String message = "处理OrgSelectTag时出错";
			try {
				this.pageContext.getOut().print(message);
			} catch (IOException localIOException) {
			}
		}
		return SKIP_BODY;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getStyleClass() {
		return styleClass;
	}
	public void setStyleClass(String styleClass) {
		this.styleClass = styleClass;
	}
	public String getStyle() {
		return style;
	}
	public void setStyle(String style) {
		this.style = style;
	}
	public String getOnclick() {
		return onclick;
	}
	public void setOnclick(String onclick) {
		this.onclick = onclick;
	}
	public String getOnchange() {
		return onchange;
	}
	public void setOnchange(String onchange) {
		this.onchange = onchange;
	}
	public String getChildchange() {
		return childchange;
	}
	public void setChildchange(String childchange) {
		this.childchange = childchange;
	}
	public static Log getLog() {
		return log;
	}
	public static void setLog(Log log) {
		OrgSelectTag.log = log;
	}
	public String getIsLink() {
		return isLink;
	}
	public void setIsLink(String isLink) {
		this.isLink = isLink;
	}
	public String getChildrenID() {
		return childrenID;
	}
	public void setChildrenID(String childrenID) {
		this.childrenID = childrenID;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getParentID() {
		return parentID;
	}
	public void setParentID(String parentID) {
		this.parentID = parentID;
	}
}
