package com.dcits.order.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

public class JsonManager {

	public static String toJson(Object obj)
	{
		return JSON.toJSONString(obj, SerializerFeature.WriteDateUseDateFormat, SerializerFeature.DisableCircularReferenceDetect); 
	}
	
	public static <T> T fromJson(String json, Class<T> type)
	{
		if (json.equals("") || json == null) {
			return null;
		}
		return JSON.parseObject(json, type);
	}
	

}
