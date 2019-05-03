package com.zhilingsd.collection.common.emuns;

public enum RequestResultEmuns {

	/** 请求成功 **/
	SUCCESS(true, "成功"),
    /** 请求失败**/
    FAIL(false, "失败");
	
	/** 状态码 **/
    private Boolean code;
    /** 状态描述 **/
    private String description;
    
    /**
     * 私有构造方法
     * 
     * @param code
     *            编码
     * @param description
     *            描述
     **/
    private RequestResultEmuns(Boolean code, String description) {
        this.code = code;
        this.description = description;
    }

    public static RequestResultEmuns find(Boolean code) {
        for (RequestResultEmuns frs : RequestResultEmuns.values()) {
            if (frs.getCode().equals(code)) {
                return frs;
            }
        }
        return null;
    }
    
    
	
	public Boolean getCode() {
		return code;
	}

	public void setCode(Boolean code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
    
    
}
