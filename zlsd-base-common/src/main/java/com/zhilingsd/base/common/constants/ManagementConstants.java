package com.zhilingsd.base.common.constants;

public class ManagementConstants {

	// 定义组织类型
	// 集团
	public static final String ORG_TYPE_CONSORTIUM = "CONSORTIUM";
	// 委员会(委案方)
	public static final String ORG_TYPE_COMMITTEE_CONSIGNER = "COMMITTEE_CONSIGNER";
	//委员会（催收方）
	public static final String ORG_TYPE_COMMITTEE_ASSIGNEE = "COMMITTEE_ASSIGNEE";
	// 总公司
	public static final String ORG_TYPE_COMPANY = "COMPANY";
	// 分公司
	public static final String ORG_TYPE_SUBCOMPANY = "SUBCOMPANY";
	// 部门
	public static final String ORG_TYPE_DEPARTMENT = "DEPARTMENT";

	//定义用户组织类型
	public static final int USER_ORGANIZATION_TYPE_DEFAULT=0;
	public static final int USER_ORGANIZATION_TYPE_PART=1;

	//定义组织状态
	public static final int ORGANIZATION_STATUS_ABLE=0;
	public static final int ORGANIZATION_STATUS_DISABLE=1;

	//定义职位类型
	public static final String POSITION_TYPE_ADMINISTRATOR = "ADMINISTRATOR";
	public static final String POSITION_TYPE_STAFF = "STAFF";

	public static String translateOrgType(String s) {
		if (s == null) {
			return null;
		}
		switch (s) {
		case ORG_TYPE_CONSORTIUM:
			return "集团";
		case ORG_TYPE_COMMITTEE_CONSIGNER:
			return "委员会(委案方)";
		case ORG_TYPE_COMPANY:
			return "总公司";
		case ORG_TYPE_SUBCOMPANY:
			return "分公司";
		case ORG_TYPE_DEPARTMENT:
			return "部门";
		case ORG_TYPE_COMMITTEE_ASSIGNEE:
			return "委员会(催收方)";
		default:
			return "";
		}
	}
}
