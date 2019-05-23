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
    private Long operatorId;
    /**
     * 当前登录用户所在的催收机构Id
     */
    private Long collectionCompanyId;

    /**
     * 当前登录用户所在的催收小组Id
     */
    private long collectionGroupId;


    private long resourceId;

    public AppAgentInfo() {
    }

    public AppAgentInfo(Long operatorId, Long collectionCompanyId) {
        this.operatorId = operatorId;
        this.collectionCompanyId = collectionCompanyId;
    }

    public Long getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(Long operatorId) {
        this.operatorId = operatorId;
    }

    public Long getCollectionCompanyId() {
        return collectionCompanyId;
    }

    public void setCollectionCompanyId(Long collectionCompanyId) {
        this.collectionCompanyId = collectionCompanyId;
    }

    public long getCollectionGroupId() {
        return collectionGroupId;
    }

    public void setCollectionGroupId(long collectionGroupId) {
        this.collectionGroupId = collectionGroupId;
    }

    public long getResourceId() {
        return resourceId;
    }

    public void setResourceId(long resourceId) {
        this.resourceId = resourceId;
    }
}
