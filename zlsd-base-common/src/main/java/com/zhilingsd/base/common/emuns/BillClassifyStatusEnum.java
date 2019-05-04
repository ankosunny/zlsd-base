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
 * @className com.zhilingsd.enums.java
 * @Description 案件归档状态
 * @createTime 2019年04月19日 14:33*
 * log.info()
 */
public enum BillClassifyStatusEnum {
    CLASSIFY_1(1, "新案件"),
    CLASSIFY_2(2, "已报停催"),
    CLASSIFY_3(3, "已还清，结案"),
    CLASSIFY_4(4, "承诺还款"),
    CLASSIFY_5(5, "查找中"),
    CLASSIFY_6(6, "部份还款，再跟进"),
    CLASSIFY_7(7, "拒接电话或呼入限制"),
    CLASSIFY_8(8, "仿冒，盗用案件"),
    CLASSIFY_9(9, "转诉讼程序"),
    CLASSIFY_10(10, "与银行有争议"),
    CLASSIFY_11(11, "还最低款额"),
    CLASSIFY_12(12, "谈判中"),
    CLASSIFY_13(13, "找到亲友"),
    CLASSIFY_14(14, "投诉预警"),
    CLASSIFY_15(15, "银行撤案"),
    CLASSIFY_16(16, "已诉讼立案"),
    CLASSIFY_17(17, "立案后已缴半费"),
    CLASSIFY_18(18, "立案后已缴全费"),
    CLASSIFY_19(19, "已开庭"),
    CLASSIFY_20(20, "已判决（调解）"),
    CLASSIFY_21(21, "已申请执行"),
    CLASSIFY_22(22, "已执行终结"),
    CLASSIFY_23(23, "诉讼已归档"),
    CLASSIFY_24(24, "诉讼还清结案"),
    CLASSIFY_25(25, "已开生效证明"),
    CLASSIFY_26(26, "留案"),
    CLASSIFY_27(27, "律师要求撤案"),
    CLASSIFY_28(28, "减免跟进"),
    CLASSIFY_29(29, "死亡"),
    CLASSIFY_30(30, "入狱"),
    CLASSIFY_31(31, "失踪"),
    CLASSIFY_32(32, "临时撤案"),
    CLASSIFY_33(33, "分期"),
    ;
    private int code;
    private String value;

    BillClassifyStatusEnum(int code, String value) {
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
        for (BillClassifyStatusEnum osEnum : BillClassifyStatusEnum.values()) {
            if (osEnum.getCode() == code) {
                return osEnum.getValue();
            }
        }
        return "";
    }

    public static List<KeyValueBean> initParam(){
        List<KeyValueBean> initParamList = Lists.newArrayList();
        for (BillClassifyStatusEnum osEnum: BillClassifyStatusEnum.values()){
            KeyValueBean keyValueBean = KeyValueBean.builder().code(osEnum.getCode()+"").name(osEnum.getValue()).build();
            initParamList.add(keyValueBean);
        }
        return initParamList;
    }

}
