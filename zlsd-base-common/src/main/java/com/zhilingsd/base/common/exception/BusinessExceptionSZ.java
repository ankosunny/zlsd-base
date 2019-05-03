package com.zhilingsd.base.common.exception;

import com.zhilingsd.base.common.emuns.ReturnCode;
import lombok.Data;

/**
 * Description: 自定义业务异常
 *
 * @Author zengkai
 * @Date 2019/4/15 13:33
 */
@Data
public class BusinessExceptionSZ extends RuntimeException {

    private Integer code;
    private String msg;

    public BusinessExceptionSZ(Integer code) {
        this.code = code;
        this.msg = ReturnCode.getValueByCode(code);
    }

    public BusinessExceptionSZ(String msg) {
        super(msg);
        this.msg = msg;
    }

    public BusinessExceptionSZ(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
