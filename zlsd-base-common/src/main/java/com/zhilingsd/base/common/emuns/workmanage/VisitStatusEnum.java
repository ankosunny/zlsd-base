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
 * @Description 外访分配状态
 * @createTime 2019年05月04日 21:22*
 * log.info()
 */
public enum VisitStatusEnum {
    //daifenpei-待分配，jinxingzhong-进行中，wancheng-已完成，quxiao-已取消
//0-待分配，1-进行中，2-已完成，3-已取消
    VISIT_DAIFENPEI("daifenpei","待分配"),
    VISIT_JINXINGZHONG("jinxingzhong","进行中"),
    VISIT_WANCHENG("wancheng","已完成"),
    VISIT_QUXIAO("quxiao","已取消"),
    ;

    private String code;
    private String value;

    VisitStatusEnum(String code, String value) {
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
        for (VisitStatusEnum osEnum : VisitStatusEnum.values()) {
            if (osEnum.getCode() .equals(code) ) {
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
