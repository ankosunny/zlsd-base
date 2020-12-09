package com.zhilingsd.base.common.constants;

import java.util.Arrays;
import java.util.List;

/**
 * ^---^---^---^---^---^---^---^
 * --v---v---v---v---v---v---v--
 *
 * @author zou.cp
 * @version 1.0
 * @Description 常量表所存信息 , 同步redis
 * @createTime 2019年04月29日 09:31*
 * log.info()
 */
public class SearchInfoCacheConstant {

    //产品名称
    public static final String PRODUCT_NAME = "product_name";

    //户籍地址
    public static final String REGISTERED_ADDRESS = "registered_address";

    //案件地区
    public static final String CASE_AREA = "case_area";

    //催收流程  （下拉不使用 ， 流程过滤使用）
    public static final String COLLECTION_TYPE = "collection_type";

    //账龄
    public static final String BUCKET = "bucket";

    //手別
    public static final String BACTH_TIME = "bacth_time";

    //所有的案件缓存常量
    public static List<String> CONSTANT_CASE_TYPE_LIST = Arrays.asList(BUCKET, PRODUCT_NAME, REGISTERED_ADDRESS, CASE_AREA, COLLECTION_TYPE, BACTH_TIME);

}
