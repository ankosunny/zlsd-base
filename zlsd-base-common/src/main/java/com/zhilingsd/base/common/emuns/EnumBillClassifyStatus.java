package com.zhilingsd.base.common.emuns;

import com.google.common.collect.Lists;
import com.zhilingsd.base.common.bean.KeyValueBean;
import lombok.extern.slf4j.Slf4j;

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
@Slf4j
public enum EnumBillClassifyStatus {
    XIN_AN_JIAN("xinanjian", "新案件"),
    YI_BAO_TING_CUI("yibaotingcui", "已报停催"),
    YI_JIE_AN("yijiean", "已还清，结案"),
    CHENG_NUO_HUAN_KUAN("chengnuohuankuan", "承诺还款"),
    CHA_ZHAO_ZHONG("chazhaozhong", "查找中"),
    BU_FEN_HUAN_KUAN("bufenhuankuan", "部份还款，再跟进"),
    JU_JIE_XIAN_ZHI("jujiexianzhi", "拒接电话或呼入限制"),
    DAO_YONG_AN_JIAN("daoyonganjian", "仿冒，盗用案件"),
    ZHUAN_SU_SONG("zhuansusong", "转诉讼程序"),
    YIN_HANG_ZHENG_YI("yinhangzhengYi", "与银行有争议"),
    HUAN_ZUI_DI_KUAN("huanzuidikuan", "还最低款额"),
    TAN_PAN_ZHONG("tanpanzhong", "谈判中"),
    ZHAO_DAO_QIN_YOU("zhaodaoqinyou", "找到亲友"),
    TOU_SU_YU_JING("tousuyujing", "投诉预警"),
    YIN_HANG_CHE_AN("yinhangchean", "银行撤案"),
    YI_LI_AN("yilian", "已诉讼立案"),
    YI_JIAO_BAN_FEI("yijiaobanfei", "立案后已缴半费"),
    YI_JIAO_QUAN_FEO("yijiaoquanfei", "立案后已缴全费"),
    YI_KAI_TING("yikaiting", "已开庭"),
    YI_PAN_JUE("yipanjue", "已判决（调解）"),
    SHEN_QING_ZHI_XING("shenqingzhixing", "已申请执行"),
    ZHI_XING_ZHONG_JIE("zhixingzhongjie", "已执行终结"),
    YI_GUI_DANG("yiguidang", "诉讼已归档"),
    HUAN_QING_JIE_AN("huanqingjiean", "诉讼还清结案"),
    SHENG_XIAO_ZHENG_MING("shengxiaozhengming", "已开生效证明"),
    LIU_AN("liuan", "留案"),
    YAO_QIU_CHE_AN("yaoqiuchean", "律师要求撤案"),
    JIAN_MIAN_GEN_JIN("jianmiangenjin", "减免跟进"),
    SI_WANG("siwang", "死亡"),
    RU_YU("ruyu", "入狱"),
    SHI_ZONG("shizong", "失踪"),
    LIN_SHI_CHE_AN("linshichean", "临时撤案"),
    FEN_QI("fenqi", "分期"),
    ;
    private String code;
    private String value;

    EnumBillClassifyStatus(String code, String value) {
        this.code = code;
        this.value = value;
    }

    public String getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }


    public static String getValueByCode(String code) {
        for (EnumBillClassifyStatus osEnum : EnumBillClassifyStatus.values()) {
            if (osEnum.getCode().equals(code)) {
                return osEnum.value;
            }
        }
        return null;
    }

    public static List<KeyValueBean> initParam() {
        List<KeyValueBean> initParamList = Lists.newArrayList();
        for (EnumBillClassifyStatus osEnum : EnumBillClassifyStatus.values()) {
            KeyValueBean keyValueBean = KeyValueBean.builder().code(osEnum.getCode() + "").name(osEnum.getValue()).build();
            initParamList.add(keyValueBean);
        }
        return initParamList;
    }

}
