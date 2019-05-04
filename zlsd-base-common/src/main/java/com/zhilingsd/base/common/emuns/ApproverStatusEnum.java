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
 * @Description 审批状态
 * @createTime 2019年05月04日 14:36*
 * log.info()
 */
public enum  ApproverStatusEnum {

//    0：待审批；1：一级主管通过；2：一级主管拒绝；3：二级文员通过；4：二级文员拒绝'
    APPROVER_STATUS_0(0, "待审批"),
    APPROVER_STATUS_1(1, "一级主管通过"),
    APPROVER_STATUS_2(2, "一级主管驳回"),
    APPROVER_STATUS_3(3, "通过"),
    APPROVER_STATUS_4(4, "驳回"),
            ;

    private int code;
    private String value;

    ApproverStatusEnum(int code, String value) {
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
        for (ApproverStatusEnum osEnum : ApproverStatusEnum.values()) {
            if (osEnum.getCode() == code) {
                return osEnum.getValue();
            }
        }
        return "";
    }

    public static List<KeyValueBean> initParam(){
        List<KeyValueBean> initParamList = Lists.newArrayList();
        for (ApproverStatusEnum osEnum: ApproverStatusEnum.values()){
            KeyValueBean keyValueBean = KeyValueBean.builder().code(osEnum.getCode()+"").name(osEnum.getValue()).build();
            initParamList.add(keyValueBean);
        }
        return initParamList;
    }

}
