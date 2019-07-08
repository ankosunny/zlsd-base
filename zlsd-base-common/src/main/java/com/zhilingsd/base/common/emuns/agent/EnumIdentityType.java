package com.zhilingsd.base.common.emuns.agent;

/**
 * 身份类型
 * @author linmenghuai
 * @date 2019-5-14 11:35:24
 * */
public enum EnumIdentityType {
/*    MANAGER("manager","管理"),
    STAFF("staff","职工"),*/
	
	AGENT_IDENTITY_CSY("agent","催收员"),
	AGENT_IDENTITY_XAZZ("grop_leader","专案组长"),
	AGENT_IDENTITY_ZAZG("charge","专案主管"),
	AGENT_IDENTITY_FGSJL("manager","分公司经理"),
	AGENT_IDENTITY_XMZJ("director","项目总监"),
	AGENT_IDENTITY_ZJLJ("president","总经理级"),
	AGENT_IDENTITY_WFZY("visiter","外访专员"),
	AGENT_IDENTITY_XZZY("office_admin","行政人员"),
	AGENT_IDENTITY_RSRY("personnel","人事人员"),
	AGENT_IDENTITY_CWRY("financial_staff","财务人员"),
	AGENT_IDENTITY_PGRY("qc_staff","品管人员"),
	AGENT_IDENTITY_HQGL("logistics_manager","后勤管理"),
	AGENT_IDENTITY_WQWY("outer_clerk","外区文员"),
	AGENT_IDENTITY_WQWG("outer_network_manger","外区网管"),
	AGENT_IDENTITY_ZBWG("headquarters_netw_mager","总部网管"),
    ;

    /** 状态码 **/
    private String code;
    /** 状态描述 **/
    private String description;


    EnumIdentityType(String code, String description) {
        this.code = code;
        this.description = description;
    }


    public static EnumIdentityType find(int code) {
        for (EnumIdentityType frs : EnumIdentityType.values()) {
            if (frs.getCode().equals(code)) {
                return frs;
            }
        }
        return null;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
