package com.zhilingsd.base.common.bean;

/**
 * 操作人信息，API层设置
 *
 * @author 灭霸
 * @date 2019-5-12 20:30:01
 */
public class AppAgentInfo {
    /**
     * 相当于token
     */
    private String session;
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


    public AppAgentInfo() {
    }

    public AppAgentInfo(String session, Long operatorId) {
        this.session = session;
        this.operatorId = operatorId;
    }

    public AppAgentInfo(Long operatorId, Long collectionCompanyId, String session, Long collectionGroupId) {
        this.operatorId = operatorId;
        this.collectionCompanyId = collectionCompanyId;
        this.session = session;
        this.collectionGroupId = collectionGroupId;
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

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }
}
