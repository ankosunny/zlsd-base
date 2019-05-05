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
 * @Description 案件分组状态
 * @createTime 2019年04月23日 17:01*
 * log.info()
 */
public enum GroupStatusEnum {

    SEND(0, "文员已发送"),
    WAIT_SEND(1, "组别管理待发布"),
    FINISH(2, "已完成"),
    REVOKED(3, "已撤销")
    ;

    private int code ;
    private String value;

    GroupStatusEnum(int code, String value) {
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
        for (GroupStatusEnum osEnum: GroupStatusEnum.values()) {
            if(osEnum.getCode()==code){
                return  osEnum.getValue();
            }
        }
        return  "";
    }
    public static List<KeyValueBean> initParam() {
        List<KeyValueBean> allotStatusList = Lists.newArrayList();
        for (GroupStatusEnum osEnum : GroupStatusEnum.values()) {
            KeyValueBean keyValueBean = KeyValueBean.builder().code(osEnum.getCode() + "").name(osEnum.getValue()).build();
            allotStatusList.add(keyValueBean);
        }
        return allotStatusList;
    }

}
