package com.zhilingsd.base.common.emuns;

import java.util.HashMap;
import java.util.Map;

public enum EnumAgentIdentity {

	AGENT_IDENTITY_CSY("AID000001","催收员"),
	AGENT_IDENTITY_XAZZ("AID000002","专案组长"),
	AGENT_IDENTITY_ZAZG("AID000003","专案主管"),
	AGENT_IDENTITY_FGSJL("AID000004","分公司经理"),
	AGENT_IDENTITY_XMZJ("AID000005","项目总监"),
	AGENT_IDENTITY_ZJLJ("AID000006","总经理级"),
	AGENT_IDENTITY_WFZY("AID000007","外访专员"),
	AGENT_IDENTITY_XZZY("AID000008","行政人员"),
	AGENT_IDENTITY_RSRY("AID000009","人事人员"),
	AGENT_IDENTITY_CWRY("AID000010","财务人员"),
	AGENT_IDENTITY_PGRY("AID000011","品管人员"),
	AGENT_IDENTITY_HQGL("AID000012","后勤管理"),
	AGENT_IDENTITY_WQWY("AID000013","外区文员"),
	AGENT_IDENTITY_WQWG("AID000014","外区网管"),
	AGENT_IDENTITY_ZBWG("AID000015","总部网管"),
    ;

    /** 状态码 **/
    private String code;
    /** 状态描述 **/
    private String description;


    private EnumAgentIdentity(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public static Map<String, String> GetALL() {
    	Map<String, String> map = new HashMap<>();
        for (EnumDeleteState frs : EnumDeleteState.values()) {
            map.put(frs.getCode(), frs.getDescription());
        }
        return map;
    }

    public static EnumDeleteState find(int code) {
        for (EnumDeleteState frs : EnumDeleteState.values()) {
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
