package com.zhilingsd.base.common.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by liyang on 2018/3/19
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PairDef<T, S> {
    private T first;
    private S second;
}
