package com.zhilingsd.base.common.utils.tree;

import java.io.Serializable;


public class ConditionTreeBean extends TreeNode implements Serializable{
    private static final long serialVersionUID = 3887559806021822087L;
    private Long id;
    private String name;

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