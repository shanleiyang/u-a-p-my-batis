package com.nrjh.framework.permission;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.sgcc.uap.kernel.spring.support.ModuleContextAware;
import com.sgcc.uap.kernel.spring.support.ModuleXMLWebApplicationContext;

public class FilterAuthenticationEntryPoint implements Filter,
		ModuleContextAware {

	protected static Log log = LogFactory
			.getLog(FilterAuthenticationEntryPoint.class);
	private WebApplicationContext wac;
	private String menuurl = "";
	private String warnurl = "/home/public/warn.jsp";
	// 是否是生产模式：true 生产，false 开发
	private boolean productModel = true;

	public void init(FilterConfig filterConfig) throws ServletException {
		if (filterConfig.getInitParameter("ignoreURI") != null) {
			this.menuurl = filterConfig.getInitParameter("ignoreURI");
		}
		if (filterConfig.getInitParameter("warnURL") != null) {
			this.warnurl = filterConfig.getInitParameter("warnURL");
		}
		if (filterConfig.getInitParameter("productModel") != null) {
			String model = filterConfig.getInitParameter("productModel");
			if (model.toUpperCase().equals("TRUE"))
				this.productModel = true;
			else
				this.productModel = false;
		}
	}

	public void doFilter(ServletRequest reqs, ServletResponse reps,
			FilterChain chain) throws ServletException, IOException {
		HttpServletRequest request = (HttpServletRequest) reqs;
		/**
		 *  初始化线程中的request参数
		 *  由于org.springframework.web.context.request.RequestContextListener配置不生效，
		 *  故在此处配置
		 */
		ServletRequestAttributes attributes = new ServletRequestAttributes(request);
		RequestContextHolder.setRequestAttributes(attributes);
		
		String pathInfo = request.getPathInfo();
		String url = pathInfo;
		// 开发模式
		if (!this.productModel) {
			chain.doFilter(reqs, reps);
			return;
		}
		if (isPermittedBeforeVote(url)) {
			chain.doFilter(reqs, reps);
			return;
		}
		String redUrl = (new StringBuilder(String.valueOf(request
				.getContextPath()))).append(this.warnurl).toString();
		if (HasPermission.hasPermission(url, request)) {
			chain.doFilter(reqs, reps);
			return;
		}
		System.out.println(String.format("拒绝访问的资源url：%s", url));
		log.warn(String.format("拒绝访问的资源url：%s", url));
		((HttpServletResponse) reps).sendRedirect(redUrl);
		return;
	}

	public void destroy() {
	}

	public boolean isPermittedBeforeVote(String url) {
		if (url == null || url.trim().equals(""))
			return true;
		String menuUrls[] = this.menuurl.split(";");
		for (int i = 0; i < menuUrls.length; i++)
			if (url.contains(menuUrls[i]))
				return true;
		return false;
	}

	private WebApplicationContext getWebApplicationContext(ServletContext sc) {
		WebApplicationContext wac = WebApplicationContextUtils
				.getWebApplicationContext(sc);
		if (wac == null)
			throw new IllegalStateException(
					"WebApplicationContext\u5BF9\u8C61\u4E3A\u7A7A\uFF0C\u8BF7\u67E5\u770BContextLoaderListener\u662F\u5426\u6CE8\u518C\u3002");
		else
			return wac;
	}

	protected ApplicationContext getContext(FilterConfig filterConfig) {
		if (wac != null)
			return wac;
		else
			return getWebApplicationContext(filterConfig.getServletContext());
	}

	public void setModuleContext(ModuleXMLWebApplicationContext moduleContext) {
		wac = moduleContext;
	}

}
