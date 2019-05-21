package com.zhilingsd.base.common.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * ^---^---^---^---^---^---^---^
 * --v---v---v---v---v---v---v--
 *
 * @author zou.cp
 * @version 1.0
 * @Description  导出ZIP压缩文件使用
 * @createTime 2019年05月18日 10:58*
 * log.info()
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ZipFilleStream implements Serializable {

    private String fileName;
    private byte[] content;
}
