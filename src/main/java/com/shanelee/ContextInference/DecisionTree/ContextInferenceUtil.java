package com.shanelee.ContextInference.DecisionTree;

import com.alibaba.fastjson.JSON;
import com.shanelee.ContextInference.entity.AttributeEntity;
import com.shanelee.ContextInference.entity.Attributes;
import com.shanelee.ContextInference.entity.TreeEntity;
import com.sun.xml.internal.messaging.saaj.packaging.mime.util.LineInputStream;
import org.hibernate.validator.constraints.URL;

import java.util.*;

/**
 * Created by lx on 2016/5/12.
 */
public class ContextInferenceUtil {

    /**
     * 获取决策树
     * @return
     */
    public static TreeEntity getDecisionTree(List<AttributeEntity> attrList){

        //若D为同一类数据,则T为单节点树,返回该类
        if(isAllTheSameContexts(attrList)){
            TreeEntity treeNode = new TreeEntity();
            treeNode.setAttrName(attrList.get(0).getContext());
            treeNode.setAttribute(null);
            treeNode.setChildren(null);
            return treeNode;
        }

        //构建决策树
        List<String> attrNames = new ArrayList<String>();
        attrNames.add("light");
        attrNames.add("sound");
        attrNames.add("temperature");
        attrNames.add("humidity");
        attrNames.add("position");
        attrNames.add("movement");
//        attrNames.add("gps");
        return buildDT(attrList,attrNames);
    }

    /**
     * 计算数据集的信息熵 H(D)
     * @param attrList 数据集
     * @return
     */
    private static double calEntropyOfDataSet(List<AttributeEntity> attrList){

        double[] numOfSampleSetOfClazz = new double[]{0,0,0,0};
        int sizeOfList = attrList.size();

        for (AttributeEntity attr : attrList) {
            if("running".equals(attr.getContext())){
                numOfSampleSetOfClazz[0]++;
            } else if ("walking".equals(attr.getContext())){
                numOfSampleSetOfClazz[1]++;
            } else if ("idle".equals(attr.getContext())){
                numOfSampleSetOfClazz[2]++;
            } else if ("resting".equals(attr.getContext())){
                numOfSampleSetOfClazz[3]++;
            }
        }

        double entropyOfDataSet = 0;
        for (int i = 0; i < numOfSampleSetOfClazz.length; i++) {
            double temp = numOfSampleSetOfClazz[i] / sizeOfList;
            entropyOfDataSet += temp * Math.log(temp);
        }

        return entropyOfDataSet;
     }

    /**
     * 计算特征A对数据集D的条件熵 H(D|A)
     * @param attrList 数据集D
     * @param featureName 特征名
     * @return
     */
    private static double calConditionEntropyOfDataSet(List<AttributeEntity> attrList, String featureName) {

        Map<String, List<AttributeEntity>> newListMap = null;
        int sizeOfList = attrList.size();

        if ("light".equals(featureName)) {
            newListMap = buildLightListMap(attrList);
        } else if("sound".equals(featureName)) {
            newListMap = buildSoundListMap(attrList);
        } else if("temperature".equals(featureName)) {
            newListMap = buildTemperatureListMap(attrList);
        } else if("humidity".equals(featureName)) {
            newListMap = buildHumidityListMap(attrList);
        } else if("position".equals(featureName)) {
            newListMap = buildPositionListMap(attrList);
        } else if("movement".equals(featureName)) {
            newListMap = buildMovementListMap(attrList);
        } else if("gps".equals(featureName)) {
            newListMap = buildGpsListMap(attrList);
        }

        double conditionEntropyOfDataSet = 0;
        for (Map.Entry<String, List<AttributeEntity>> entry : newListMap.entrySet()) {
            conditionEntropyOfDataSet += (entry.getValue().size() / attrList.size()) * calEntropyOfDataSet(entry.getValue());
        }

        return conditionEntropyOfDataSet;
    }

    /**
     * 构造决策树
     * @param attrList 训练集
     * @return
     */
    private static TreeEntity buildDT(List<AttributeEntity> attrList, List<String> attrNames){

        //要返回的决策树的根节点
        TreeEntity treeNode = new TreeEntity();

        Map<String, Double> KLICMap = new HashMap<String, Double>();
        for (String attrName : attrNames) {
            KLICMap.put(attrName,calEntropyOfDataSet(attrList) - calConditionEntropyOfDataSet(attrList,attrName));
        }

        List<Map.Entry<String, Double>> infolds = new ArrayList<Map.Entry<String, Double>>(KLICMap.entrySet());
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
        String maxKey = infolds.get(infolds.size() - 1).getKey();

        return null;
    }

