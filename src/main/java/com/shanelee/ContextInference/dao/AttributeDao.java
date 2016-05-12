package com.shanelee.ContextInference.dao;

import com.shanelee.ContextInference.entity.AttributeEntity;

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

}
