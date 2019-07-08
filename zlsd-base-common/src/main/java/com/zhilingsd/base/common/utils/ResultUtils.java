package com.zhilingsd.base.common.utils;

import com.zhilingsd.base.common.emuns.ReturnCode;
import com.zhilingsd.base.common.exception.DAOException;
import com.zhilingsd.base.common.exception.ServiceException;
import com.zhilingsd.base.common.result.CommonResult;
import com.zhilingsd.base.common.result.ListResult;
import com.zhilingsd.base.common.result.SingleResult;

import java.util.Date;
import java.util.List;

/**
 *
 * 功能描述:返回值工具类
 * @param: [t]
 * @return: com.zhilingsd.base.common.result.ListResult<T>
 * @auther: 吞星
 * @date: 2019/6/26-11:30
 */
public class ResultUtils {

    public static <T> ListResult<T> getSuccessListResult(List<T> t) {
        ListResult listResult = getListResult(t);
        listResult.setCode(ReturnCode.SUCCESS.getCode());
        listResult.setMsg(ReturnCode.SUCCESS.getMsg());
        Date now = new Date();
        listResult.setSysTime(now.getTime() + "");
        return listResult;
    }

    public static <T> ListResult<T> getErrListResult(List<T> t, int errCode, String errMsg) {
        ListResult listResult = getListResult(t);
        listResult.setCode(errCode);
        listResult.setMsg(errMsg);
        return listResult;
    }

    public static <T> ListResult<T> getErrListResult(List<T> t, Exception e) {
        if (e instanceof DAOException) {
            DAOException daoException = (DAOException) e;
            return ResultUtils.getErrListResult(t, daoException.getCode(), daoException.getMessage());
        } else {
            ServiceException serviceException = (ServiceException) e;
            return ResultUtils.getErrListResult(t, serviceException.getCode(), serviceException.getMessage());
        }
    }

    public static <T> SingleResult<T> getSuccessSingleResult(T t) {
        SingleResult singleResult = getSingleResult(t);
        singleResult.setCode(ReturnCode.SUCCESS.getCode());
        singleResult.setMsg(ReturnCode.SUCCESS.getMsg());
        Date now = new Date();
        singleResult.setSysTime(now.getTime() + "");
        return singleResult;
    }

    public static <T> SingleResult<T> getErrSingleResult(T t, int errCode, String errMsg) {
        SingleResult singleResult = getSingleResult(t);
        singleResult.setCode(errCode);
        singleResult.setMsg(errMsg);
        return singleResult;
    }

    public static <T> SingleResult<T> getSingleResult(T t) {
        SingleResult singleResult = new SingleResult();
        singleResult.setData(t);
        return singleResult;
    }

    public static <T> ListResult<T> getListResult(List<T> t) {
        ListResult listResult = new ListResult();
        listResult.setDataList(t);
        return listResult;
    }

    public static <T> SingleResult<T> getErrSingleResult(T t, Exception e) {
        if (e instanceof DAOException) {
            DAOException daoException = (DAOException) e;
            return ResultUtils.getErrSingleResult(t, daoException.getCode(), daoException.getMessage());
        } else  {
            ServiceException serviceException = (ServiceException) e;
            return ResultUtils.getErrSingleResult(t, serviceException.getCode(), serviceException.getMessage());
        }
    }

    public static CommonResult getErrCommonResult(int errCode, String errMsg) {
        CommonResult commonResult = new CommonResult();
        commonResult.setCode(errCode);
        commonResult.setMsg(errMsg);
        commonResult.setSysTime(String.valueOf(new Date()));
        return commonResult;
    }

    public static CommonResult getErrCommonResult(Exception e) {
        if (e instanceof DAOException) {
            DAOException daoException = (DAOException) e;
            return ResultUtils.getErrCommonResult(daoException.getCode(), daoException.getMessage());
        } else {
            ServiceException serviceException = (ServiceException) e;
            return ResultUtils.getErrCommonResult(serviceException.getCode(), serviceException.getMessage());
        }
    }

    public static CommonResult getSuccessCommonResult() {
        CommonResult commonResult = new CommonResult();
        commonResult.setCode(ReturnCode.SUCCESS.getCode());
        commonResult.setMsg(ReturnCode.SUCCESS.getMsg());
        Date now = new Date();
        commonResult.setSysTime(now.getTime() + "");
        return commonResult;
    }

    /**
     * 功能描述:验证singleResult返回结果
     *
     * @param: [result, exceptionMsg]
     * @return: void
     * @auther: 吞星
     * @date: 2019/6/18-14:46
     */
    public static void verifyResult(SingleResult result, String exceptionMsg) {
        if (!(ReturnCode.SUCCESS.getCode() == result.code)) {
            throw new ServiceException(ReturnCode.BUSINESS_ERROR.getCode(), exceptionMsg + "-->" + result.getMsg());
        }
    }
}
