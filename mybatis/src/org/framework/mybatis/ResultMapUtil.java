package org.framework.mybatis;

public class ResultMapUtil {

	public static final ThreadLocal threadInfo = new ThreadLocal();
	private static String upperOrLower;

	public static String getUpperOrLower() {
		return upperOrLower;
	}

	public static void setUpperOrLower(String upperOrLower) {
		upperOrLower = upperOrLower;
	}
	
}
