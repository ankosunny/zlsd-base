package com.zhilingsd.base.common.utils;

import org.apache.poi.xwpf.usermodel.XWPFRun;

/**
 * @program zlsd
 * @description:
 * @author: LiuHang
 * @create: 2019/06/04 14:41
 * @Copyright: 2018 www.mujinkeji.com Inc. All rights reserved.
 */

public abstract class ExportVo {
    //测试导出打印函件

    public abstract void replaceContent(String text, XWPFRun bufferrun);

}
