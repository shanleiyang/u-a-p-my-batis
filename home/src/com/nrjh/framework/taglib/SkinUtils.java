package com.nrjh.framework.taglib;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

public class SkinUtils {

	private static String key = "Key.Skin";
	private static String javascript_format = "<script src=\"%s\" type=\"text/javascript\"></script>";
	private static String css_format = "<link href=\"%s\" rel=\"stylesheet\" type=\"text/css\"/>";

	protected static Skin createDefaultForRequest(HttpServletRequest request) {
		Skin def = new DefaultSkin(request);
		request.setAttribute(key, def);
		return def;
	}

	public static Skin getSkin(ServletRequest request) {
		Skin skin = (Skin) request.getAttribute(key);
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		if (skin == null)
			skin = (Skin) httpServletRequest.getSession(true).getAttribute(key);
		return skin == null ? createDefaultForRequest(httpServletRequest)
				: skin;
	}

	public static String getImage(ServletRequest request, String imgName) {
		String img = getSkin(request).getImage(imgName);
		return img;
	}

	public static String getJS(ServletRequest request, String jsName) {
		String js = getSkin(request).getJS(jsName);
		return js;
	}

	public static String getCSS(ServletRequest request, String cssName) {
		String css = getSkin(request).getCSS(cssName);
		return css;
	}
	
	public static List<String> getAsset(ServletRequest request, String... assetName) {
		List<String> assetlist = new ArrayList<String>();
		for(String name : assetName) {
			if(StringUtils.isNotBlank(name)) {
				if(name.endsWith(".js")) {
					assetlist.add(String.format(javascript_format, getJS(request, name)));
				} else if (name.endsWith(".css")) {
					assetlist.add(String.format(css_format, getCSS(request, name)));
				}
			}
		}
		return assetlist;
	}

}

class DefaultSkin implements Skin, Serializable {
	private String rootUrl;

	protected DefaultSkin(HttpServletRequest request) {
		StringBuffer sb = new StringBuffer();
		sb.append(request.getScheme());
		sb.append("://");
		sb.append(request.getServerName());
		sb.append(":");
		sb.append(request.getServerPort());
		if (request.getAttribute("web_context_path_web_key") != null)
			sb.append(request.getAttribute("web_context_path_web_key"));
		else
			sb.append(request.getContextPath()).append("/..");
		sb.append("/skins");
		this.rootUrl = sb.toString();
	}

	public String getImage(String imgName) {
		StringBuffer sb = new StringBuffer();
		sb.append(this.rootUrl);
		sb.append("/img/");
		sb.append(imgName);
		return sb.toString();
	}

	public String getJS(String jsName) {
		StringBuffer sb = new StringBuffer();
		sb.append(this.rootUrl);
		sb.append("/js/");
		sb.append(jsName);
		return sb.toString();
	}

	public String getCSS(String cssName) {
		StringBuffer sb = new StringBuffer();
		sb.append(this.rootUrl);
		sb.append("/css/");
		sb.append(cssName);
		return sb.toString();
	}
}
