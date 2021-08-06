package com.zhilingsd.base.common.bean;

/**
 * 操作人信息，API层设置
 * @author 灭霸
 * @date 2019-5-12 20:30:01
 * */
public class AppAgentInfo {
    /**
     * 相当于token
     */
    private String session;
    /**
     * 当前登录用户Id
     */
    private Long operatorId;


    public AppAgentInfo() {
    }

    public AppAgentInfo(String session, Long operatorId) {
        this.session = session;
        this.operatorId = operatorId;
    }

    public AppAgentInfo(Long operatorId,  String session) {
        this.operatorId = operatorId;
        this.session=session;

    }

    public Long getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(Long operatorId) {
        this.operatorId = operatorId;
    }


    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }
}
