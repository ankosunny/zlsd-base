package com.zhilingsd.base.es.bo;

import lombok.Data;

/**
 * @program: 智灵时代广州研发中心
 * @description:封装es hit结构
 * @author: 吞星(yangguojun)
 * @create: 2020-03-16 18:30
 **/
@Data
public class HitBO<T> {
    /*documentId*/
    private String documentId;
    /*索引名*/
    private String indexName;
    /*文档内容*/
    private T ducumentContent;
}
