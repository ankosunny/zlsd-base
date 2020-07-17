package com.zhilingsd.base.es.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program zlsd
 * @description:
 * @author: LiuHang
 * @create: 2020/03/05 11:03
 * @Copyright: 2018 www.mujinkeji.com Inc. All rights reserved.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupCountRecordBo {
    /*分组统计的数据*/
    private Long countNum;
    /*分组的字段*/
    private String groupByColum;
}
