package com.zhilingsd.base.common.utils.core;

import java.util.UUID;

/**
 * UUID 工具
 * @author lgl
 *
 */
public class UUIDGenerator {
	public UUIDGenerator() {
	}

	/**
	 * 获得一个UUID
	 * 
	 * @return String UUID
	 */
	public static String getUUID() {
		String uuid = UUID.randomUUID().toString();
		// 去掉“-”符号
		return uuid.toString().replace("-", "");
	}

	public static String getUUIDToUpper() {
        String uuid = UUID.randomUUID().toString().trim().replaceAll("-", "").toUpperCase();
        return uuid;
    }
	/**
	 * 获得指定数目的UUID
	 * 
	 * @param number
	 *            int 需要获得的UUID数量
	 * @return String[] UUID数组
	 */
	public static String[] getUUIDSS(int number) {
		if (number < 1) {
			return null;
		}
		String[] ss = new String[number];
		for (int i = 0; i < number; i++) {
			ss[i] = getUUID();
		}
		return ss;
	}
}
