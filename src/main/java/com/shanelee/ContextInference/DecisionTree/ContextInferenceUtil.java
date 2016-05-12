package com.shanelee.ContextInference.DecisionTree;

import com.shanelee.ContextInference.entity.AttributeEntity;
import com.sun.xml.internal.messaging.saaj.packaging.mime.util.LineInputStream;
import org.hibernate.validator.constraints.URL;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by lx on 2016/5/12.
 */
public class ContextInferenceUtil {

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

        } else if("sound".equals(featureName)) {

        } else if("temperature".equals(featureName)) {

        } else if("humidity".equals(featureName)) {

        } else if("position".equals(featureName)) {

        } else if("movement".equals(featureName)) {

        } else if("gps".equals(featureName)) {

        }

        double conditionEntropyOfDataSet = 0;
        for (Map.Entry<String, List<AttributeEntity>> entry : newListMap.entrySet()) {
            conditionEntropyOfDataSet += (entry.getValue().size() / attrList.size()) * calEntropyOfDataSet(entry.getValue());
        }

        return conditionEntropyOfDataSet;
    }

    private static Map<String, List<AttributeEntity>> buildLightListMap(List<AttributeEntity> attrList){
        return null;
    }
}