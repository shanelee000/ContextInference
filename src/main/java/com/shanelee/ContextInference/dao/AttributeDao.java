package com.shanelee.ContextInference.dao;

import com.shanelee.ContextInference.entity.AttributeEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by ShaneLee on 16/5/11.
 */
public interface AttributeDao {

    /**
     * 获取所有训练数据
     * @return
     */
    List<AttributeEntity> queryAllAttributes();

    /**
     * 获取用于计算准确率的数据
     * @return
     */
    List<AttributeEntity> queryTestDatas();

    int insert(AttributeEntity attr);

    int insertBatch(@Param("list") List<AttributeEntity> list);

}
