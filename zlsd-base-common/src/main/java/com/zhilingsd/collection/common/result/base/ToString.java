package com.zhilingsd.collection.common.result.base;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;

/**
 * 所有领域模型或者参数对象可继承此类
 *
 * @author zhangrong67
 * @version 1.0
 * @since 1.0 2017年12月12日 15:58
 */
public class ToString implements Serializable {

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
