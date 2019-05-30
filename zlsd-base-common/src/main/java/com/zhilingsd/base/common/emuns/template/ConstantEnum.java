package com.zhilingsd.base.common.emuns.template;

import com.google.common.collect.Lists;
import com.zhilingsd.base.common.bean.KeyValueBean;

import java.util.List;

/**
 * ^---^---^---^---^---^---^---^
 * --v---v---v---v---v---v---v--
 *
 * @author zou.cp
 * @version 1.0
 * @Description
 * @createTime 2019年05月30日 10:39*
 * log.info()
 */
public enum ConstantEnum {
    //常量表字段
    COLLECTION_TYPE("collection_type", "催收流程"),
    REGISTERED_ADDRESS("registered_address", "户籍地址"),
    CASE_AREA("case_area","案件地区"),
    BUCKET("bucket","账龄"),
    BACTH_TIME("bacth_time","手别"),
    PRODUCT_NAME("product_name","产品名称"),
            ;

    private String code;
    private String value;

    ConstantEnum(String code, String value) {
        this.code = code;
        this.value = value;
    }

    public static String getValueByCode(String code) {
        for (ConstantEnum osEnum : ConstantEnum.values()) {
            if (code.equals(osEnum.getCode())) {
                return osEnum.getValue();
            }
        }
        return "";
    }

    public static List<KeyValueBean> initParam(){
        List<KeyValueBean> initParamList = Lists.newArrayList();
        for (ConstantEnum osEnum: ConstantEnum.values()){
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
