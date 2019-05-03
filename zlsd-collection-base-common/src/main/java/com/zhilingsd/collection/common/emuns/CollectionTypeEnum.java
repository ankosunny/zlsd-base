package com.zhilingsd.collection.common.emuns;

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
    COLLECTION_CALL(2,"预测式外呼"),
    COLLECTION_VISIT(4,"外访催收"),
    COLLECTION_JUSTICE(8,"函件催收"),
    COLLECTION_POLICE(16,"公安协催"),
    COLLECTION_MESSAGE(32,"短信催收"),
    COLLECTION_VOICE(64,"语音催收"),
    COLLECTION_ROBOT(128,"机器人催收");
//    COLLECTION_ALL(255,"全部流程"); //全部流程后面有修改再改

    private int type;
    private String desc;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    CollectionTypeEnum(int type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public static String getDescByType(Integer type){
        for(CollectionTypeEnum b : CollectionTypeEnum.values()){
            if(b.type == type){
                return b.desc.trim();
            }
        }
        return null;
    }

    public static CollectionTypeEnum getByType(Integer type){
        for(CollectionTypeEnum b : CollectionTypeEnum.values()){
            if(b.type == type){
                return b;
            }
        }
        return null;
    }
}

