package org.framework.mybatis;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class PageUtil {
	public static final String DEFAULT_FETCH_SIZE_KEY = "mybatis.fetchSize";
	private static Integer defaultFetchSize;
	private static Log log = LogFactory.getLog(PageUtil.class);

	public static final ThreadLocal<Object> total = new ThreadLocal();

	public static Integer getDefaultFetchSize() {
		if (defaultFetchSize == null) {
			defaultFetchSize = new Integer(100000);
		}

		return defaultFetchSize;
	}

	public static int getTotalCount() {
		return getTotalCount("total");
	}

	public static int getTotalCount(Object key) {
    int count = -1;
    try {
      if (total.get() == null) {
        log.debug("未开启获取查询记录总条数的配置。");
        total.remove();
        return count;
      }
      count = ((Integer)total.get()).intValue();
    }
    catch (NumberFormatException e) {
      log.error("", e);
    }
    return count;
  }

	public static void setTotalCount(Object count) {
		total.set(count);
	}
}
