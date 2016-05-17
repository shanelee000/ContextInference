package com.shanelee.ContextInference;

import com.alibaba.fastjson.JSON;
import com.shanelee.ContextInference.entity.IAttributes;
import com.shanelee.ContextInference.entity.Light;

import java.util.*;

/**
 * Created by lx on 2016/5/17.
 */
public class CommonUtil {

    public static List<Map.Entry<String, Double>> sortMapByDoubleValue(Map<String, Double> map){
        List<Map.Entry<String, Double>> infolds = new ArrayList<Map.Entry<String, Double>>(map.entrySet());
        Collections.sort(infolds, new Comparator<Map.Entry<String, Double>>(){
            public int compare(Map.Entry<String, Double> o1, Map.Entry<String, Double> o2) {
                if(o1.getValue() < o2.getValue()){
                    return -1;
                } else if(o1.getValue().equals(o2.getValue())) {
                    return 0;
                } else {
                    return 1;
                }
            }
        });
        return infolds;
    }

    public static String[] getEnumPropNamesByClazzName(String name){
        String fullPackageUrl = "com.shanelee.ContextInference.entity." + name.trim().substring(0,1).toUpperCase() + name.trim().substring(1).toLowerCase();
        Class<?> c = null;
        try {
            c = Class.forName(fullPackageUrl);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        String[] propNames = new String[c.getEnumConstants().length];
        for (int i = 0; i < c.getEnumConstants().length; i++){
            IAttributes a = (IAttributes)c.getEnumConstants()[i];
            propNames[i] = a.getName();
        }
        return propNames;
    }

//    public static void main(String[] args) {
//        String light = "humidity";
//
//        String[] strs = getEnumPropNamesByClazzName(light);
//        System.out.println(JSON.toJSONString(strs));
//
//    }

}
