package com.zhilingsd.base.common.emuns.billmanage;

import com.google.common.collect.Lists;
import com.zhilingsd.base.common.bean.KeyValueBean;

import java.util.List;

/**
 * @author: longhui
 * @description:导入导出管理子类型
 * @create: 2019/6/17 14:45
 */
public enum ImpExpManageSubtypeEnum {

    IMP_BILL("imp_bill","导入新案"),
    UPDATE_BILL("update_bill","更新案件"),
    EXP_BATCH_MARK("exp_batch_mark","导出批量催记"),
    EXP_SINGLE_MARK("exp_single_mark","导出单户催记"),
    EXP_RECORDINGS("exp_recordings","导出录音"),
    EXP_BILL_CENTER("exp_bill_center","导出案件（案件中心）"),
    /////////////还款相关///////////////////
    IMP_REPAY_FLOW("imp_repay_record","导入日账单（还款）"),
    DEL_REPAY_FLOW("del_repay_record","删除日账单（还款）"),
    IMP_REPAY_FLOW_BILL("imp_repay_record_bill","导入流水结算账单（还款）"),
    IMP_REPAY_BILL("imp_repay_bill","导入汇总结算账单（还款）"),
    EXP_REPAY_FLOW("exp_repay_record","导出日账单（还款）"),
    EXP_REPAY_FLOW_LIST("exp_repay_record","导出日账单列表（还款）"),
    EXP_REPAY_FLOW_BILL("exp_repay_record_bill","导出流水结算账单（还款）"),
    EXP_REPAY_FLOW_BILL_LIST("exp_repay_record_bill_list","导出流水结算账单列表（还款）"),
    EXP_REPAY_BILL("exp_repay_bill","导出汇总结算账单（还款）"),
    EXP_REPAY_BILL_LIST("exp_repay_bill_list","导出汇总结算账单列表（还款）"),
    IMP_CONTACTS("imp_contacts","导入联系人")
    ;

    private String code;
    private String value;

    ImpExpManageSubtypeEnum(String code, String value) {
        this.code = code;
        this.value = value;
    }

    public static String getValueByCode(String code) {
        for (ImpExpManageSubtypeEnum osEnum : ImpExpManageSubtypeEnum.values()) {
            if (code.equals(osEnum.getCode())) {
                return osEnum.getValue();
            }
        }
        return "";
    }

    public static List<KeyValueBean> initParam(){
        List<KeyValueBean> initParamList = Lists.newArrayList();
        for (ImpExpManageSubtypeEnum osEnum: ImpExpManageSubtypeEnum.values()){
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
