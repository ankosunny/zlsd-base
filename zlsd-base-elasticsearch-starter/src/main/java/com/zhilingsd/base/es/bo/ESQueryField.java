package com.zhilingsd.base.es.bo;

import com.zhilingsd.base.es.emuns.ESearchType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: 智灵时代广州研发中心
 * @description:
 * @author: 吞星(yangguojun)
 * @create: 2020-03-03 17:52
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ESQueryField {

    //字段值
    private Object value;

    //字段类型
    private ESearchType eSearchType;


}
