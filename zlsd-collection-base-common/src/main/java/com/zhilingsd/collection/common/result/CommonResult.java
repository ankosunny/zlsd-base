package com.zhilingsd.collection.common.result;


import com.zhilingsd.collection.common.emuns.BaseResultCodeEnum;
import com.zhilingsd.collection.common.result.base.ToString;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 公用结果对象
 *
 * @author zhangrong67
 * @version 1.0
 * @since 1.0 2017年12月12日 16:57
 */
@ApiModel("公共结果模式")
public class CommonResult extends ToString {

    private static final long serialVersionUID = -5922291019518100856L;
    /**
     * 返回code
     */
    @ApiModelProperty("返回code")
    public String code;

    /**
     * 结果描述
     */
    @ApiModelProperty("结果描述")
    public String msg;

    /**
     * 相应时间
     */
    @ApiModelProperty("响应时间")
    public String sysTime;

    public String getSysTime() {
		return sysTime;
	}

	public void setSysTime(String sysTime) {
		this.sysTime = sysTime;
	}

	public CommonResult() {
    }

    public CommonResult(BaseResultCodeEnum baseResultCodeEnum) {
        setCode(baseResultCodeEnum.getCode());
        setMsg(baseResultCodeEnum.getMsg());
        this.sysTime = String.valueOf(System.currentTimeMillis());
    }


    public CommonResult( String code, String msg) {
        this.code = code;
        this.msg = msg;
        this.sysTime = String.valueOf(System.currentTimeMillis());
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