    private static Map<String, List<AttributeEntity>> buildLightListMap(List<AttributeEntity> attrList){

        Map<String, List<AttributeEntity>> newListMap = new HashMap<String, List<AttributeEntity>>();
        List<AttributeEntity> attrOfVeryBright = new ArrayList<AttributeEntity>();
        List<AttributeEntity> attrOfBright = new ArrayList<AttributeEntity>();
        List<AttributeEntity> attrOfNormal = new ArrayList<AttributeEntity>();
        List<AttributeEntity> attrOfDark = new ArrayList<AttributeEntity>();
        List<AttributeEntity> attrOfVeryDark = new ArrayList<AttributeEntity>();

        for (AttributeEntity attr : attrList) {
            if("very bright".equals(attr.getLight())){
                attrOfVeryBright.add(attr);
            } else if ("bright".equals(attr.getLight())){
                attrOfBright.add(attr);
            } else if ("normal".equals(attr.getLight())){
                attrOfNormal.add(attr);
            } else if ("dark".equals(attr.getLight())){
                attrOfDark.add(attr);
            } else if ("very dark".equals(attr.getLight())){
                attrOfVeryDark.add(attr);
            }
        }

        newListMap.put("very bright", attrOfVeryBright);
        newListMap.put("bright", attrOfBright);
        newListMap.put("normal", attrOfNormal);
        newListMap.put("dark", attrOfDark);
        newListMap.put("very dark", attrOfVeryDark);
        return newListMap;
    }

    private static Map<String, List<AttributeEntity>> buildSoundListMap(List<AttributeEntity> attrList){

        Map<String, List<AttributeEntity>> newListMap = new HashMap<String, List<AttributeEntity>>();
        List<AttributeEntity> attrOfVeryLoud = new ArrayList<AttributeEntity>();
        List<AttributeEntity> attrOfLoud = new ArrayList<AttributeEntity>();
        List<AttributeEntity> attrOfNormal = new ArrayList<AttributeEntity>();
        List<AttributeEntity> attrOfQuiet = new ArrayList<AttributeEntity>();
        List<AttributeEntity> attrOfVeryQuiet = new ArrayList<AttributeEntity>();

        for (AttributeEntity attr : attrList) {
            if("very loud".equals(attr.getSound())){
                attrOfVeryLoud.add(attr);
            } else if ("loud".equals(attr.getSound())){
                attrOfLoud.add(attr);
            } else if ("normal".equals(attr.getSound())){
                attrOfNormal.add(attr);
            } else if ("quiet".equals(attr.getSound())){
                attrOfQuiet.add(attr);
            } else if ("very quiet".equals(attr.getSound())){
                attrOfVeryQuiet.add(attr);
            }
        }

        newListMap.put("very loud", attrOfVeryLoud);
        newListMap.put("loud", attrOfLoud);
        newListMap.put("normal", attrOfNormal);
        newListMap.put("quiet", attrOfQuiet);
        newListMap.put("very quiet", attrOfVeryQuiet);
        return newListMap;
    }

    private static Map<String, List<AttributeEntity>> buildTemperatureListMap(List<AttributeEntity> attrList){

        Map<String, List<AttributeEntity>> newListMap = new HashMap<String, List<AttributeEntity>>();
        List<AttributeEntity> attrOfVeryHot = new ArrayList<AttributeEntity>();
        List<AttributeEntity> attrOfHot = new ArrayList<AttributeEntity>();
        List<AttributeEntity> attrOfNormal = new ArrayList<AttributeEntity>();
        List<AttributeEntity> attrOfCold = new ArrayList<AttributeEntity>();
        List<AttributeEntity> attrOfVeryCold = new ArrayList<AttributeEntity>();

        for (AttributeEntity attr : attrList) {
            if("very hot".equals(attr.getTemperature())){
                attrOfVeryHot.add(attr);
            } else if ("hot".equals(attr.getTemperature())){
                attrOfHot.add(attr);
            } else if ("normal".equals(attr.getTemperature())){
                attrOfNormal.add(attr);
            } else if ("cold".equals(attr.getTemperature())){
                attrOfCold.add(attr);
            } else if ("very cold".equals(attr.getTemperature())){
                attrOfVeryCold.add(attr);
            }
        }

        newListMap.put("very hot", attrOfVeryHot);
        newListMap.put("hot", attrOfHot);
        newListMap.put("normal", attrOfNormal);
        newListMap.put("cold", attrOfCold);
        newListMap.put("very cold", attrOfVeryCold);
        return newListMap;
    }

