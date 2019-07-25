package com.zhilingsd.base.common.emuns.businessmanage;

import com.google.common.collect.Lists;
import com.zhilingsd.base.common.bean.KeyValueBean;

import java.util.List;

/**
 * ^---^---^---^---^---^---^---^
 * --v---v---v---v---v---v---v--
 *
 * @author zou.cp
 * @version 1.0
 * @Description 备忘处理状态
 * @createTime 2019年07月01日 17:32*
 * log.info()
 */
public enum MemorandumStatusEnum {

    MEMORANDUM_WEIZUO("weizuo", "未做"),
    MEMORANDUM_YIZUO("yizuo", "已做"),
    ;
    private String code;
    private String value;

    MemorandumStatusEnum(String code, String value) {
        this.code = code;
        this.value = value;
    }

    public static String getValueByCode(String code) {
        for (MemorandumStatusEnum osEnum : MemorandumStatusEnum.values()) {
            if (osEnum.getCode().equals(code)) {
                return osEnum.value;
            }
        }
        return "";
    }

    public static MemorandumStatusEnum getByCode(String code) {
        for (MemorandumStatusEnum osEnum : MemorandumStatusEnum.values()) {
            if (osEnum.getCode().equals(code)) {
                return osEnum;
            }
        }
        return null;
    }

    public static List<KeyValueBean> initParam() {
        List<KeyValueBean> allotStatusList = Lists.newArrayList();
        for (MemorandumStatusEnum osEnum : MemorandumStatusEnum.values()) {
            KeyValueBean keyValueBean = KeyValueBean.builder().code(osEnum.getCode() + "").name(osEnum.getValue()).build();
            allotStatusList.add(keyValueBean);
        }
        return allotStatusList;
    }

    public String getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }

}
