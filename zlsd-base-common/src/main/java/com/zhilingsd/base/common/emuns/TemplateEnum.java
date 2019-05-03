package com.zhilingsd.base.common.emuns;

import java.util.ArrayList;
import java.util.List;

/**
 * Description: 模板枚举
 *
 * @Author zengkai
 * @Date 2019/1/16 14:47
 */
public class TemplateEnum {

    /**
     * 模板路径： 服务器地址+IMPORT_TEMPLATE_UPLOAD_URL+基础模板类型+用户公司标识+模板名称+后缀
     * 如：mujin.com/template/import/案件导入模板/delv/案件导入模板第二版.xlsx
     */

    /**
     * 导入模板上传地址
     */
    private final static String IMPORT_TEMPLATE_UPLOAD_URL = "/template/import/";

    /**
     * 导出模板上传地址
     */
    private final static String EXPORT_TEMPLATE_UPLOAD_URL = "/template/export/";


    /**
     * 描述: 基础模板类型
     * @Author zengkai
     * @Date 2019/1/16 15:18
     */
    public enum TemplateBaseTypeEnum{

        IMPORT_TEMPLATE_CASE(1, "案件导入模板"),
        IMPORT_TEMPLATE_WORK_ORDER(2, "工单导入模板"),
        IMPORT_TEMPLATE_CONTACT(3, "联系人导入模板"),
        IMPORT_TEMPLATE_WITHDRAW(4, "撤案导入模板"),
        IMPORT_TEMPLATE_STOP_COLLECTION(5, "停催导入模板"),
        IMPORT_TEMPLATE_REPAYMENT(6, "还款导入模板"),
        IMPORT_TEMPLATE_COMPLAINT(7, "投诉导入模板"),
        IMPORT_TEMPLATE_BLACKLIST_NUMBER(8, "黑名单号码库导入模板"),
        IMPORT_TEMPLATE_CASE_UPDATE(9, "案件更新导入模板"),
        EXPORT_TEMPLATE_COLLECTION_RECORD(10, "催记导出模板"),
        EXPORT_TEMPLATE_FOLLOW_STREAM(11, "跟进流水导出模板"),
        EXPORT_TEMPLATE_APPLY_INFO(12, "审批信息导出模板"),
        EXPORT_TEMPLATE_VISIT_REPORT(13, "外访报告导出模板"),
        EXPORT_TEMPLATE_REPAYMENT_LIST(14, "还款列表导出模板"),
        EXPORT_TEMPLATE_COMPLAINT_WARN(15, "投诉预警导出模板"),
        EXPORT_TEMPLATE_REPAYMENT_STREAM(16, "还款流水导出模板"),
        EXPORT_TEMPLATE_TRANSFER_CASE_RECODE(17, "调案记录(流水、案件)导出模板"),
        EXPORT_TEMPLATE_WITHDRAW_CASE_URECODE(18, "撤案记录导出模板");

        private int code ;
        private String value;

        TemplateBaseTypeEnum(int code, String value) {
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
            for (TemplateBaseTypeEnum osEnum: TemplateBaseTypeEnum.values()) {
                if(osEnum.getCode()==code){
                    return  osEnum.getValue();
                }
            }
            return  "";
        }
    }

    /**
     * 描述: 获得完整的上传路径
     * @Author zengkai
     * @Date 2019/1/16 15:25
     * @param importOrExport 类型 0：导入；1：导出
     * @param dataFilePath 服务器地址
     * @param templateTypeCode 基础模板code
     * @param templateName 基础名称
     * @return java.lang.String
     */
    public static String getCompleteUrl(int importOrExport, String dataFilePath,int templateTypeCode, String templateName){
        String url = null;
        if (0==importOrExport){
            url = dataFilePath+IMPORT_TEMPLATE_UPLOAD_URL+ TemplateBaseTypeEnum.getValueByCode(templateTypeCode)+"/"+templateName;
        }else{
            url = dataFilePath+EXPORT_TEMPLATE_UPLOAD_URL+ TemplateBaseTypeEnum.getValueByCode(templateTypeCode)+"/"+templateName;
        }
        return url.replaceAll("//","/");
    }

    /**
     * 描述: 获得非标准模板上传完整路径
     * @Author zengkai
     * @Date 2019/1/21 19:45
     * @param templateNonStandardType 非标准模板类型 1：函件；2：减免凭证 3.坐席分配
     * @param dataFilePath
     * @param orgSign
     * @param templateName
     * @return java.lang.String
     */
    public static String getNonTemplateCompleteUrl(String templateNonStandardType, String dataFilePath, String templateName){
        String url = null;
        if ("1".equals(templateNonStandardType)){
            url = dataFilePath+"/template/letter/"+templateName;
        }else if ("2".equals(templateNonStandardType)){
            url = dataFilePath+"/template/certificate/"+templateName;
        }
        return url.replaceAll("//","/");
    }

    /**
     * 描述: 获取需要填写组合编号的模板类型编码列表
     * @Author zengkai
     * @Date 2019/2/20 16:50
     * @param
     * @return java.util.List
     */
    public static List getHaveCombinationTemplateType(){
        List<Integer> list = new ArrayList<>(15);
        list.add(TemplateBaseTypeEnum.IMPORT_TEMPLATE_CASE.getCode());
        list.add(TemplateBaseTypeEnum.IMPORT_TEMPLATE_WORK_ORDER.getCode());
        list.add(TemplateBaseTypeEnum.IMPORT_TEMPLATE_CONTACT.getCode());
        list.add(TemplateBaseTypeEnum.IMPORT_TEMPLATE_WITHDRAW.getCode());
        list.add(TemplateBaseTypeEnum.IMPORT_TEMPLATE_STOP_COLLECTION.getCode());
        list.add(TemplateBaseTypeEnum.IMPORT_TEMPLATE_REPAYMENT.getCode());
        list.add(TemplateBaseTypeEnum.IMPORT_TEMPLATE_COMPLAINT.getCode());
        list.add(TemplateBaseTypeEnum.IMPORT_TEMPLATE_CASE_UPDATE.getCode());
        return list;
    }

}
