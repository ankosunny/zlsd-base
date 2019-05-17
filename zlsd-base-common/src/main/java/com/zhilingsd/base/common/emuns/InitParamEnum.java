package com.zhilingsd.base.common.emuns;

import com.google.common.collect.Lists;
import com.zhilingsd.base.common.bean.KeyValueBean;
import com.zhilingsd.base.common.constants.BaseDictConstants;

import java.util.List;

/**
 * ^---^---^---^---^---^---^---^
 * --v---v---v---v---v---v---v--
 *
 * @author zou.cp
 * @version 1.0
 * @Description 下拉参数属性枚举 ( key,value )说明:key=1 表示在字典表 key=0 表示在常量表
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
    BILL_STATUS(1, BaseDictConstants.BILL_STATUS),
    /**
     * @description 案件归类状态
     **/
    BILL_CLASSIFY_STATUS(1, BaseDictConstants.BILL_CLASSIFY_STATUS),
    /**
     * @description 电话状态
     **/
    TEL_STATUS(1, BaseDictConstants.TEL_STATUS),
    /**
     * @description 案件跟进情况
     **/
    BILL_FOLLOW_TYPE(1, BaseDictConstants.BILL_FOLLOW_TYPE),
    /**
     * @description 性别
     **/
    SEX(1, BaseDictConstants.SEX),
    /**
     * @description 外访分配状态
     **/
    VISIT_ALLOT_STATUS(1, BaseDictConstants.VISIT_ALLOT_STATUS),
    /**
     * @description 外访状态
     **/
    VISIT_STATUS(1, BaseDictConstants.VISIT_STATUS),
    /**
     * @description 还款状态
     **/
    REPAY_STATUS(1, BaseDictConstants.REPAY_STATUS),
    /**
     * @description 案件停催状态
     **/
    BILL_STOP_STATUS(1, BaseDictConstants.BILL_STOP_STATUS),
    /**
     * @description 手机停催状态
     **/
    PHONE_STOP_STATUS(1, BaseDictConstants.PHONE_STOP_STATUS),
    /**
     * @description 申请类型
     **/
    APPLY_TYPE(1, BaseDictConstants.APPLY_TYPE),
    /**
     * @description 审批状态
     **/
    APPROVER_STATUS(1, BaseDictConstants.APPROVER_STATUS),
    /**
     * @description 案件分配状态
     **/
    ALLOT_STATUS(1, BaseDictConstants.ALLOT_STATUS),
    /**
     * @description 地址类型
     **/
    ADDRESS_TYPE(1, BaseDictConstants.ADDRESS_TYPE),
    /**
     * @description 客户关系
     **/
    CLIENT_RELATION(1, BaseDictConstants.CLIENT_RELATION),
    /**
     * @description 币种
     **/
    CURRENCY(1, BaseDictConstants.CURRENCY),
    /**
     * 函件类型
     */
    JUSTICE_TYPE(1, BaseDictConstants.JUSTICE_TYPE),
/******************** 使用CollectionType作为下拉 ，查询需要通过现有流程进行处理 ****************************/
    /**
     * @description 催收流程
     **/
    COLLECTION_TYPE(1, BaseDictConstants.COLLECTION_TYPE),

/************************ 常量信息表 ***********************************************/
    /**
     * @description 手别
     **/
    BACTH_TIME(1, BaseDictConstants.BACTH_TIME),
    /**
     * @description 户籍地址
     **/
    REGISTERED_ADDRESS(0, BaseDictConstants.REGISTERED_ADDRESS),
    /**
     * @description 案件地区
     **/
    CASE_AREA(0, BaseDictConstants.CASE_AREA),
    /**
     * @description 账龄种类
     **/
    BUCKET(0, BaseDictConstants.BUCKET),
    /**
     * @description 产品名称
     **/
    PRODUCT_NAME(0, BaseDictConstants.PRODUCT_NAME),
    ;

    /**
     * @description 案件中心下拉列表
     **/
    public static List<String> getBillCenterTypes() {
        List<String> list = Lists.newArrayList(BILL_STATUS.getValue(), BILL_CLASSIFY_STATUS.getValue(), COLLECTION_TYPE.getValue(),
                BACTH_TIME.getValue(), TEL_STATUS.getValue(), BILL_FOLLOW_TYPE.getValue());
        return list;
    }

    /**
     * @description 人工催收下拉列表
     **/
    public static List<String> getManualTypes() {
        List<String> list = Lists.newArrayList(BILL_STATUS.getValue(), BILL_CLASSIFY_STATUS.getValue(), COLLECTION_TYPE.getValue(),
                BACTH_TIME.getValue(), TEL_STATUS.getValue(), BILL_FOLLOW_TYPE.getValue());
        return list;
    }

    /**
     * @description 外访下拉
     **/
    public static List<String> getVisitTypes() {
        List<String> list = Lists.newArrayList(BILL_STATUS.getValue(), BILL_CLASSIFY_STATUS.getValue(), COLLECTION_TYPE.getValue(),
                VISIT_ALLOT_STATUS.getValue(), VISIT_STATUS.getValue());
        return list;
    }

    /**
     * @description 函件下拉
     **/
    public static List<String> getJusticeTypes() {
        List<String> list = Lists.newArrayList(BILL_STATUS.getValue(), JUSTICE_TYPE.getValue());
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

    /**
     * @description 判断是否是字典数据
     **/
    public static boolean isDict(String value) {
        for (InitParamEnum osEnum : InitParamEnum.values()) {
            if (value.equals(osEnum.getValue()) && osEnum.getCode() == 1) {
                return true;
            }
        }
        return false;
    }

    /**
     * @description 判断是否是常量数据
     **/
    public static boolean isConstant(String value) {
        for (InitParamEnum osEnum : InitParamEnum.values()) {
            if (value.equals(osEnum.getValue()) && osEnum.getCode() == 0) {
                return true;
            }
        }
        return false;
    }


}
