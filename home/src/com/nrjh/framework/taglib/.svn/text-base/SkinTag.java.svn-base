package com.nrjh.framework.taglib;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class SkinTag extends TagSupport {

	private static Log log = LogFactory.getLog(SkinTag.class);
	private String img;
	private String js;
	private String css;

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getJs() {
		return js;
	}

	public void setJs(String js) {
		this.js = js;
	}

	public String getCss() {
		return css;
	}

	public void setCss(String css) {
		this.css = css;
	}

	@Override
	public int doStartTag() throws JspException {
		try {
			if (this.img != null) {
				this.pageContext.getOut().print(
						SkinUtils.getImage(
								(HttpServletRequest) this.pageContext
										.getRequest(), this.img));
			}

			if (this.js != null) {
				this.pageContext.getOut().print(
						SkinUtils.getJS((HttpServletRequest) this.pageContext
								.getRequest(), this.js));
			}

			if (this.css != null) {
				this.pageContext.getOut().print(
						SkinUtils.getCSS((HttpServletRequest) this.pageContext
								.getRequest(), this.css));
			}
		} catch (Exception e) {
			String message = "处理Skin-tag时出错, 参数: img='" + this.img + "', js='"
					+ this.js + "'";
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
		this.img = null;
		this.css = null;
		this.js = null;
	}

}
