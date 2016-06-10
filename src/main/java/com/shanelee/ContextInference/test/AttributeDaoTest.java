package com.shanelee.ContextInference.test;

import com.alibaba.fastjson.JSON;
import com.shanelee.ContextInference.CommonUtil;
import com.shanelee.ContextInference.dao.AttributeDao;
import com.shanelee.ContextInference.entity.AttributeEntity;
import com.shanelee.ContextInference.NaiveBayes.ContextInferenceUtil;
import com.shanelee.ContextInference.entity.TreeEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by ShaneLee on 16/5/11.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/spring/spring-mvc.xml" })
public class AttributeDaoTest {

    @Autowired
    private AttributeDao attributeDao;

    @Test
    public void testQueryAllAttribute(){
        List<AttributeEntity> list = this.attributeDao.queryAllAttributes();
        ContextInferenceUtil.handleTrainingData(list);
        AttributeEntity entity = new AttributeEntity();
        entity.setLight("very bright");
        entity.setSound("very loud");
        entity.setTemperature("normal");
        entity.setHumidity("medium");
        entity.setPosition("lying");
        entity.setMovement("moving fast");
        entity.setGps("outdoor");
        entity.setTime("afternoon");

        //测试贝叶斯
        long NbcBeginTime = System.currentTimeMillis();
        ContextInferenceUtil.handleTrainingData(list);
        String context = ContextInferenceUtil.inferContext(entity);
        long NbcEndTime = System.currentTimeMillis();
//        int i = 1;
//        System.out.println("条件概率矩阵：");
//        System.out.print(">>>>>>>>>>>>>>>>>>");
//        for (Map.Entry entry : ContextInferenceUtil.PROBABILITY_MATRIX.entrySet()){
//            System.out.print(entry.getKey() + "---" + entry.getValue() + "\t");
//            i++;
//            if(i % 5 == 0){
//                System.out.print("\n");
//                System.out.print(">>>>>>>>>>>>>>>>>>");
//            }
//        }
////        System.out.println("\n>>>>>>>>>>>>>>>>>>>>>>>" + JSON.toJSONString(ContextInferenceUtil.PROBABILITY_MATRIX));
        System.out.println("\n>>>>>>>>>>>>>>>>>>>>>>>上下文：" + context);
        System.out.println(">>>>>>>>>>朴素贝叶斯分析时间：" + (NbcEndTime - NbcBeginTime) + "毫秒");
//
//        System.out.println("朴素贝叶斯正确率：" + ContextInferenceUtil.calCorrectProb(this.attributeDao.queryTestDatas()));

        //测试构造决策树
        long TreeBeginTime = System.currentTimeMillis();
        TreeEntity tree = com.shanelee.ContextInference.DecisionTree.ContextInferenceUtil.getDecisionTree(list);
//        System.out.println(JSON.toJSONString(tree));
//
//        //测试决策树
        String context1 = com.shanelee.ContextInference.DecisionTree.ContextInferenceUtil.inferContext(tree, entity);
        long TreeEndTime = System.currentTimeMillis();
        System.out.println("\n>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + context1);
        System.out.println(">>>>>>>>>决策树分析时间：" + (TreeEndTime - TreeBeginTime) + "毫秒");

        //测试决策树正确率
//        System.out.println("决策树正确率：" + com.shanelee.ContextInference.DecisionTree.ContextInferenceUtil.calCorrectProb(tree, this.attributeDao.queryTestDatas()));
    }

    @Test
    public void doInsertDatas(){
        List<AttributeEntity> list = new ArrayList<AttributeEntity>();
        String[] lightArray = CommonUtil.getEnumPropNamesByClazzName("light");
        String[] soundArray = CommonUtil.getEnumPropNamesByClazzName("sound");
        String[] tempArray = CommonUtil.getEnumPropNamesByClazzName("temperature");
        String[] humidityArray = CommonUtil.getEnumPropNamesByClazzName("humidity");
        String[] positionArray = CommonUtil.getEnumPropNamesByClazzName("position");
        String[] movementArray = CommonUtil.getEnumPropNamesByClazzName("movement");
        String[] gpsArray = CommonUtil.getEnumPropNamesByClazzName("gps");
        String[] timeArray = CommonUtil.getEnumPropNamesByClazzName("time");
        String[] contextArray = CommonUtil.getEnumPropNamesByClazzName("context");

        for (String light : lightArray){
            for (String sound : soundArray){
                for (String temp : tempArray){
                    for (String humidity : humidityArray){
                        for (String position : positionArray){
                            for (String movement : movementArray){
                                for (String gps : gpsArray){
                                    for (String time : timeArray){
                                        AttributeEntity entity = new AttributeEntity();
                                        entity.setLight(light);
                                        entity.setSound(sound);
                                        entity.setTemperature(temp);
                                        entity.setHumidity(humidity);
                                        entity.setPosition(position);
                                        entity.setMovement(movement);
                                        entity.setGps(gps);
                                        entity.setTime(time);
                                        entity.setContext(contextArray[new Random().nextInt(contextArray.length)]);
                                        list.add(entity);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        int rows = this.attributeDao.insertBatch(list);
    }
}
