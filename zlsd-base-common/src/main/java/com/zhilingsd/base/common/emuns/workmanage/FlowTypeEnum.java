package com.zhilingsd.base.common.emuns.workmanage;

import com.google.common.collect.Lists;
import com.zhilingsd.base.common.bean.KeyValueBean;

import java.util.List;

/**
 * ^---^---^---^---^---^---^---^
 * --v---v---v---v---v---v---v--
 *
 * @author zou.cp
 * @version 1.0
 * @Description 案件跟进流水类型
 * @createTime 2019年05月04日 15:04*
 * log.info()
 */
public enum FlowTypeEnum {

    //    外访/减免/撤案/留案/报案/函件/记催记
    APPLY_TYPE_1(1, "外访"),
    APPLY_TYPE_2(2, "减免"),
    APPLY_TYPE_3(3, "撤案"),
    APPLY_TYPE_4(4, "留案"),
    APPLY_TYPE_5(5, "报案"),
    APPLY_TYPE_6(6, "函件"),
    APPLY_TYPE_7(7, "记催记"),
    ;

    private int code;
    private String value;

    FlowTypeEnum(int code, String value) {
        this.code = code;
        this.value = value;
    }

    public static String getValueByCode(int code) {
        for (FlowTypeEnum osEnum : FlowTypeEnum.values()) {
            if (osEnum.getCode() == code) {
                return osEnum.getValue();
            }
        }
        return "";
    }

    public static List<KeyValueBean> initParam(){
        List<KeyValueBean> initParamList = Lists.newArrayList();
        for (FlowTypeEnum osEnum: FlowTypeEnum.values()){
            KeyValueBean keyValueBean = KeyValueBean.builder().code(osEnum.getCode()+"").name(osEnum.getValue()).build();
            initParamList.add(keyValueBean);
        }
        return initParamList;
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

}
