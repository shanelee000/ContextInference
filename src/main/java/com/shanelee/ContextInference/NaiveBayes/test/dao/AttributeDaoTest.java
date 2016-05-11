package com.shanelee.ContextInference.NaiveBayes.test.dao;

import com.alibaba.fastjson.JSON;
import com.shanelee.ContextInference.NaiveBayes.dao.AttributeDao;
import com.shanelee.ContextInference.NaiveBayes.entity.AttributeEntity;
import com.shanelee.ContextInference.NaiveBayes.utils.ContextInferenceUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

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
        entity.setSound("normal");
        entity.setTemperature("normal");
        entity.setHumidity("high");
        entity.setPosition("standing");
        entity.setMovement("moving");
        entity.setGps("outdoor");
        String context = ContextInferenceUtil.inferContext(entity);
//        System.out.println("\n>>>>>>>>>>>>>>>>>>>>>>>>>" + JSON.toJSONString(ContextInferenceUtil.PROBABILITY_MATRIX));
        System.out.println("\n>>>>>>>>>>>>>>>>>>>>>>>>" + context);
    }
}
