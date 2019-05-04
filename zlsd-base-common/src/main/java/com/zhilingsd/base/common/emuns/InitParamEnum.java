package com.zhilingsd.base.common.emuns;

import com.zhilingsd.base.common.constants.BaseDictConstants;

/**
 * ^---^---^---^---^---^---^---^
 * --v---v---v---v---v---v---v--
 *
 * @author zou.cp
 * @version 1.0
 * @Description  下拉参数枚举
 * @createTime 2019年05月04日 14:22*
 * log.info()
 */
public enum InitParamEnum {

    BILL_STATUS(1, BaseDictConstants.BILL_STATUS),
    BILL_CLASSIFY_STATUS(2, BaseDictConstants.BILL_CLASSIFY_STATUS),
    ALLOT_STATUS(3, BaseDictConstants.ALLOT_STATUS),
    INIT_1(0, "文员已发送"),
    INIT_2(0, "文员已发送"),
    INIT_3(0, "文员已发送"),
    INIT_4(0, "文员已发送"),
    ;

    private int code ;
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

    public static  String getValueByCode(int code){
        for (InitParamEnum osEnum: InitParamEnum.values()) {
            if(osEnum.getCode()==code){
                return  osEnum.getValue();
            }
        }
        return  "";
    }

}