    private static Map<String, List<AttributeEntity>> buildHumidityListMap(List<AttributeEntity> attrList){

        Map<String, List<AttributeEntity>> newListMap = new HashMap<String, List<AttributeEntity>>();
        List<AttributeEntity> attrOfHigh = new ArrayList<AttributeEntity>();
        List<AttributeEntity> attrOfMedium = new ArrayList<AttributeEntity>();
        List<AttributeEntity> attrOfLow = new ArrayList<AttributeEntity>();

        for (AttributeEntity attr : attrList) {
            if("high".equals(attr.getHumidity())){
                attrOfHigh.add(attr);
            } else if ("medium".equals(attr.getHumidity())){
                attrOfMedium.add(attr);
            } else if ("low".equals(attr.getHumidity())){
                attrOfLow.add(attr);
            }
        }

        newListMap.put("high", attrOfHigh);
        newListMap.put("medium", attrOfMedium);
        newListMap.put("low", attrOfLow);
        return newListMap;
    }

    private static Map<String, List<AttributeEntity>> buildPositionListMap(List<AttributeEntity> attrList){

        Map<String, List<AttributeEntity>> newListMap = new HashMap<String, List<AttributeEntity>>();
        List<AttributeEntity> attrOfLying = new ArrayList<AttributeEntity>();
        List<AttributeEntity> attrOfStanding = new ArrayList<AttributeEntity>();

        for (AttributeEntity attr : attrList) {
            if("lying".equals(attr.getPosition())){
                attrOfLying.add(attr);
            } else if ("standing".equals(attr.getPosition())){
                attrOfStanding.add(attr);
            }
        }

        newListMap.put("lying", attrOfLying);
        newListMap.put("standing", attrOfStanding);
        return newListMap;
    }

    private static Map<String, List<AttributeEntity>> buildMovementListMap(List<AttributeEntity> attrList){

        Map<String, List<AttributeEntity>> newListMap = new HashMap<String, List<AttributeEntity>>();
        List<AttributeEntity> attrOfNotMoving = new ArrayList<AttributeEntity>();
        List<AttributeEntity> attrOfMoving = new ArrayList<AttributeEntity>();
        List<AttributeEntity> attrOfMovingFast = new ArrayList<AttributeEntity>();

        for (AttributeEntity attr : attrList) {
            if("not moving".equals(attr.getMovement())){
                attrOfNotMoving.add(attr);
            } else if ("moving".equals(attr.getMovement())){
                attrOfMoving.add(attr);
            } else if ("moving fast".equals(attr.getMovement())){
                attrOfMovingFast.add(attr);
            }
        }

        newListMap.put("not moving", attrOfNotMoving);
        newListMap.put("moving", attrOfMoving);
        newListMap.put("moving fast", attrOfMovingFast);
        return newListMap;
    }

    private static Map<String, List<AttributeEntity>> buildGpsListMap(List<AttributeEntity> attrList){

        Map<String, List<AttributeEntity>> newListMap = new HashMap<String, List<AttributeEntity>>();
        List<AttributeEntity> attrOfIndoor = new ArrayList<AttributeEntity>();
        List<AttributeEntity> attrOfOutdoor = new ArrayList<AttributeEntity>();

        for (AttributeEntity attr : attrList) {
            if("indoor".equals(attr.getGps())){
                attrOfIndoor.add(attr);
            } else if ("outdoor".equals(attr.getGps())){
                attrOfOutdoor.add(attr);
            }
        }

        newListMap.put("indoor", attrOfIndoor);
        newListMap.put("outdoor", attrOfOutdoor);
        return newListMap;
    }

    /**
     * 判断数据集是否为同一类数据
     * @param attrList
     * @return
     */
    private static boolean isAllTheSameContexts(List<AttributeEntity> attrList){
        List<String> contextsList = new ArrayList<String>();
        for (AttributeEntity attr : attrList) {
            if(!contextsList.contains(attr.getContext())){
                contextsList.add(attr.getContext());
            }
        }
        return contextsList.size() == 1;
    }
}