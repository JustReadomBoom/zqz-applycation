package com.zqz.service.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zqz.service.entity.GoodsStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: zqz
 * @Description:
 * @Date: Created in 11:08 2020/10/15
 */
public class JsonUtil {
    private static final Logger log = LoggerFactory.getLogger(JsonUtil.class);


    /**
     * @Author: zqz
     * @Description: Json —> Map
     * @Param: [jsonStr]
     * @Return: java.util.Map<java.lang.String,java.lang.Object>
     * @Date: 11:11 2020/10/15
     */
    public static Map<String, Object> jsonToMap(String jsonStr) {
        try {
            Map<String, Object> map = new HashMap<>();
            //最外层解析
            JSONObject json = JSON.parseObject(jsonStr);
            for (Object k : json.keySet()) {
                Object v = json.get(k);
                map.put(k.toString(), v);
            }
            return map;
        }catch (Exception e){
            log.error("Json to Map Error:[{}]", e.getMessage(), e);
            return null;
        }
    }

    /**
     * @Author: zqz
     * @Description: List -> Json
     * @Param: [list]
     * @Return: java.lang.String
     * @Date: 11:17 2020/10/15
     */
    public static <T> String listToJson(List<T> list) {
        try {
            String listStr = JSON.toJSONString(list);
            JSONArray jsonArray = JSON.parseArray(listStr);
            return jsonArray.toString();
        }catch (Exception e){
            log.error("List to Json Error:[{}]", e.getMessage(), e);
            return null;
        }
    }

    /**
     * @Author: zqz
     * @Description: Map -> Json
     * @Param: [map]
     * @Return: java.lang.String
     * @Date: 11:42 2020/10/15
     */
    public static String mapToJson(Map<String, Object> map){
        try {
            JSONObject jb = new JSONObject(map);
            return jb.toJSONString();
        }catch (Exception e){
            log.error("Map to Json Error:[{}]", e.getMessage(), e);
            return null;
        }
    }


    public static void main(String[] args) {
        List<GoodsStore> list = new ArrayList<>();
        GoodsStore store1 = new GoodsStore();
        GoodsStore store2 = new GoodsStore();

        store1.setCode("111");
        store1.setStore(111);

        store2.setCode("222");
        store2.setStore(222);

        list.add(store1);
        list.add(store2);

        String json = listToJson(list);
        System.out.println(json);

        Map<String, Object> map = new HashMap<>();
        map.put("name", "zqz");
        map.put("age", 27);
        String mapJson = mapToJson(map);
        System.out.println(mapJson);

    }









}
