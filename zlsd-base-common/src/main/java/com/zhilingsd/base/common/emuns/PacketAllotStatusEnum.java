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
 * @Description  批次分配状态
 * @createTime 2019年04月19日 14:33*
 * log.info()
 */
public enum PacketAllotStatusEnum {

    PACKET_NOT_ALLOT(0, "待分配"),
    PACKET_ALLOTING(1, "部分分配"),
    PACKET_ALLOT(2, "已分配");

    private int code ;
    private String value;

    PacketAllotStatusEnum(int code, String value) {
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
        for (PacketAllotStatusEnum osEnum: PacketAllotStatusEnum.values()) {
            if(osEnum.getCode()==code){
                return  osEnum.getValue();
            }
        }
        return  "";
    }

    public static List<KeyValueBean> initParam(){
        List<KeyValueBean> initParamList = Lists.newArrayList();
        for (PacketAllotStatusEnum osEnum: PacketAllotStatusEnum.values()){
            KeyValueBean keyValueBean = KeyValueBean.builder().code(osEnum.getCode()+"").name(osEnum.getValue()).build();
            initParamList.add(keyValueBean);
        }
        return initParamList;
    }

}
