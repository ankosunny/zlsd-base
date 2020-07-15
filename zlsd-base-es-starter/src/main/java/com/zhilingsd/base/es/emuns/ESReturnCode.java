package com.zhilingsd.base.es.emuns;

import com.zhilingsd.base.common.bean.IReturnCode;

/**
 * @program: 智灵时代广州研发中心
 * @description:
 * @author: 吞星(yangguojun)
 * @create: 2020-02-26 21:41
 **/
public enum ESReturnCode implements IReturnCode {


    //ES功能
    ERROR_CODE_110501(110501,"保存数据到ES失败"),
    ERROR_CODE_110502(110502,"从ES查询数据失败,未搜索到保存过的数据"),
    ERROR_CODE_110503(110503,"修改数据到ES失败"),
    ERROR_CODE_110504(110504,"从ES删除数据失败"),
    ERROR_CODE_110505(110505, "保存转换角色后的数据失败"),
    ERROR_CODE_110506(110506, "从ES查询数据失败"),
    ERROR_CODE_110507(110507, "构建ES查询失败"),
    ;

    private int code;
    private String msg;

    ESReturnCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }


    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return msg;
    }

}
