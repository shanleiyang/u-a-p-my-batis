package org.framework.util;

import org.framework.util.uuid.UUIDGenerator;

public class UUIDUtils {
	private static UUIDGenerator uuid = new UUIDGenerator();

	public static String nextUUID(int len) {
		return (String) uuid.getNextSeqId(len);
	}

	public static String nextUUID() {
		return (String) uuid.getNextSeqId(32);
	}

	public static String[] nextUUID(int len, int size) {
		String[] uds = new String[size];
		for (int i = 0; i < size; i++) {
			uds[i] = (String) uuid.getNextSeqId(size);
		}
		return uds;
	}

}
