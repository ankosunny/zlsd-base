package com.zhilingsd.base.common.utils;

import com.zhilingsd.base.common.vo.ReportExportVo;
import com.zhilingsd.base.common.vo.VisitExportVo;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.apache.xmlbeans.XmlOptions;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTBody;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import lombok.extern.slf4j.Slf4j;

/**
 * @Author: zhangbo
 * @DateTime: 2019/5/17 9:34
 */
@Slf4j
public class ReportWordUtil {
    /**
     * @description 导出ZIP文件
     **/
    public static byte[] getWorldZipFile(List<byte[]> listBytes, List<VisitExportVo> list) throws IOException {
        //最大10M的world文件
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(10 * 1024);
        ZipOutputStream zipOut = new ZipOutputStream(byteArrayOutputStream);
        try {
            if (!CollectionUtils.isEmpty(list)) {
                for (int i = 0; i < list.size(); i++) {
                    VisitExportVo vo = list.get(i);
                    //输出地址 输入地址 加随机数
                    InputStream is = new ByteArrayInputStream(listBytes.get(i));
                    XWPFDocument doc = new XWPFDocument(is);
                    replaceContent(doc, vo);
                    //把doc输出到输出流中
                    ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream();
                    doc.write(byteOutputStream);

                    zipOut.putNextEntry(new ZipEntry(System.currentTimeMillis() + ".docx"));
                    byte[] bytes = byteOutputStream.toByteArray();
                    zipOut.write(bytes);
                    zipOut.closeEntry();
                    byteOutputStream.close();
                }
                zipOut.close();
                String zipName = DateUtil.convertDateToString(DateUtil.DATE_TIME_PATTERN, new Date()) + ".zip";
                return byteArrayOutputStream.toByteArray();
            }
        }  finally {
            if (zipOut != null) {
                zipOut.close();
            }
            if (byteArrayOutputStream != null) {
                byteArrayOutputStream.close();
            }
        }
        return null;
    }

    /**
     * 导出单个文件 world
     *
     * @param bytes 输入地址
     * @throws Exception 导出单个文件
     */
    public static byte[] getWorldFile(byte[] bytes, ReportExportVo vo) throws Exception {
        ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream();
        try {
            //输出地址 输入地址 加随机数
            InputStream is = new ByteArrayInputStream(bytes);
            XWPFDocument docx = new XWPFDocument(is);
            replaceContent(docx, vo);
            //把doc输出到输出流中
            docx.write(byteOutputStream);
            byteOutputStream.close();
            return byteOutputStream.toByteArray();
        } finally {
            if (null != byteOutputStream) {
                byteOutputStream.close();
            }
        }
    }

    /**
     * @description 两个对象进行追加
     **/
    public static XWPFDocument mergeWord(XWPFDocument document1, XWPFDocument document2) throws Exception {
        //设置分页符---当刚好一页数据时，会导致出现空白页，后面出现分页符导致格式有一点差异
        //解决方法是，在模板头上增加分页符
        CTBody src1Body = document1.getDocument().getBody();
        CTBody src2Body = document2.getDocument().getBody();
        XmlOptions optionsOuter = new XmlOptions();
        optionsOuter.setSaveOuter();
        String appendString = src2Body.xmlText(optionsOuter);
        String srcString = src1Body.xmlText();
        String prefix = srcString.substring(0, srcString.indexOf(">") + 1);
        String mainPart = srcString.substring(srcString.indexOf(">") + 1, srcString.lastIndexOf("<"));
        String sufix = srcString.substring(srcString.lastIndexOf("<"));
        String addPart = appendString.substring(appendString.indexOf(">") + 1, appendString.lastIndexOf("<"));
        CTBody makeBody = CTBody.Factory.parse(prefix + mainPart + addPart + sufix);
        src1Body.set(makeBody);
        return document1;
    }

    /**
     * @description 追加文档属性设置
     **/
    public static ResponseEntity<byte[]> mergeWord(List<byte[]> byteArrList, String docName) throws Exception {
        //设置分页符---当刚好一页数据时，会导致出现空白页，后面出现分页符导致格式有一点差异
        //解决方法是，在模板头上增加分页符
        InputStream is = new ByteArrayInputStream(byteArrList.get(0));
        XWPFDocument docx = new XWPFDocument(is);
        for (int i = 0; i < byteArrList.size(); i++) {
            if (i == 1) {
                continue;
            } else {
                InputStream newIs = new ByteArrayInputStream(byteArrList.get(0));
                XWPFDocument newDocx = new XWPFDocument(newIs);
                docx = mergeWord(docx, newDocx);
            }
        }
        //把doc输出到输出流中
        ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream();
        docx.write(byteOutputStream);
        byteOutputStream.close();
        return SpringWebFileUtil.download(byteOutputStream.toByteArray(), docName);
    }

