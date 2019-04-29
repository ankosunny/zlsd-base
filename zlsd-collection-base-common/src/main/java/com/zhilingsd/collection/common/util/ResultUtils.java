package com.zhilingsd.collection.common.util;

import com.zhilingsd.collection.common.emuns.BaseResultCodeEnum;
import com.zhilingsd.collection.common.emuns.ExceptionCodeEnum;
import com.zhilingsd.collection.common.exception.DAOException;
import com.zhilingsd.collection.common.exception.ServiceException;
import com.zhilingsd.collection.common.util.result.CommonResult;
import com.zhilingsd.collection.common.util.result.ListResult;
import com.zhilingsd.collection.common.util.result.SingleResult;
import com.zhilingsd.collection.common.util.result.info.Page;
import java.util.Date;
import java.util.List;

/**
 * Created by chenzongbo on 2017/12/13.
 */
public class ResultUtils {

    public static <T> ListResult<T> getSuccessListResult(List<T> t) {
        ListResult listResult = getListResult(t);
        listResult.setCode(BaseResultCodeEnum.SUCCESS.getCode());
        listResult.setMsg(BaseResultCodeEnum.SUCCESS.getMsg());
        Date now = new Date();
        listResult.setSysTime(now.getTime()+"");
        return listResult;
    }

    public static <T> ListResult<T> getErrListResult(List<T> t, String errCode, String errMsg) {
        ListResult listResult = getListResult(t);
        listResult.setCode(errCode);
        listResult.setMsg(errMsg);
        return listResult;
    }

    public static <T> ListResult<T> getErrListResult(List<T> t, Exception e) {
        if (e instanceof DAOException) {
            DAOException daoException = (DAOException) e;
            return ResultUtils.getErrListResult(t, daoException.getCode(), daoException.getMessage());
        }else if (e instanceof ServiceException) {
            ServiceException serviceException = (ServiceException) e;
            return ResultUtils.getErrListResult(t, serviceException.getCode(), serviceException.getMessage());
        }else{
            return ResultUtils.getErrListResult(t, ExceptionCodeEnum.UNKNOWN_ERROR_999.getCode(), ExceptionCodeEnum.UNKNOWN_ERROR_999.getMsg());
        }
    }

    public static <T> SingleResult<T> getSuccessSingleResult(T t) {
        SingleResult singleResult = getSingleResult(t);
        singleResult.setCode(BaseResultCodeEnum.SUCCESS.getCode());
        singleResult.setMsg(BaseResultCodeEnum.SUCCESS.getMsg());
        Date now = new Date();
        singleResult.setSysTime(now.getTime() + "");
        return singleResult;
    }

    public static <T> SingleResult<T> getErrSingleResult(T t, String errCode, String errMsg) {
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
        }else if (e instanceof ServiceException) {
            ServiceException serviceException = (ServiceException) e;
            return ResultUtils.getErrSingleResult(t, serviceException.getCode(), serviceException.getMessage());
        }else{
            return ResultUtils.getErrSingleResult(t, ExceptionCodeEnum.UNKNOWN_ERROR_999.getCode(), ExceptionCodeEnum.UNKNOWN_ERROR_999.getMsg());
        }
    }

    public static CommonResult getErrCommonResult(String errCode, String errMsg) {
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
        }else if (e instanceof ServiceException) {
            ServiceException serviceException = (ServiceException) e;
            return ResultUtils.getErrCommonResult(serviceException.getCode(), serviceException.getMessage());
        }else{
            return ResultUtils.getErrCommonResult(ExceptionCodeEnum.UNKNOWN_ERROR_999.getCode(), ExceptionCodeEnum.UNKNOWN_ERROR_999.getMsg());
        }
    }

    public static CommonResult getSuccessCommonResult() {
        CommonResult commonResult = new CommonResult();
        commonResult.setCode(BaseResultCodeEnum.SUCCESS.getCode());
        commonResult.setMsg(BaseResultCodeEnum.SUCCESS.getMsg());
        Date now = new Date();
        commonResult.setSysTime(now.getTime()+"");
        return commonResult;
    }
}
