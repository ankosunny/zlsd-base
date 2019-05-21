package com.zhilingsd.base.common.result;

import com.zhilingsd.base.common.emuns.ReturnCode;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.xpath.operations.Bool;

import java.io.Serializable;

/**
 * Description: 业务返回参数封装
 *
 * @Author zengkai
 * @Date 2019/4/10 11:58
 */
@Data
@NoArgsConstructor
public class CollectionResult<T> implements Serializable {

    private static final long serialVersionUID = 8254311356031310504L;

    @ApiModelProperty(value = "返回结果")
    private T data;

    @ApiModelProperty(value = "状态值")
    private int code;

    @ApiModelProperty(value = "描述信息")
    private String msg;


    /**
     * 构造函数
     *
     * @param code    错误或者成功代码
     * @param message 错误描述
     */
    public CollectionResult(int code, String message) {
        this.code = code;
        this.msg = message;
    }

    /**
     * 构造函数
     *
     * @param code    错误或者成功代码
     * @param message 错误描述
     * @param data    响应结果
     */
    public CollectionResult(int code, String message, T data) {
        this.code = code;
        this.msg = message;
        this.data = data;
    }

    /**
     * 新增成功或失败
     *
     * @param flag 成功或失败
     * @return CollectionResult
     */
    public static <E> CollectionResult<E> insert(Boolean flag) {
        if (flag) {
            return new CollectionResult<>(ReturnCode.SUCCESS_INSERT.getCode(), ReturnCode.SUCCESS_INSERT.getMsg());
        } else {
            return new CollectionResult<>(ReturnCode.FAIL_INSERT.getCode(), ReturnCode.FAIL_INSERT.getMsg());
        }
    }

    /**
     * 修改成功或失败
     *
     * @param flag 成功或失败
     * @return CollectionResult
     */
    public static <E> CollectionResult<E> update(Boolean flag) {
        if (flag) {
            return new CollectionResult<>(ReturnCode.SUCCESS_UPDATE.getCode(), ReturnCode.SUCCESS_UPDATE.getMsg());
        } else {
            return new CollectionResult<>(ReturnCode.FAIL_UPDATE.getCode(), ReturnCode.FAIL_UPDATE.getMsg());
        }
    }


    /**
     * 删除成功或失败
     *
     * @param flag 成功或失败
     * @return CollectionResult
     */
    public static <E> CollectionResult<E> delete(Boolean flag) {
        if (flag) {
            return new CollectionResult<>(ReturnCode.SUCCESS_DELETE.getCode(), ReturnCode.SUCCESS_DELETE.getMsg());
        } else {
            return new CollectionResult<>(ReturnCode.FAIL_DELETE.getCode(), ReturnCode.FAIL_DELETE.getMsg());
        }
    }

    /**
     * 成功
     *
     * @return CollectionResult
     */
    public static <E> CollectionResult<E> success() {
        return new CollectionResult<>(ReturnCode.SUCCESS.getCode(), ReturnCode.SUCCESS.getMsg());
    }

    /**
     * 成功
     *
     * @param msg 成功描述
     * @return CollectionResult
     */
    public static <E> CollectionResult<E> success(String msg) {
        return new CollectionResult<>(ReturnCode.SUCCESS.getCode(), msg);
    }

    /**
     * 成功
     *
     * @param data 返回数据
     * @return CollectionResult
     */
    public static <E> CollectionResult<E> success(E data) {
        if (data instanceof Boolean){
            return new CollectionResult<>(ReturnCode.SUCCESS.getCode(), ReturnCode.SUCCESS.getMsg());
        }else {
            return new CollectionResult<>(ReturnCode.SUCCESS.getCode(), ReturnCode.SUCCESS.getMsg(), data);
        }
    }

    /**
     * 成功
     *
     * @param code 状态值
     * @param msg  成功描述
     * @return CollectionResult
     */
    public static <E> CollectionResult<E> success(int code, String msg) {
        return new CollectionResult<>(code, msg);
    }

    /**
     * 成功
     *
     * @param code 状态值
     * @param msg  成功描述
     * @param data 结果
     * @return CollectionResult
     */
    public static <E> CollectionResult<E> success(int code, String msg, E data) {
        return new CollectionResult<>(code, msg, data);
    }


    /**
     * 失败
     *
     * @return CollectionResult
     */
    public static <E> CollectionResult<E> failed() {
        return new CollectionResult<>(ReturnCode.ERROR_01.getCode(), ReturnCode.ERROR_01.getMsg());
    }

    /**
     * 失败
     *
     * @param msg 失败描述
     * @return CollectionResult
     */
    public static <E> CollectionResult<E> failed(String msg) {
        return new CollectionResult<>(ReturnCode.ERROR_01.getCode(), msg);
    }

    /**
     * 失败
     *
     * @param code 状态值
     * @param msg  失败描述
     * @return CollectionResult
     */
    public static <E> CollectionResult<E> failed(int code, String msg) {
        return new CollectionResult<>(code, msg);
    }

    /**
     * 失败
     *
     * @param code 状态值
     * @param msg  失败描述
     * @param data 结果
     * @return CollectionResult
     */
    public static <E> CollectionResult<E> failed(int code, String msg, E data) {
        return new CollectionResult<>(code, msg, data);
    }

    @Override
    public String toString() {
        return "CollectionResult{" +
                "data=" + data +
                ", code=" + code +
                ", msg='" + msg + '\'' +
                '}';
    }
}
