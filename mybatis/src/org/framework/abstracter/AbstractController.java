package org.framework.abstracter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.framework.mybatis.PageUtil;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

public abstract class AbstractController<E extends AbstractEntity> {
	private final static Log log = LogFactory.getLog(AbstractController.class);
	/**
	 * 获取service
	 * @return
	 */
	public abstract InterfaceService<E> getService();
	/**
	 * 获取查询JSP
	 * @return
	 */
	public abstract String getQueryJsp();
	/**
	 * 获取编辑JSP
	 * @return
	 */
	public abstract String getEditJsp();
	/**
	 * 默认跳转url
	 */
	public String defaultUrl;
	
	protected AbstractController() {
		RequestMapping requestMapping = AnnotationUtils.findAnnotation(getClass(), RequestMapping.class);
		if(requestMapping != null) {
			defaultUrl = requestMapping.value()[0];
			defaultUrl = defaultUrl.startsWith("/") ? defaultUrl : "/" + defaultUrl;
		}
	}
	
	/**
	 * 默认跳转页
	 * @return
	 */
	@RequestMapping
	public String queryPage() {
		return getQueryJsp();
	}
	
	/**
     * 分页查询
     *
     * @param parameters key分别为：
     *              <code>orderfield<code>[排序列]
     *              <code>orderdir<code>[排序方向desc或asc] 
     *              <code>start<code>[起始行] 
     *              <code>limit<code>[每页显示条数] 
     *              
     * @return Map key分别为：
     *              <code>total<code>[总记录条数] 
     *              <code>data<code>[数据列表], List 内容为 E
     * 
     */
	@RequestMapping("/query")
	@ResponseBody
	public Map query(@RequestBody Map<String, String> parameters) {
        Map<String, Object> resltMap = new HashMap<String, Object>();
        List<E> list = new ArrayList<E>();
        list = getService().queryMysql(parameters);
        resltMap.put("data", list);
        // 获取总记录条数
        int total = PageUtil.getTotalCount();
        resltMap.put("total", total != -1 ? total : list.size());
        return resltMap;
    }
	
	/**
	 * 跳转编辑页
	 * @param id
	 * @return
	 */
	@RequestMapping("/edit")
    public ModelAndView editPage(E bean) {
        Map<String, Object> model = new HashMap<String, Object>();
        E resltBean = null;
        if (bean.getId() != null && !"".equals(bean.getId())) {
        	resltBean = getService().get(bean.getId());
        }
        
        return new ModelAndView(getEditJsp(), model);
    }
	
	/**
	 * 保存编辑内容
	 * @param bean
	 * @return
	 */
	@RequestMapping(value = "/save")
    public String save(E bean, HttpServletRequest request) {
        getService().save(bean);
        // 获取当前模块名称
        //String bundleName = ((ModuleHttpServletRequestAdaptor)request).getBundleContextPath();
        return String.format("redirect:/rest%s", defaultUrl);
    }
	
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete")
    public String delete(E bean, HttpServletRequest request) {
        if (bean.getId() != null && !"".equals(bean.getId())) {
            getService().delete(bean.getId());
        }
        // 获取当前模块名称
        //String bundleName = ((ModuleHttpServletRequestAdaptor)request).getBundleContextPath();
        return String.format("redirect:/rest%s", defaultUrl);
    }
}
