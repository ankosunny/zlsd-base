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
 * @Description 自定义模板类型
 * @createTime 2019年05月04日 15:04*
 * log.info()
 */
public enum TemplateCustomTypeEnum {

    IMPORT_TEMPLATE_CASE(1, "案件导入模板"),
    IMPORT_TEMPLATE_CASE_UPDATE(2, "案件更新模板"),
    IMPORT_TEMPLATE_WORK_ORDER(3, "工单导入模板"),
    IMPORT_TEMPLATE_CONTACT(4, "联系人导入模板"),
    IMPORT_TEMPLATE_WITHDRAW(5, "撤案导入模板"),
    IMPORT_TEMPLATE_REPAYMENT(6, "还款流水模板"),
    IMPORT_TEMPLATE_WATER_BILL(7, "还款流水账单模板"),
    IMPORT_TEMPLATE_BATCH_CLEAN_SUM(8, "批次结算汇总账单模板"),
    IMPORT_TEMPLATE_COMPLAINT(9, "投诉导入模板"),
    IMPORT_TEMPLATE_STOP_COLLECTION(10, "投诉导入模板"),
    EXPORT_TEMPLATE_COLLECTION_RECORD(11, "原始催记模板"),
    EXPORT_TEMPLATE_STOP_PHONE(12, "停催号码模板"),
    EXPORT_TEMPLATE_BATCH_COLLECTION_MARK(13, "批量催记"),
    EXPORT_TEMPLATE_SINGLE_COLLECTION_MARK(14, "单户催记模板"),
    EXPORT_TEMPLATE_VISIT_REPORT(15, "外访报告导出模板");

    private int code;
    private String value;

    TemplateCustomTypeEnum(int code, String value) {
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
        for (TemplateCustomTypeEnum osEnum : TemplateCustomTypeEnum.values()) {
            if (osEnum.getCode() ==(code)) {
                return osEnum.getValue();
            }
        }
        return "";
    }

    public static List<KeyValueBean> initParam(){
        List<KeyValueBean> initParamList = Lists.newArrayList();
        for (TemplateCustomTypeEnum osEnum: TemplateCustomTypeEnum.values()){
            KeyValueBean keyValueBean = KeyValueBean.builder().code(osEnum.getCode()+"").name(osEnum.getValue()).build();
            initParamList.add(keyValueBean);
        }
        return initParamList;
    }

}
