/**
 * Software License Declaration.
 * <p>
 * zhilingsd.com, Co,. Ltd.
 * Copyright © 2016 All Rights Reserved.
 * <p>
 * Copyright Notice
 * This documents is provided to zhilingsd contracting agent or authorized programmer only.
 * This source code is written and edited by zhilingsd Co,.Ltd Inc specially for financial
 * business contracting agent or authorized cooperative company, in order to help them to
 * install, programme or central control in certain project by themselves independently.
 * <p>
 * Disclaimer
 * If this source code is needed by the one neither contracting agent nor authorized programmer
 * during the use of the code, should contact to zhilingsd Co,. Ltd Inc, and get the confirmation
 * and agreement of three departments managers  - Research Department, Marketing Department and
 * Production Department.Otherwise zhilingsd will charge the fee according to the programme itself.
 * <p>
 * Any one,including contracting agent and authorized programmer,cannot share this code to
 * the third party without the agreement of zhilingsd. If Any problem cannot be solved in the
 * procedure of programming should be feedback to zhilingsd Co,. Ltd Inc in time, Thank you!
 */
package com.zhilingsd.base.es.core;

import java.io.IOException;
import java.util.List;

import com.zhilingsd.base.es.core.mapping.IndexCoordinates;
import com.zhilingsd.base.es.core.query.*;
import org.elasticsearch.common.settings.Settings;


/**
 * @className ElasticSearchTemplate.java
 * @description //TODO
 * @author linmenghuai
 * @version 1.0
 * @date 2020/9/25 10:14
 */
public interface ElasticSearchTemplate {

    boolean doCreate(IndexCoordinates index, Class clazz);

    boolean doCreate(IndexCoordinates index, Class clazz, Settings.Builder setting);

    /**
     * 默认ES生成主键
     * */
    Boolean index(Object record, IndexCoordinates index);

    /**
     * 由业务生成主键（推荐）
     * */
    Boolean index(Object record, String id, IndexCoordinates index);

    Object getDocumentByKey(IndexCoordinates index, String id);

    List<HitEntity> query(Object queryObject, ESNormalQuery normalQuery) throws Exception;

    ScrollResult queryScroll(Object queryObject, ESNormalQuery normalQuery) throws Exception;

    ScrollResult queryScroll(Object queryObject, ESNormalQuery normalQuery, String scrollId) throws Exception;

    PageDocumentResult queryPage(Object queryObject, ESNormalQuery normalQuery) throws Exception;

    BulkIndexResult bulkIndex(Object object, String fieldName, IndexCoordinates index) throws IOException;


    /**
     * 根据主键删除文档
     * */
    public Boolean removeById(String id, IndexCoordinates index);

}
