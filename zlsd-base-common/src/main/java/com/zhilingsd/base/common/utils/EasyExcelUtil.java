package com.zhilingsd.base.common.utils;

import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.BaseRowModel;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.util.CollectionUtils;
import com.alibaba.fastjson.JSON;
import com.zhilingsd.base.common.exception.BusinessException;
import com.zhilingsd.base.common.result.CollectionResult;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

/**
 * Description: 封装的EasyExcel导出工具类
 *
 * @Author zengkai
 * @Date 2019/5/6 16:50
 */
@Slf4j
public class EasyExcelUtil {

//    private static final String HEADER_CONTENT_DISPOSITION="Content-Disposition";
//    private static final String HEADER_RESPONSE_MESSAGE="response-message";
//    private static final String MEDIA_TYPE_OCTET_STREAM="application/octet-stream";
//    private static final String MEDIA_TYPE_JSON="application/json;charset=UTF-8";
//    private static final String CHARACTER_ENCODING="UTF-8";

    /**
     * Description: 导出excel 支持一张表导出多个sheet
     *
     * @param response             输出流
     * @param sheetNameAndDateList sheetName和每个sheet的数据
     * @param type                 要导出的excel的类型 有ExcelTypeEnum.xls 和有ExcelTypeEnum.xlsx
     * @param fileName             文件名
     * @return void
     * @Author zengkai
     * @Date 2019/5/6 16:52
     */
    public static void createExcelStreamMutilByEaysExcel(HttpServletResponse response, Map<String, List<? extends BaseRowModel>> sheetNameAndDateList, ExcelTypeEnum type, String fileName) throws IOException {

        if (checkParam(sheetNameAndDateList, type)) {
            response.setContentType("multipart/form-data");
            response.setCharacterEncoding("utf-8");
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "utf-8") + type.getValue());
            response.setHeader("response-message", "attachment;filename=" + URLEncoder.encode(JSON.toJSONString(CollectionResult.success()), "utf-8"));
            ServletOutputStream out = response.getOutputStream();
            ExcelWriter writer = new ExcelWriter(out, type, true);
            setSheet(sheetNameAndDateList, writer);
            writer.finish();
            out.flush();
        } else {
            throw new BusinessException("导出数据存在错误信息");
        }
    }

    /**
     * @param sheetNameAndDateList
     * @param type
     * @throws IOException
     */
    public static byte[] getExcelFileBytes(Map<String, List<? extends BaseRowModel>> sheetNameAndDateList, ExcelTypeEnum type) throws IOException {
        ByteArrayOutputStream out = null;
        if (checkParam(sheetNameAndDateList, type)) {
            out = new ByteArrayOutputStream();
            ExcelWriter writer = new ExcelWriter(out, type, true);
            setSheet(sheetNameAndDateList, writer);
            writer.finish();
            out.flush();
        } else {
            throw new BusinessException("导出数据存在错误信息");
        }
        return out.toByteArray();
    }

    /**
     * Description: setSheet数据
     *
     * @param SheetNameAndDateList
     * @param writer
     * @return void
     * @Author zengkai
     * @Date 2019/5/6 16:52
     */
    private static void setSheet(Map<String, List<? extends BaseRowModel>> SheetNameAndDateList, ExcelWriter writer) {
        int sheetNum = 1;
        for (Map.Entry<String, List<? extends BaseRowModel>> stringListEntry : SheetNameAndDateList.entrySet()) {
            Sheet sheet = new Sheet(sheetNum, 0, stringListEntry.getValue().get(0).getClass());
            sheet.setSheetName(stringListEntry.getKey());
            writer.write(stringListEntry.getValue(), sheet);
            sheetNum++;
        }
    }


    /**
     * Description: 校验参数
     *
     * @param sheetNameAndDateList
     * @param type
     * @return boolean
     * @Author zengkai
     * @Date 2019/5/6 16:52
     */
    private static boolean checkParam(Map<String, List<? extends BaseRowModel>> sheetNameAndDateList, ExcelTypeEnum type) {
        if (CollectionUtils.isEmpty(sheetNameAndDateList)) {
            log.error("SheetNameAndDateList不能为空");
            return false;
        } else if (type == null) {
            log.error("导出的excel类型不能为空");
            return false;
        }
        for (List<? extends BaseRowModel> value : sheetNameAndDateList.values()) {
            if (CollectionUtils.isEmpty(value)) {
                log.error("导出的excel数据对象不能为空");
                return false;
            }
        }
        return true;
    }


}
