
package com.zhilingsd.collection.common.util;

import com.google.gson.Gson;

/**
 * @author linguangliang
 */
public class JsonUtils {

	// gson 
	private static final Gson GSON_OBJ = new Gson();

	// 对象到json
	public static String toJsonString(Object object) {
		return GSON_OBJ.toJson(object);
	}

	//json到对象
	public static <T> T parseObject(String jsonString, Class<T> clazz) {
		return GSON_OBJ.fromJson(jsonString, clazz);
	}
}
