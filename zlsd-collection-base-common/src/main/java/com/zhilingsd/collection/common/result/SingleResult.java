package com.zhilingsd.collection.common.result;

import com.zhilingsd.collection.common.emuns.BaseResultCodeEnum;
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

    public SingleResult(BaseResultCodeEnum baseResultCodeEnum) {
        super(baseResultCodeEnum);
    }

    public SingleResult(String code, String msg) {
        super( code, msg);
    }

    public SingleResult(String code, String msg, T data) {
        super(code, msg);
        setData(data);
    }

    public SingleResult(T data) {
        setData(data);
    }

    public SingleResult(BaseResultCodeEnum baseResultCodeEnum, T data) {
        this(baseResultCodeEnum);
        setData(data);
    }

    public static <T> SingleResult<T> of(BaseResultCodeEnum baseResultCodeEnum) {
        return new SingleResult<>(baseResultCodeEnum);
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
