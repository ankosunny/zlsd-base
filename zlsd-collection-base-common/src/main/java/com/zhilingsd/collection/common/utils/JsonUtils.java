package com.zhilingsd.collection.common.utils;

import com.google.gson.Gson;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.reflect.TypeToken;
import com.google.gson.*;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

	/**
	 * Bean对象转JSON
	 *
	 * @param object
	 * @param dataFormatString
	 * @return
	 */
	public static String beanToJson(Object object, String dataFormatString) {
		if (object != null) {
			if (StringUtils.isEmpty(dataFormatString)) {
				return JSONObject.toJSONString(object);
			}
			return JSON.toJSONStringWithDateFormat(object, dataFormatString);
		} else {
			return null;
		}
	}

	/**
	 * Bean对象转JSON
	 *
	 * @param object
	 * @return
	 */
	public static String beanToJson(Object object) {
		if (object != null) {
			return JSON.toJSONString(object);
		} else {
			return null;
		}
	}

	/**
	 * String转JSON字符串
	 *
	 * @param key
	 * @param value
	 * @return
	 */
	public static String stringToJsonByFastjson(String key, String value) {
		if (StringUtils.isEmpty(key) || StringUtils.isEmpty(value)) {
			return null;
		}
		Map<String, String> map = new HashMap<String, String>(16);
		map.put(key, value);
		return beanToJson(map, null);
	}

	/**
	 * 将json字符串转换成对象
	 *
	 * @param json
	 * @param clazz
	 * @return
	 */
	public static Object jsonToBean(String json, Object clazz) {
		if (StringUtils.isEmpty(json) || clazz == null) {
			return null;
		}
		return JSON.parseObject(json, clazz.getClass());
	}

	/**
	 * json字符串转map
	 *
	 * @param json
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> jsonToMap(String json) {
		if (StringUtils.isEmpty(json)) {
			return null;
		}
		return JSON.parseObject(json, Map.class);
	}

	private static Gson gson;

	private static synchronized Gson newInstance() {
		if (gson == null) {
			gson = new Gson();
		}
		return gson;
	}

	public static String toJson(Object obj) {
		return newInstance().toJson(obj);
	}

	/**
	 * json字符串转换成bean
	 *
	 * @param json json字符串
	 * @param clz  类
	 * @return T
	 */
	public static <T> T toBean(String json, Class<T> clz) {
		return newInstance().fromJson(json, clz);
	}

	/**
	 * json字符串转换成Map
	 *
	 * @param json json字符串
	 * @param clz  类
	 * @return Map
	 */
	public static <T> Map<String, T> toMap(String json, Class<T> clz) {
		Map<String, JsonObject> map = newInstance()
				.fromJson(json, new TypeToken<Map<String, JsonObject>>() {
				}.getType());
		Map<String, T> result = new HashMap<>(map.size());
		for (Map.Entry<String, JsonObject> entry : map.entrySet()) {
			result.put(entry.getKey(), newInstance().fromJson(entry.getValue(), clz));
		}
		return result;
	}


	/**
	 * json字符串转换成List
	 *
	 * @param json json字符串
	 * @param clz  类
	 * @return List
	 */
	public static <T> List<T> toList(String json, Class<T> clz) {
		JsonArray array = new JsonParser().parse(json).getAsJsonArray();
		List<T> list = new ArrayList<>();
		for (final JsonElement elem : array) {
			list.add(newInstance().fromJson(elem, clz));
		}
		return list;
	}
}
