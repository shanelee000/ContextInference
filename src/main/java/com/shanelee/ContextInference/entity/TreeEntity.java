package com.shanelee.ContextInference.entity;

import java.util.List;

/**
 * 决策树模型
 */
public class TreeEntity {

    //该节点的分类名称
    private String attrName;

    //该节点上方的引导分类属性
    private String attribute;

    //该节点下方的子节点
    private List<TreeEntity> children;

    public String getAttrName() {
        return attrName;
    }

    public void setAttrName(String attrName) {
        this.attrName = attrName;
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public List<TreeEntity> getChildren() {
        return children;
    }

    public void setChildren(List<TreeEntity> children) {
        this.children = children;
    }
}
