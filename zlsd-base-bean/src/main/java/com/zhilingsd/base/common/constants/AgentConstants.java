package com.zhilingsd.base.common.constants;

/**
 * ^---^---^---^---^---^---^---^
 * --v---v---v---v---v---v---v--
 *
 * @author zou.cp
 * @version 1.0
 * @Description
 * @createTime 2019年06月14日 18:27*
 * log.info()
 */
public class AgentConstants {
    /**
     * 总公司
     */
    public static final String PARENT_COMPANY = "PARENT_COMPANY";

    /**
     * 分公司
     */
    public static final String BRANCH_COMPANY = "BRANCH_COMPANY";

    /**
     * @description 系统默认催收机构ID，用于定时任务等无确定操作人情况
     **/
    public static final Long SYSTEM_COLLECTION_COMPANY_ID = 0L;

    /**
     * @description 系统默认操作人ID，用于定时任务等无确定操作人情况
     **/
    public static final Long SYSTEM_OPERATOR_ID = 0L;

    /**
     * @description 系统默认SESSION，用于定时任务等无确定操作人情况
     **/
    public static final String SYSTEM_SESSION = "00000000-0000-0000-0000-000000000000";
}
