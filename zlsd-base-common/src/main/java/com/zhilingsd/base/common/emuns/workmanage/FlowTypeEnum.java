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

    //跟进类型：waifang:外访 jianmian:减免 chean:撤案 liuan:留案 baoan:报案 hanjian:函件 cuiji:记催记
    //    外访/减免/撤案/留案/报案/函件/记催记
    FLOW_TYPE_WAIFANG("waifang", "外访"),
    FLOW_TYPE_JIANMIAN("jianmian", "减免"),
    FLOW_TYPE_CHEAN("chean", "撤案"),
    FLOW_TYPE_LIUAN("liuan", "留案"),
    FLOW_TYPE_BAOAN("baoan", "报案"),
    FLOW_TYPE_HANJIAN("hanjian", "函件"),
    FLOW_TYPE_CUIJI("cuiji", "记催记"),
    ;

    private String code;
    private String value;

    FlowTypeEnum(String code, String value) {
        this.code = code;
        this.value = value;
    }

    public static String getValueByCode(String code) {
        for (FlowTypeEnum osEnum : FlowTypeEnum.values()) {
            if (osEnum.getCode().equals( code)) {
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

}
