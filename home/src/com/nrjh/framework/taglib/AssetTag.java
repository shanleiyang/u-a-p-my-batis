package com.nrjh.framework.taglib;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class AssetTag extends TagSupport {

	private static Log log = LogFactory.getLog(AssetTag.class);
	private String path;

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	@Override
	public int doStartTag() throws JspException {
		try {
			if (this.path != null) {
				List<String> assetlist = SkinUtils.getAsset(
						(HttpServletRequest) this.pageContext.getRequest(),
						this.path.split(","));
				for (String assetstr : assetlist) {
					this.pageContext.getOut().println(assetstr);
				}
			}
		} catch (Exception e) {
			String message = "处理Asset-tag时出错, 参数: path='" + this.path + "'";
			log.error(message, e);
			try {
				this.pageContext.getOut().print(message + e.getMessage());
			} catch (IOException localIOException) {
			}
		}
		return SKIP_BODY;
	}

	@Override
	public void release() {
		this.path = null;
	}

}
