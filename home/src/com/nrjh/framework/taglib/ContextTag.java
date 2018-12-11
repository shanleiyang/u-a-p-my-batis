package com.nrjh.framework.taglib;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

public class ContextTag extends TagSupport {

	@Override
	public int doStartTag() throws JspException {
		try {
			this.pageContext.getOut().print(SkinUtils.getSkin((HttpServletRequest) this.pageContext.getRequest()).getRootPath());
		} catch (Exception e) {
			String message = "处理Context-Tag时出错";
			try {
				this.pageContext.getOut().print(message + e.getMessage());
			} catch (IOException localIOException) {
			}
		}
		return SKIP_BODY;
	}
}
