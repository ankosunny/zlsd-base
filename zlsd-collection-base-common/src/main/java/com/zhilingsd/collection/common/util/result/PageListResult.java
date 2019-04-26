package com.zhilingsd.collection.common.util.result;

import com.zhilingsd.collection.common.emuns.BaseResultCodeEnum;
import com.zhilingsd.collection.common.util.result.info.Page;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 *
 * @author zhangrong67
 * @version 1.0
 * @since 1.0 2017年12月12日 17:00
 */
@ApiModel("分页模型")
public class PageListResult<T> extends ListResult<T> {

    @ApiModelProperty("分页对象")
    private Page page;

    public PageListResult() {
        super();
    }

    public PageListResult(BaseResultCodeEnum baseResultCodeEnum) {
        super(baseResultCodeEnum);
    }

    public PageListResult(List<T> dataList, Page page) {
        this(BaseResultCodeEnum.SUCCESS);
        setDataList(dataList);
        setPage(page);
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }
}
