package com.zhilingsd.base.common.result;

import com.zhilingsd.base.common.emuns.ReturnCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 单个对象的返回结果
 *
 * @author zhangrong67
 * @version 1.0
 * @since 1.0 2017年12月12日 16:54
 */
@ApiModel("单个结果对象模型")
public class SingleResult<T> extends CommonResult {

    private static final long serialVersionUID = 3847004683544003631L;

    /**
     * 结果对象
     */
    @ApiModelProperty("结果对象")
    private T data;

    public SingleResult() {
    }

    public SingleResult(ReturnCode ReturnCode) {
        super(ReturnCode);
    }

    public SingleResult(int code, String msg) {
        super(code, msg);
    }

    public SingleResult(int code, String msg, T data) {
        super(code, msg);
        setData(data);
    }

    public SingleResult(T data) {
        setData(data);
    }

    public SingleResult(ReturnCode ReturnCode, T data) {
        this(ReturnCode);
        setData(data);
    }

    public static <T> SingleResult<T> of(ReturnCode ReturnCode) {
        return new SingleResult<>(ReturnCode);
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
