package com.zhilingsd.base.common.result;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;

/**
 * ^---^---^---^---^---^---^---^
 * --v---v---v---v---v---v---v--
 *
 * @author zou.cp
 * @version 1.0
 * @Description 简单分页传参处理
 * @createTime 2019年06月18日 15:59*
 * log.info()
 */
@Data
public class SimplePageBean {
    private static final long serialVersionUID = -917109313838368872L;

    /**
     * 当前页
     */
    @ApiModelProperty(value = "当前页")
    private Integer currentPage = 1;

    /**
     * 每页显示的总条数
     */
    @ApiModelProperty(value = "每页显示的总条数")
    private Integer pageSize = 10;
}
