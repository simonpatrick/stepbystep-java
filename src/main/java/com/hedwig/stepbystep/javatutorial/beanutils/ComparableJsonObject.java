package com.hedwig.stepbystep.javatutorial.beanutils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * Created by patrick on 15/7/9.
 *
 * @version $Id$
 */


public class ComparableJsonObject extends JSONObject{

    public Map<String,Object> dataHolder = Maps.newHashMap();

    public ComparableJsonObject(String key,String value){
        dataHolder.put("test","Test");
        dataHolder.put("testMap",Maps.newHashMap());
        ((Map)dataHolder.get("testMap")).put("N","n");
        ((Map)dataHolder.get("testMap")).put("M","m");
        ((Map)dataHolder.get("testMap")).put(key,value);
    }

    public static boolean compare(Map<String,Object> c,Map<String,Object> d,String ignoreFields){
        boolean flag =true;
        for (Entry<String, Object> entry : c.entrySet()) {
            System.out.println(entry.getKey());
            if(entry.getKey().equalsIgnoreCase(ignoreFields)) {
                System.out.println("ignored:"+entry.getKey());
                continue;
            }
            if(entry.getValue() instanceof Map && d.get(entry.getKey()) instanceof Map) {
                flag= compare(((Map)entry.getValue()),((Map)d.get(entry.getKey())),ignoreFields);
            }else if(entry.getValue() instanceof List && d.get(entry.getKey()) instanceof List){
                flag=compareList(((List)entry.getValue()),((List)d.get(entry.getKey())),ignoreFields);
            }else{
                if(!entry.getValue().equals(d.get(entry.getKey()))) flag =false;
            }
        }

        return flag;
    }

    private static boolean compareList(List value, List o, String ignoreFields) {
        //simple return false
        return false;
    }

    public static void main(String[] args) {
        ComparableJsonObject c = new ComparableJsonObject("key","value");
        ComparableJsonObject d = new ComparableJsonObject("key","value_d");
        String ignoreFields ="key";

        System.out.println(ComparableJsonObject.compare(c.dataHolder, d.dataHolder, ignoreFields));
        System.out.println(ComparableJsonObject.compare(d.dataHolder, c.dataHolder, ignoreFields));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        ComparableJsonObject that = (ComparableJsonObject) o;

        return !(dataHolder != null ? !dataHolder.equals(that.dataHolder) : that.dataHolder != null);

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (dataHolder != null ? dataHolder.hashCode() : 0);
        return result;
    }
}
