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
public class PageDocumentOutBO {

    /**
     * 当前页码
     */
    private int curPage;

    /**
     * 每页项数
     */
    private int pageSize;

    /**
     * 记录总数
     */
    private long totalRecord;

    /*页数据*/
    private List<HitBO> hitBOS;


}
