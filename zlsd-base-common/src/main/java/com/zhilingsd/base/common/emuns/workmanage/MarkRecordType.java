package com.zhilingsd.base.common.emuns.workmanage;

import com.google.common.collect.Lists;
import com.zhilingsd.base.common.bean.KeyValueBean;

import java.util.List;

/**
 * ^---^---^---^---^---^---^---^
 * --v---v---v---v---v---v---v--
 *
 * @author wangjq
 * @version 1.0
 * @Description 记催记类型
 * @createTime 2019年06月02日 11:50*
 * log.info()
 */
public enum MarkRecordType {

    RECORD_TYPE_1(1,"电催"),
    RECORD_TYPE_2(2,"工单"),
    RECORD_TYPE_3(3,"外访"),
    RECORD_TYPE_4(4,"标注"),
    RECORD_TYPE_5(5,"原始催记"),
    RECORD_TYPE_6(6,"信函"),
    ;

    private int code;
    private String value;

    MarkRecordType(int code, String value) {
        this.code = code;
        this.value = value;
    }

    public static String getValueByCode(int code) {
        for (MarkRecordType osEnum : MarkRecordType.values()) {
            if (osEnum.getCode() == code) {
                return osEnum.getValue();
            }
        }
        return "";
    }

    public static List<KeyValueBean> initParam(){
        List<KeyValueBean> initParamList = Lists.newArrayList();
        for (MarkRecordType recordType: MarkRecordType.values()){
            KeyValueBean keyValueBean = KeyValueBean.builder().code(recordType.getCode()+"").name(recordType.getValue()).build();
            initParamList.add(keyValueBean);
        }
        return initParamList;
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





}
