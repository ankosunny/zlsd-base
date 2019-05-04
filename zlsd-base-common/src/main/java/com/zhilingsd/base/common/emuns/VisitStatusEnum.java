package com.zhilingsd.base.common.emuns;

import com.google.common.collect.Lists;
import com.zhilingsd.base.common.bean.KeyValueBean;

import java.util.List;

/**
 * ^---^---^---^---^---^---^---^
 * --v---v---v---v---v---v---v--
 *
 * @author zou.cp
 * @version 1.0
 * @Description 外访分配状态
 * @createTime 2019年05月04日 21:22*
 * log.info()
 */
public enum VisitStatusEnum {
//0-待分配，1-进行中，2-已完成，3-已取消
    VISIT_STATUS_0(0,"待分配"),
    VISIT_STATUS_1(1,"进行中"),
    VISIT_STATUS_2(2,"已完成"),
    VISIT_STATUS_3(3,"已取消"),
    ;

    private int code;
    private String value;

    VisitStatusEnum(int code, String value) {
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
        for (VisitStatusEnum osEnum : VisitStatusEnum.values()) {
            if (osEnum.getCode() == code) {
                return osEnum.getValue();
            }
        }
        return "";
    }

    public static List<KeyValueBean> initParam(){
        List<KeyValueBean> initParamList = Lists.newArrayList();
        for (VisitStatusEnum osEnum: VisitStatusEnum.values()){
            KeyValueBean keyValueBean = KeyValueBean.builder().code(osEnum.getCode()+"").name(osEnum.getValue()).build();
            initParamList.add(keyValueBean);
        }
        return initParamList;
    }

}
