package com.shanelee.ContextInference;

import com.alibaba.fastjson.JSON;
import com.shanelee.ContextInference.entity.IAttributes;
import com.shanelee.ContextInference.entity.Light;

import java.util.*;

/**
 * Created by lx on 2016/5/17.
 */
public class CommonUtil {

    /**
     * 根据map的value中的double值大小给map排序
     * @param map 传入的map参数
     * @return
     */
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

    /**
     * 根据枚举类的名称获取对应枚举类中的枚举值
     * @param name 类名
     * @return
     */
    public static String[] getEnumPropNamesByClazzName(String name){
        String fullPackageUrl = "com.shanelee.ContextInference.entity." +
                name.trim().substring(0,1).toUpperCase() +
                name.trim().substring(1).toLowerCase();
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

}
