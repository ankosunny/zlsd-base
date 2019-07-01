package com.zhilingsd.base.common.emuns;

/**
 * 员工职位
 * @author Administrator
 *
 */
public enum EnumAgentPosition {

	AGENT_IDENTITY_CSY(1000001,"催收员"),
	AGENT_IDENTITY_XAZZ(1000002,"专案组长"),
	AGENT_IDENTITY_ZAZG(1000003,"专案主管"),
	AGENT_IDENTITY_FGSJL(1000004,"分公司经理"),
	AGENT_IDENTITY_XMZJ(1000005,"项目总监"),
	AGENT_IDENTITY_ZJLJ(1000006,"总经理级"),
	AGENT_IDENTITY_WFZY(1000007,"外访专员"),
	AGENT_IDENTITY_XZZY(1000008,"行政人员"),
	AGENT_IDENTITY_RSRY(1000009,"人事人员"),
	AGENT_IDENTITY_CWRY(1000010,"财务人员"),
	AGENT_IDENTITY_PGRY(1000011,"品管人员"),
	AGENT_IDENTITY_HQGL(1000012,"后勤管理"),
	AGENT_IDENTITY_WQWY(1000013,"外区文员"),
	AGENT_IDENTITY_WQWG(1000014,"外区网管"),
	AGENT_IDENTITY_ZBWG(1000015,"总部网管"),
    ;

    /** 状态码 **/
    private int code;
    /** 状态描述 **/
    private String description;


    private EnumAgentPosition(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public static EnumAgentPosition find(int code) {
        for (EnumAgentPosition frs : EnumAgentPosition.values()) {
            if (frs.getCode() == code) {
                return frs;
            }
        }
        return null;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

}
