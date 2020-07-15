package com.zhilingsd.base.es.bo;

import lombok.Data;

import java.util.List;

/**
 * @program: 智灵时代广州研发中心
 * @description:
 * @author: 吞星(yangguojun)
 * @create: 2020-03-06 17:09
 **/
@Data
public class QueryDocumentOutBO {
    private List<HitBO> hitBOS;
    /*总记录数*/
    private Long totalHits;


}
