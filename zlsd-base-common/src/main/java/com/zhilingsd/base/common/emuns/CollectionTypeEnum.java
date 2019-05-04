package com.zhilingsd.base.common.emuns;

import com.google.common.collect.Lists;
import com.zhilingsd.base.common.bean.KeyValueBean;

import java.util.List;

/**
 * **className CollectionTypeEnum
 * @author: zou.cp
 * description: This is a  催收流程类型
 * ----------------------------------------
 * =======================================>
 */
public enum CollectionTypeEnum {

    /**
     *  低位第一位 人工催收      1               1
     *  低位第二位 预测式外呼催收 10              2
     *  低位第三位 外访催收      100             4
     *  低位第四位 函件催收      1000            8
     *  低位第五位 公安催收      10000           16
     *  低位第六位 短信催收      100000          32
     *  低位第七位 语音催收      1000000         64
     *  低位第八位 机器人催收    10000000        128
     *  后面有其它催收流程添加    低位补0          2^n
     *  包含全部流程           11111111         255
     *
     *
     **/
    COLLECTION_MANUAL(1,"人工催收"),
    COLLECTION_VISIT(2,"外访催收"),
    COLLECTION_JUSTICE(4,"函件催收"),
    COLLECTION_POLICE(8,"报案催收"),
//    COLLECTION_CALL(16,"预测式外呼"),
//    COLLECTION_MESSAGE(32,"短信催收"),
//    COLLECTION_VOICE(64,"语音催收"),
//    COLLECTION_ROBOT(128,"机器人催收");
//    COLLECTION_ALL(255,"全部流程"); //全部流程后面有修改再改
;
    private int code;
    private String value;

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

    CollectionTypeEnum(int code, String value) {
        this.code = code;
        this.value = value;
    }



    public static String getDescByType(Integer code){
        for(CollectionTypeEnum b : CollectionTypeEnum.values()){
            if(b.code == code){
                return b.value.trim();
            }
        }
        return null;
    }

    public static CollectionTypeEnum getByType(Integer code){
        for(CollectionTypeEnum b : CollectionTypeEnum.values()){
            if(b.code == code){
                return b;
            }
        }
        return null;
    }

    public static List<KeyValueBean> initParam(){
        List<KeyValueBean> initParamList = Lists.newArrayList();
        for (CollectionTypeEnum osEnum: CollectionTypeEnum.values()){
            KeyValueBean keyValueBean = KeyValueBean.builder().code(osEnum.getCode()+"").name(osEnum.getValue()).build();
            initParamList.add(keyValueBean);
        }
        return initParamList;
    }
}

