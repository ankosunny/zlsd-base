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
public enum BillClassifyStatusEnum {
    XIN_AN_JIAN("xinAnJian", "新案件"),
    YI_BAO_TING_CUI("yiBaoTingCui", "已报停催"),
    YI_JIE_AN("yiJieAn", "已还清，结案"),
    CHENG_NUO_HUAN_KUAN("chengNuoHuanKuan", "承诺还款"),
    CHA_ZHAO_ZHONG("chaZhaoZhong", "查找中"),
    BU_FEN_HUAN_KUAN("buFenHuanKuan", "部份还款，再跟进"),
    JU_JIE_XIAN_ZHI("juJieXianZhi", "拒接电话或呼入限制"),
    DAO_YONG_AN_JIAN("daoYongAnJian", "仿冒，盗用案件"),
    ZHUAN_SU_SONG("zhuanSuSong", "转诉讼程序"),
    YIN_HANG_ZHENG_YI("yinHangZhengYi", "与银行有争议"),
    HUAN_ZUI_DI_KUAN("huanZuiDiKuan", "还最低款额"),
    TAN_PAN_ZHONG("tanPanZhong", "谈判中"),
    ZHAO_DAO_QIN_YOU("zhaoDaoQinYou", "找到亲友"),
    TOU_SU_YU_JING("touSuYuJing", "投诉预警"),
    YIN_HANG_CHE_AN("yinHangCheaN", "银行撤案"),
    YI_LI_AN("yiLiAn", "已诉讼立案"),
    YI_JIAO_BAN_FEI("yiJiaoBanFei", "立案后已缴半费"),
    YI_JIAO_QUAN_FEO("yiJiaoQuanFei", "立案后已缴全费"),
    YI_KAI_TING("yiKaiTing", "已开庭"),
    YI_PAN_JUE("yiPanJue", "已判决（调解）"),
    SHEN_QING_ZHI_XING("shenQingZhiXing", "已申请执行"),
    ZHI_XING_ZHONG_JIE("zhiXingZhongJie", "已执行终结"),
    YI_GUI_DANG("yiGuiDang", "诉讼已归档"),
    HUAN_QING_JIE_AN("huanQingJieAn", "诉讼还清结案"),
    SHENG_XIAO_ZHENG_MING("shengXiaoZhengMing", "已开生效证明"),
    LIU_AN("liuAn", "留案"),
    YAO_QIU_CHE_AN("yaoQiuCheAn", "律师要求撤案"),
    JIAN_MIAN_GEN_JIN("jianMianGenJin", "减免跟进"),
    SI_WANG("siWang", "死亡"),
    RU_YU("ruYu", "入狱"),
    SHI_ZONG("shiZong", "失踪"),
    LIN_SHI_CHE_AN("linShiCheAn", "临时撤案"),
    FEN_QI("fenQi", "分期"),
    ;
    private String code;
    private String value;

    BillClassifyStatusEnum(String code, String value) {
        this.code = code;
        this.value = value;
    }

    public String getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }


    public static BillClassifyStatusEnum getValueByCode(int code) {
        for (BillClassifyStatusEnum osEnum : BillClassifyStatusEnum.values()) {
            if (osEnum.getCode().equals(code)) {
                return osEnum;
            }
        }
        return null;
    }

    public static List<KeyValueBean> initParam() {
        List<KeyValueBean> initParamList = Lists.newArrayList();
        for (BillClassifyStatusEnum osEnum : BillClassifyStatusEnum.values()) {
            KeyValueBean keyValueBean = KeyValueBean.builder().code(osEnum.getCode() + "").name(osEnum.getValue()).build();
            initParamList.add(keyValueBean);
        }
        return initParamList;
    }

}
