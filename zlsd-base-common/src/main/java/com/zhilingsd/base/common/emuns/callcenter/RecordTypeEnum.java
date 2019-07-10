package com.zhilingsd.base.common.emuns.callcenter;

/**
 * 呼叫类型
 * */
public enum RecordTypeEnum {
    HUMAN("human","人工呼叫"),
    ASR("asr","智能对话"),
    IVR("ivr","智能语音"),
    ;
    /** 状态码 **/
    private String code;
    /** 状态描述 **/
    private String description;


    RecordTypeEnum(String code, String description) {
        this.code = code;
        this.description = description;
    }


    public static RecordTypeEnum find(int code) {
        for (RecordTypeEnum frs : RecordTypeEnum.values()) {
            if (frs.getCode().equals(code)) {
                return frs;
            }
        }
        return null;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
