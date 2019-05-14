package com.zhilingsd.base.common.bean;

/**
 * 操作人信息，API层设置
 * @author 灭霸
 * @date 2019-5-12 20:30:01
 * */
public class AppAgentInfo {
    /**
     * 当前登录用户Id
     */
    private long operatorId;
    /**
     * 当前登录用户所在的催收机构Id
     */
    private long collectionCompanyId;

    /**
     * 当前登录用户所在的催收小组Id
     */
    private long collectionGroupId;

    public AppAgentInfo(long operatorId, long collectionCompanyId,long collectionGroupId) {
        this.operatorId = operatorId;
        this.collectionCompanyId = collectionCompanyId;
        this.collectionGroupId = collectionGroupId;
    }

    public long getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(long operatorId) {
        this.operatorId = operatorId;
    }

    public long getCollectionCompanyId() {
        return collectionCompanyId;
    }

    public void setCollectionCompanyId(long collectionCompanyId) {
        this.collectionCompanyId = collectionCompanyId;
    }

    public long getCollectionGroupId() {
        return collectionGroupId;
    }

    public void setCollectionGroupId(long collectionGroupId) {
        this.collectionGroupId = collectionGroupId;
    }
}
