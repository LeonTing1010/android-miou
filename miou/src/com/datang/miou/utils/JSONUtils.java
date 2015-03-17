package com.datang.miou.utils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

public class JSONUtils {

	public static <T> JSONObject toJson(T cls) {
		Map<String, Object> copyForm = new HashMap<String, Object>();
		Field[] fields = cls.getClass().getDeclaredFields();
		if (fields != null && fields.length > 0) {
			try {
				for (Field field : fields) {
					field.setAccessible(true);
					Object fieldValue = field.get(cls);
					if(fieldValue != null) {
						copyForm.put(field.getName(), fieldValue);
					}
				}
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return new JSONObject(copyForm);
	}
	
	public static <T> String toJsonStr(T cls) {
		return toJson(cls).toString();
	}
	
	public static <T> T fromJson(T obj, String json) {
		try {
			return fromJson(obj, new JSONObject(json));
		} catch (JSONException e) {
			return null;
		}
	}
	
	public static <T> T fromJson(T obj, JSONObject jsonObject) {
		try {
			Field[] fields = obj.getClass().getDeclaredFields();
			for(Field field : fields) {
				field.setAccessible(true);
				if(jsonObject.has(field.getName())) {
					field.set(obj, jsonObject.get(field.getName()));
				}
			}
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return obj;
	}
}
