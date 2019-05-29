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
 * @Description 模板方向
 * @createTime 2019年05月04日 15:04*
 * log.info()
 */
public enum TemplateDirectionEnum {

    //    外访/减免/撤案/留案/报案
    IMPORT(1, "导入"),
    EXPORT(2, "导出");

    private int code;
    private String value;

    TemplateDirectionEnum(int code, String value) {
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
        for (TemplateDirectionEnum osEnum : TemplateDirectionEnum.values()) {
            if (osEnum.getCode() == code) {
                return osEnum.getValue();
            }
        }
        return "";
    }

    public static List<KeyValueBean> initParam(){
        List<KeyValueBean> initParamList = Lists.newArrayList();
        for (TemplateDirectionEnum osEnum: TemplateDirectionEnum.values()){
            KeyValueBean keyValueBean = KeyValueBean.builder().code(osEnum.getCode()+"").name(osEnum.getValue()).build();
            initParamList.add(keyValueBean);
        }
        return initParamList;
    }

}
