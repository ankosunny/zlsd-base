package com.zhilingsd.base.common.result;


import com.zhilingsd.base.common.emuns.BaseResultCodeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author zhangrong67
 * @version 1.0
 * @since 1.0 2017年12月12日 17:02
 */
@ApiModel("结果列表模型")
public class ListResult<T> extends CommonResult {

    /**
     * 结果列表
     */
    @ApiModelProperty("结果列表")
    private List<T> dataList;

    public ListResult(){
    }

    public ListResult(BaseResultCodeEnum baseResultCodeEnum) {
        super(baseResultCodeEnum);
    }

    public ListResult(List<T> dataList) {
        this(BaseResultCodeEnum.SUCCESS);
        setDataList(dataList);
    }

    public List<T> getDataList() {
        return dataList;
    }

    public void setDataList(List<T> dataList) {
        this.dataList = dataList;
    }
}