    /**
     * 获取填写日期 +3天
     *
     * @param onDoorDate
     * @return
     */
    private static String getWiteDate(String onDoorDate) {
        String result = "";
        try {
            Date date = DateUtil.addDate(DateUtil.convertStringToDate(DateUtil.DATE_MINUTE_CHINESE_YMD, onDoorDate), 3);
            result = DateUtil.convertDateToString(DateUtil.DATE_MINUTE_CHINESE_YMD, date);
        } catch (ParseException ex) {
            log.error(ex.getMessage(), ex);
        }
        return result;
    }


    private static void replaceContent(XWPFDocument doc, ExportVo vo){
        Map<String,String> values = vo.getExportValue();
        //文本
        for (XWPFParagraph paragraph : doc.getParagraphs()) {
            replaceContent(values, paragraph);
        }

        //表格
        Iterator<XWPFTable> iterator = doc.getTablesIterator();
        XWPFTable table;
        List<XWPFTableRow> rows;
        List<XWPFTableCell> cells;
        while (iterator.hasNext()) {
            table = iterator.next();
            rows = table.getRows();
            List<XWPFParagraph> paras;
            for (XWPFTableRow row : rows) {
                cells = row.getTableCells();
                for (XWPFTableCell cell : cells) {
                    paras = cell.getParagraphs();
                    for (XWPFParagraph para : paras) {
                        replaceContent(values,para);
                    }
                }
            }
        }

    }

    private static void replaceContent(Map<String, String> values, XWPFParagraph paragraph) {
        String LEFT = "[" ; String RIGHT = "]" ;
        List<XWPFRun> runs = paragraph.getRuns();

        if(!CollectionUtils.isEmpty(runs)){
            StringBuilder sb = new StringBuilder();
            //word文档
            boolean flag = false;
            for (int i=0; i<runs.size(); i++) {
                XWPFRun run = runs.get(i);
                String runText = run.toString();
                if(StringUtils.isNotBlank(runText) && runText.contains(RIGHT)){
                    run.setText("",0);
                    flag = false;
                }
                if(flag){
                    runText = values.get(runText);
                    run.setText(runText,0);
                }
                if(StringUtils.isNotBlank(runText) && runText.contains(LEFT)){
                    run.setText("",0);
                    flag = true;
                }
            }
        }
    }

    /**
     * 导出单个文件 world
     *
     * @param bytes 输入地址
     * @throws Exception 导出单个文件
     */
    public static void createWorldFile(byte[] bytes, ReportExportVo vo) throws Exception {
        //输出地址 输入地址 加随机数
        InputStream is = new ByteArrayInputStream(bytes);
        XWPFDocument docx = new XWPFDocument(is);
        replaceContent(docx, vo);
        //把doc输出到输出流中
        File file = new File("F:\\data\\c.docx");
        if (!file.exists()) {
            file.createNewFile();
            FileOutputStream fileOutputStream = null;
            try {
                fileOutputStream = new FileOutputStream(file);
                docx.write(fileOutputStream);
            } catch (Exception e) {
                log.error("导出错误：{}", e);
            } finally {
                if (Objects.nonNull(fileOutputStream)) {
                    fileOutputStream.close();
                }
            }
        }
    }

    public static void main(String[] args) {

        ReportExportVo vo = new ReportExportVo();
        vo.setName("我的姓名");
        vo.setCardNum("123456789");
        vo.setCommitMoney("100000");
        vo.setStaffName("我是催收员");
        vo.setAddress("我的地址");
        vo.setArea("我的地区");
        vo.setIdCard("511321777777");
        vo.setCommitYear("2019");
        vo.setCommitMonth("5");
        vo.setCommitDay("21");
        vo.setYear("2019");
        vo.setMonth("5");
        vo.setDay("20");
        vo.setStaffTelPhone("10010");
        vo.setDate("2019-05-21");
        vo.setLastName("李");
        vo.setBillCode("case0010");
        vo.setBeginDate("2019-05-21");
        vo.setBeginTime("15:16");
        vo.setEndTime("18:00");
        vo.setVisitPhone("1578787878");
        vo.setCommitDate("2019-05-21");
        vo.setDebtMoney("1000");
        vo.setVisitDesc("外放中");

        byte[] readSize = new byte[8 * 1024];

        try {
            FileInputStream fileInputStream = new FileInputStream(new File("F:\\data\\外访通用模板.docx"));
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            while (fileInputStream.read(readSize) != -1) {
                byteArrayOutputStream.write(readSize);
            }
            createWorldFile(byteArrayOutputStream.toByteArray(), vo);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}