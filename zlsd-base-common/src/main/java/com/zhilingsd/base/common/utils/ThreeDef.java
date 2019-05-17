package com.zhilingsd.base.common.utils;

import com.zhilingsd.base.common.utils.collection.GV;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by liyang on 2018/4/12
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ThreeDef<T, S, K> implements Comparable<ThreeDef> {
    private T first;
    private S second;
    private K third;

    @Override
    public int compareTo(ThreeDef o) {
        return GV.i(this.getFirst()) - GV.i(o.getFirst());
    }
}
