package com.zhilingsd.base.common.emuns.workmanage;

import com.google.common.collect.Lists;
import com.zhilingsd.base.common.bean.KeyValueBean;

import java.util.ArrayList;
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

//审批状态 daishenpi：待审批；yitongguo：一级主管通过；yijujue：一级主管拒绝；ertongguo：二级主管通过；erjujue：二级主管拒绝 ;shixiao：失效
    APPROVER_DAISHENPI("daishenpi", "待审批"),
    APPROVER_YITONGGUO("yitongguo", "一级审批通过"),
    APPROVER_YIJUJUE("yijujue", "一级审批驳回"),
    APPROVER_ERTONGGUO("ertongguo", "通过"),
    APPROVER_ERJUJUE("erjujue", "驳回"),
    APPROVER_SHIXIAO("shixiao", "失效"),
            ;

    private String code;
    private String value;

    public static List<String> ONE_APPROVE = new ArrayList<>(2);
    public static List<String> TWO_APPROVE = new ArrayList<>(2);


    static {
        ONE_APPROVE.add(APPROVER_YITONGGUO.getCode());
        ONE_APPROVE.add(APPROVER_YIJUJUE.getCode());
        TWO_APPROVE.add(APPROVER_ERTONGGUO.getCode());
        TWO_APPROVE.add(APPROVER_ERJUJUE.getCode());
    }

    ApproverStatusEnum(String code, String value) {
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
        for (ApproverStatusEnum osEnum : ApproverStatusEnum.values()) {
            if (osEnum.getCode() .equals(code) ) {
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
