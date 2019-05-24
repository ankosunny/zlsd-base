package com.zhilingsd.base.common.utils;

import com.zhilingsd.base.common.vo.ReportExportVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xwpf.usermodel.IRunBody;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlObject;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTR;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;

import java.io.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @Author: zhangbo
 * @DateTime: 2019/5/17 9:34
 */
@Slf4j
public class ReportWordUtil {
    /**
     * @description 导出ZIP文件
     **/
    public static ResponseEntity<byte[]> getWorldZipFile(List<byte[]> listBytes, List<ReportExportVo> list) throws IOException {
        //最大10M的world文件
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(10 * 1024);
        ZipOutputStream zipOut = new ZipOutputStream(byteArrayOutputStream);
        try {
            if (!CollectionUtils.isEmpty(list)) {
                for (int i = 0; i < list.size(); i++) {
                    ReportExportVo vo = list.get(i);
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
                return SpringWebFileUtil.download(byteArrayOutputStream.toByteArray(), zipName);
            }
        } catch (XmlException e) {
            e.printStackTrace();
        } finally {
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
    public static ResponseEntity<byte[]> getWorldFile(byte[] bytes, ReportExportVo vo) throws Exception {
        ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream();
        try {
            //输出地址 输入地址 加随机数
            InputStream is = new ByteArrayInputStream(bytes);
            XWPFDocument docx = new XWPFDocument(is);
            replaceContent(docx, vo);
            //把doc输出到输出流中
            docx.write(byteOutputStream);
            byteOutputStream.close();
            String docName = DateUtil.convertDateToString(DateUtil.DATE_TIME_PATTERN, new Date()) + ".docx";
            return SpringWebFileUtil.download(byteOutputStream.toByteArray(), docName);
        } finally {
            if (null != byteOutputStream) {
                byteOutputStream.close();
            }
        }
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


    private static void replaceContent(XWPFDocument doc, ReportExportVo vo) throws XmlException {
        for (XWPFParagraph paragraph : doc.getParagraphs()) {
            XmlCursor cursor = paragraph.getCTP().newCursor();
            cursor.selectPath("declare namespace w='http://schemas.openxmlformats.org/wordprocessingml/2006/main' .//*/w:txbxContent/w:p/w:r");
            List<XmlObject> ctrsintxtbx = new ArrayList<>();
            while (cursor.hasNextSelection()) {
                cursor.toNextSelection();
                XmlObject obj = cursor.getObject();
                ctrsintxtbx.add(obj);
            }
            String preText = "";
            StringBuffer text1 = new StringBuffer();
            for (XmlObject obj : ctrsintxtbx) {
                CTR ctr = CTR.Factory.parse(obj.xmlText());
                //CTR ctr = CTR.Factory.parse(obj.newInputStream());
                XWPFRun bufferrun = new XWPFRun(ctr, (IRunBody) paragraph);
                log.info("bufferrun.getTextPosition()：{}", bufferrun.getTextPosition());
                System.out.println(bufferrun.getText(0));
            }
            log.info("文档内容：{}", text1);
            for (int i = 0; i < ctrsintxtbx.size(); i++) {
                XmlObject obj = ctrsintxtbx.get(i);
                CTR ctr = CTR.Factory.parse(obj.xmlText());
                //CTR ctr = CTR.Factory.parse(obj.newInputStream());
                XWPFRun bufferrun = new XWPFRun(ctr, (IRunBody) paragraph);
                log.info("bufferrun.getTextPosition()：{}", bufferrun.getTextPosition());
                String text = bufferrun.getText(0);
                System.out.println(text);
                if (i != 0) {
                    preText = new XWPFRun(CTR.Factory.parse(ctrsintxtbx.get(i - 1).xmlText()), (IRunBody) paragraph).getText(0);
                }
                if (text != null) {
                    if ("[".equals(text)) {
                        bufferrun.setText("", 0);
                        obj.set(bufferrun.getCTR());
                        continue;
                    }
                    if ("]".equals(text)) {
                        bufferrun.setText("", 0);
                        obj.set(bufferrun.getCTR());
                        if (!StringUtils.isBlank(preText)) {
                            text = "[" + preText + "]";
                        }
                    }

                    for (String word : vo.getExportValue().keySet()) {
                        if (word.equals(text) || text.contains(word)) {
                            text = text.replace(word, vo.getExportValue().get(word));
                            bufferrun.setText(text, 0);
                            break;
                        }
                    }
                }
                obj.set(bufferrun.getCTR());
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
        File file = new File("F:\\新私有化\\导出\\c.docx");
        if (!file.exists()) {
            file.createNewFile();
            FileOutputStream fileOutputStream = null;
            try{
                fileOutputStream = new FileOutputStream(file);
                docx.write(fileOutputStream);
            } catch (Exception e) {
                log.error("导出错误：{}",e);
            }finally {
                if (Objects.nonNull(fileOutputStream)){
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
            FileInputStream fileInputStream = new FileInputStream(new File("F:\\新私有化\\导出\\a.docx"));
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            while (fileInputStream.read(readSize) != -1) {
                byteArrayOutputStream.write(readSize);
            }
            createWorldFile(byteArrayOutputStream.toByteArray(), vo);
        } catch (Exception e) {
            System.out.println(e);
        }

    }
}