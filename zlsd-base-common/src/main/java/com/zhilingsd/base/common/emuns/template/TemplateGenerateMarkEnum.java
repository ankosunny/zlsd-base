package com.zhilingsd.base.common.emuns.template;

import com.google.common.collect.Lists;
import com.zhilingsd.base.common.bean.KeyValueBean;

import java.util.List;

/**
 * ^---^---^---^---^---^---^---^
 * --v---v---v---v---v---v---v--
 *
 * @author liuhang
 * @version 1.0
 * @Description 模板地区和
 * @createTime 2019年05月04日 15:04*
 * log.info()
 */
public enum TemplateGenerateMarkEnum {

    //    外访/减免/撤案/留案/报案
    NONE("00", "不生成案件编号和从excel中读取地址"),
    GENERATE_BILL_CODE("10", "生成案件编号"),
    GENERATE_ADDR("01", "生成地址"),
    ALL_GENERATE("11", "生成案件编号和生成地址");

    private String code;
    private String value;

    TemplateGenerateMarkEnum(String code, String value) {
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
        for (TemplateGenerateMarkEnum osEnum : TemplateGenerateMarkEnum.values()) {
            if (osEnum.getCode() .equals(code)) {
                return osEnum.getValue();
            }
        }
        return "";
    }

    public static List<KeyValueBean> initParam(){
        List<KeyValueBean> initParamList = Lists.newArrayList();
        for (TemplateGenerateMarkEnum osEnum: TemplateGenerateMarkEnum.values()){
            KeyValueBean keyValueBean = KeyValueBean.builder().code(osEnum.getCode()+"").name(osEnum.getValue()).build();
            initParamList.add(keyValueBean);
        }
        return initParamList;
    }

}
