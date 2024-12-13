package org.jingtao8a.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import lombok.extern.slf4j.Slf4j;
import java.util.*;

@Slf4j
public class JsonUtils {
    public static String convertObj2Json(Object obj) {
        if (null == obj) {
            return null;
        }
        return JSON.toJSONString(obj, SerializerFeature.DisableCircularReferenceDetect);
    }

    public static <T> T convertJson2Obj(String json, Class<T> clazz) { return JSONObject.parseObject(json, clazz); }

    public static <T> List<T> convertJson2List(String json, Class<T> clazz) { return JSONArray.parseArray(json, clazz);}
}
