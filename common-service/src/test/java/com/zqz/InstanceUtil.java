package com.zqz;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class InstanceUtil {

    static String key = "3eac2241aca3b6f67079173fe3f3b8ec";


    public static String getGeo(String address) {
        String s = "http://restapi.amap.com/v3/geocode/geo?address=%s&output=JSON&key=%s";
        String url = String.format(s, address, key);
        String result = HttpUtil.get(url);
        JSONObject m = JSON.parseObject(result);
        JSONArray geocodes = m.getJSONArray("geocodes");
        String location = geocodes.getJSONObject(0).getString("location");
        return location;
    }

    public static List<String> getGeo(List<String> addresses) {
        String address = String.join("|", addresses);
        String s = "http://restapi.amap.com/v3/geocode/geo?address=%s&output=JSON&key=%s&batch=true";
        String url = String.format(s, address, key);
        String result = HttpUtil.get(url);
        JSONObject m = JSON.parseObject(result);
        JSONArray geocodes = m.getJSONArray("geocodes");
        Integer size = addresses.size();
        List<String> locations = new ArrayList<>(size);
        for (int k = 0; k < size; k++) {
            String location = geocodes.getJSONObject(k).getString("location");
            locations.add(location);
        }
        return locations;
    }

    public static List<Integer> getDistance(List<String> addresses, String address) {
        List<Integer> distances = new ArrayList<>();
        List<String> locations = getGeo(addresses);
        String origins = String.join("|", locations);
        String destination = getGeo(address);
        String d = "http://restapi.amap.com/v3/distance?origins=%s&&destination=%s&output=JSON&key=%s&type=1";
        String url2 = String.format(d, origins, destination, key);
        String result2 = HttpUtil.get(url2);
        JSONObject m2 = JSON.parseObject(result2);
        JSONArray results = m2.getJSONArray("results");
        Integer size = addresses.size();
        for (int k = 0; k < size; k++) {
            Integer distance = results.getJSONObject(k).getInteger("distance");
            distances.add(distance);
        }
        System.out.println(distances);
        return distances;
    }

    public static Integer getDistance(String address1, String address2) {
        String address = address1 + "|" + address2;
        String key = "3eac2241aca3b6f67079173fe3f3b8ec";
        String s = "http://restapi.amap.com/v3/geocode/geo?address=%s&output=JSON&key=%s&batch=true";
        String url = String.format(s, address, key);
        String result = HttpUtil.get(url);
        JSONObject m = JSON.parseObject(result);
        JSONArray geocodes = m.getJSONArray("geocodes");
        String location1 = geocodes.getJSONObject(0).getString("location");
        String location2 = geocodes.getJSONObject(1).getString("location");
        String d = "http://restapi.amap.com/v3/distance?origins=%s&&destination=%s&output=JSON&key=%s&type=1";
        String url2 = String.format(d, location1, location2, key);
        String result2 = HttpUtil.get(url2);
        JSONObject m2 = JSON.parseObject(result2);
        JSONArray results = m2.getJSONArray("results");
        Integer distance = results.getJSONObject(0).getInteger("distance");
        System.out.println(distance);
        return distance;
    }



    public static void main(String[] args) {
        String address1 = "宜昌市第一人民医院";
        String address2 = "宜昌市中心人民医院";
        String address = "宜昌市万达广场";
        List<String> addresses = new ArrayList<>();
        addresses.add(address1);
        addresses.add(address2);
        getDistance(addresses, address);
    }
}
