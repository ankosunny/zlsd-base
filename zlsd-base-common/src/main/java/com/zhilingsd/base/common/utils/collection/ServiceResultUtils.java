package com.zhilingsd.base.common.utils.collection;


import com.google.common.collect.Lists;
import com.zhilingsd.base.common.emuns.CollectionTypeEnum;
import com.zhilingsd.base.common.emuns.ReturnCode;
import com.zhilingsd.base.common.exception.BusinessException;
import com.zhilingsd.base.common.result.SingleResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.formula.functions.T;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * **className CollectionTypeUtils
 *
 * @author: liuhang
 * ******date: 2018/12/27 11:52
 * description: This is a  催收工具类
 * ----------------------------------------
 * =======================================>
 */
@Slf4j
public class ServiceResultUtils {
    /*****************************重构使用******************************************************/
    public Object checkResult(SingleResult result) {
        Object data = null;
        if ( ReturnCode.SUCCESS.getCode()==result.getCode()) {
            data = result.getData();
        } else {
            log.error("调用接口获取数据失败：{}", result.getMsg());
            throw new BusinessException(result.getCode(),result.getMsg());
        }
        return data;
    }


}
