//package com.zhilingsd.base.common.utils;
//
//import lombok.extern.slf4j.Slf4j;
//import org.docx4j.Docx4J;
//import org.docx4j.convert.out.FOSettings;
//import org.docx4j.fonts.IdentityPlusMapper;
//import org.docx4j.fonts.Mapper;
//import org.docx4j.fonts.PhysicalFont;
//import org.docx4j.fonts.PhysicalFonts;
//import org.docx4j.jaxb.Context;
//import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
//import org.docx4j.wml.RFonts;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.OutputStream;
//import java.net.URL;
//
///**
// * @Author: zhangbo
// * @DateTime: 2019/5/14 14:55
// */
//@Slf4j
//public class DocxToPDFConverter {
//
//    private InputStream inStream;
//    private OutputStream outStream;
//
//    public DocxToPDFConverter(InputStream inStream, OutputStream outStream) {
//        this.inStream = inStream;
//        this.outStream = outStream;
//    }
//
//    public void convert() throws Exception {
//
//        log.info("docx to pdf:{}", "convert start");
//
//        InputStream iStream = inStream;
//        WordprocessingMLPackage wordMLPackage = getMLPackage(iStream);
//
//        ClassLoader classLoader = this.getClass().getClassLoader();
//
//        Mapper fontMapper = new IdentityPlusMapper();
//        wordMLPackage.setFontMapper(fontMapper);
//
////        Linux下
//        String fontFamily = "SimSun";
//
//        //加载字体文件（解决linux环境下无中文字体问题）
//        URL simsunUrl = classLoader.getResource("font/simsun.ttc");
//        PhysicalFonts.addPhysicalFont(fontFamily, simsunUrl);
//        PhysicalFont simsunFont = PhysicalFonts.get(fontFamily);
//        fontMapper.put(fontFamily, simsunFont);
//
//        //设置文件默认字体
//        RFonts rfonts = Context.getWmlObjectFactory().createRFonts();
//        rfonts.setAsciiTheme(null);
//        rfonts.setAscii(fontFamily);
//        wordMLPackage.getMainDocumentPart().getPropertyResolver()
//                .getDocumentDefaultRPr().setRFonts(rfonts);
//
//        log.info("docx to pdf: load font finish.");
//
//        FOSettings foSettings = Docx4J.createFOSettings();
//        foSettings.setWmlPackage(wordMLPackage);
//        Docx4J.toFO(foSettings, outStream, Docx4J.FLAG_EXPORT_PREFER_XSL);
//
//        try {
//            inStream.close();
//            outStream.close();
//        } catch (IOException e) {
//            throw e;
//        }
//
//        log.info("docx to pdf: convert finish.");
//    }
//
//    private WordprocessingMLPackage getMLPackage(InputStream iStream) throws Exception {
//        WordprocessingMLPackage mlPackage = WordprocessingMLPackage.load(iStream);
//        return mlPackage;
//    }
//}