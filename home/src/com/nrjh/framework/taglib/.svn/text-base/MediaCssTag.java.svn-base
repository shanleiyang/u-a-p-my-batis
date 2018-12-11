package com.nrjh.framework.taglib;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class MediaCssTag extends TagSupport {

	private static final long serialVersionUID = 1L;
	
	private static Log log = LogFactory.getLog(MediaCssTag.class);
	// 1920分辨率
	private static String css_1920_format = "<link href=\"%s\" rel=\"stylesheet\" type=\"text/css\" media=\"screen and (min-width:1890px)\"/>";
	// 1366分辨率
	private static String css_1366_format = "<link href=\"%s\" rel=\"stylesheet\" type=\"text/css\" media=\"screen and (max-width:1890px)\"/>";
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
				HttpServletRequest request = (HttpServletRequest) this.pageContext.getRequest();
				String[] assetlist = this.path.split(",");
				for (String name : assetlist) {
					if (name.endsWith(".css")) {
						name = name.substring(0, name.lastIndexOf(".css"));
						this.pageContext.getOut().println(
										String.format(
												css_1920_format,
												SkinUtils.getCSS(request, String.format("%s.css", name))));
						this.pageContext.getOut().println(
										String.format(
												css_1366_format,
												SkinUtils.getCSS(request, String.format("%s_1366.css", name))));
					}
				}
			}
		} catch (Exception e) {
			String message = "处理MediaCss-tag时出错, 参数: path='" + this.path + "'";
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
