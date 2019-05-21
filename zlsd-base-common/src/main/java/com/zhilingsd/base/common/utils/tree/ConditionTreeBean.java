package com.zhilingsd.base.common.utils.tree;

import java.io.Serializable;


public class ConditionTreeBean extends TreeNode implements Serializable{
    private static final long serialVersionUID = 3887559806021822087L;
    private Long id;
    private String name;
    private Integer functionType;//(0-普通机构，1-预测式外呼机构)

    public Integer getFunctionType() {
        return functionType;
    }

    public void setFunctionType(Integer functionType) {
        this.functionType = functionType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}