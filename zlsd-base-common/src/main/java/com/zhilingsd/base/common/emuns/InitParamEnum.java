package com.zhilingsd.base.common.emuns;

import com.google.common.collect.Lists;
import com.zhilingsd.base.common.constants.BaseDictConstants;

import java.util.List;

/**
 * ^---^---^---^---^---^---^---^
 * --v---v---v---v---v---v---v--
 *
 * @author zou.cp
 * @version 1.0
 * @Description 下拉参数枚举
 * @createTime 2019年05月04日 14:22*
 * log.info()
 */
public enum InitParamEnum {

    /**
     * @description 批次分配状态
     **/
    PACKET_ALLOT_STATUS(1, BaseDictConstants.PACKET_ALLOT_STATUS),
    /**
     * @description 案件状态
     **/
    BILL_STATUS(2, BaseDictConstants.BILL_STATUS),
    /**
     * @description 案件归类状态
     **/
    BILL_CLASSIFY_STATUS(3, BaseDictConstants.BILL_CLASSIFY_STATUS),
    /**
     * @description 手别
     **/
    BACTH_TIME(4, BaseDictConstants.BACTH_TIME),
    /**
     * @description 电话状态
     **/
    TEL_STATUS(5, BaseDictConstants.TEL_STATUS),
    /**
     * @description 案件跟进情况
     **/
    BILL_FOLLOW_TYPE(6, BaseDictConstants.BILL_FOLLOW_TYPE),
    /**
     * @description 性别
     **/
    SEX(7, BaseDictConstants.SEX),
    /**
     * @description 外访分配状态
     **/
    VISIT_ALLOT_STATUS(8, BaseDictConstants.VISIT_ALLOT_STATUS),
    /**
     * @description 外访状态
     **/
    VISIT_STATUS(9, BaseDictConstants.VISIT_STATUS),
    /**
     * @description 还款状态
     **/
    REPAY_STATUS(10, BaseDictConstants.REPAY_STATUS),
    /**
     * @description 案件停催状态
     **/
    BILL_STOP_STATUS(11, BaseDictConstants.BILL_STOP_STATUS),
    /**
     * @description 手机停催状态
     **/
    PHONE_STOP_STATUS(12, BaseDictConstants.PHONE_STOP_STATUS),
    /**
     * @description 申请类型
     **/
    APPLY_TYPE(13, BaseDictConstants.APPLY_TYPE),
    /**
     * @description 审批状态
     **/
    APPROVER_STATUS(14, BaseDictConstants.APPROVER_STATUS),
    /**
     * @description 催收流程
     **/
    COLLECTION_TYPE(15,BaseDictConstants.COLLECTION_TYPE),
    /**
     * @description  案件分配状态
     **/
    ALLOT_STATUS(16,BaseDictConstants.ALLOT_STATUS),
    /**
     * @description 地址类型  
     **/
    /**
     * @description  客户关系
     **/
    ;

    /**
     * @description  案件中心下拉列表
     **/
    public static List<String> getBillCenterDictTypes(){
        List<String> list = Lists.newArrayList(BILL_STATUS.getValue(),BILL_CLASSIFY_STATUS.getValue(),COLLECTION_TYPE.getValue(),
                BACTH_TIME.getValue(),TEL_STATUS.getValue(),BILL_FOLLOW_TYPE.getValue());
        return list;
    }
    /**
     * @description  人工催收下拉列表
     **/
    public static List<String> getManualDictTypes(){
        List<String> list = Lists.newArrayList(BILL_STATUS.getValue(),BILL_CLASSIFY_STATUS.getValue(),COLLECTION_TYPE.getValue(),
                BACTH_TIME.getValue(),TEL_STATUS.getValue(),BILL_FOLLOW_TYPE.getValue());
        return list;
    }

    private int code;
    private String value;

    InitParamEnum(int code, String value) {
        this.code = code;
        this.value = value;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public static String getValueByCode(int code) {
        for (InitParamEnum osEnum : InitParamEnum.values()) {
            if (osEnum.getCode() == code) {
                return osEnum.getValue();
            }
        }
        return "";
    }


}
