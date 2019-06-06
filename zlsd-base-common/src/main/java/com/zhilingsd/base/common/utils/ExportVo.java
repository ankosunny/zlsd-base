package com.zhilingsd.base.common.utils;

import org.apache.poi.xwpf.usermodel.XWPFRun;

import java.util.Map;

/**
 * @program zlsd
 * @description:
 * @author: LiuHang
 * @create: 2019/06/04 14:41
 * @Copyright: 2018 www.mujinkeji.com Inc. All rights reserved.
 */

public abstract class ExportVo {

    public Map<String, String> exportValue;
    //测试导出打印函件

    public abstract void replaceContent(String text, XWPFRun bufferrun);

    public Map<String, String> getExportValue() {
        return exportValue;
    }

    public void setExportValue(Map<String, String> exportValue) {
        this.exportValue = exportValue;
    }
}
