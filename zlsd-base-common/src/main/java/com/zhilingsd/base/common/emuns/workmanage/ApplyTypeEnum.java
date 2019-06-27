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
 * @Description 申请类型
 * @createTime 2019年05月04日 15:04*
 * log.info()
 */
public enum ApplyTypeEnum{

    //waifang：外访，jianmian：减免，chean：撤案，liuan：留案,baoan：报案
    //    外访/减免/撤案/留案/报案
    APPLY_TYPE_WAIFANG("waifang", "外访"),
    APPLY_TYPE_JIANMIAN("jianmian", "减免"),
    APPLY_TYPE_CHEAN("chean", "撤案"),
    APPLY_TYPE_LIUAN("liuan", "留案"),
    APPLY_TYPE_BAOAN("baoan", "报案"),
    ;

    private String code;
    private String value;

    ApplyTypeEnum(String code, String value) {
        this.code = code;
        this.value = value;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public static String getValueByCode(String code) {
        for (ApplyTypeEnum osEnum : ApplyTypeEnum.values()) {
            if (osEnum.getCode() .equals(code)) {
                return osEnum.getValue();
            }
        }
        return "";
    }

    public static List<KeyValueBean> initParam(){
        List<KeyValueBean> initParamList = Lists.newArrayList();
        for (ApplyTypeEnum osEnum: ApplyTypeEnum.values()){
            KeyValueBean keyValueBean = KeyValueBean.builder().code(osEnum.getCode()+"").name(osEnum.getValue()).build();
            initParamList.add(keyValueBean);
        }
        return initParamList;
    }

}
