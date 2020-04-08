package com.zhilingsd.base.common.utils;

import com.zhilingsd.base.common.emuns.ReturnCode;
import com.zhilingsd.base.common.exception.CoreException;
import com.zhilingsd.base.common.result.SingleResult;
import org.apache.poi.ss.formula.functions.T;

/**
 * @program: 智灵时代广州研发中心
 * @description:针对singleResult返回值工具类
 * @author: 吞星(yangguojun)
 * @create: 2020-04-01 18:25
 **/
public class SingleResultUtil {


    /**
     * 功能描述:请求成功时，返回data
     *
     * @param singleResult
     * @return W
     * @auther 吞星（yangguojun）
     * @date 2020/4/1-18:50
     */
    public static <W> W getSuccessSingleResultSucData (SingleResult<W> singleResult){
        if (ReturnCode.SUCCESS.getCode() == singleResult.getCode()) {
            W data = singleResult.getData();
            return data;
        } else {
            throw new CoreException(singleResult.getCode(), singleResult.getMsg());
        }
    }


}
