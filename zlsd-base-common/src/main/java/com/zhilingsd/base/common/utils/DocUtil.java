package com.zhilingsd.base.common.utils;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import org.apache.commons.io.IOUtils;
import org.docx4j.dml.wordprocessingDrawing.Inline;
import org.docx4j.jaxb.Context;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.PartName;
import org.docx4j.openpackaging.parts.WordprocessingML.AlternativeFormatInputPart;
import org.docx4j.openpackaging.parts.WordprocessingML.BinaryPartAbstractImage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.docx4j.relationships.Relationship;
import org.docx4j.wml.*;

import java.io.*;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: lq
 * Description:
 */
public class DocUtil {
    private static ObjectFactory factory = new ObjectFactory();

    /**
     * 合并docx
     *
     * @param streams 要合并的word文件的输入流
     * @param path    合并后的文件的路径
     * @return
     * @throws Docx4JException
     * @throws IOException
     */
    public static File mergeDocx(final List<InputStream> streams, String path) throws Docx4JException, IOException {

        WordprocessingMLPackage target = null;
        File generated = new File(path);
        int chunkId = 0;
        Iterator<InputStream> it = streams.iterator();
        while (it.hasNext()) {
            InputStream is = it.next();
            if (is != null) {
                try {
                    if (target == null) {
                        // Copy first (master) document
                        OutputStream os = new FileOutputStream(generated);
                        os.write(IOUtils.toByteArray(is));
                        os.close();

                        target = WordprocessingMLPackage.load(generated);
                    } else {
                        MainDocumentPart documentPart = target.getMainDocumentPart();
                        // 另起一页，换页
                        addPageBreak(documentPart);
                        insertDocx(documentPart, IOUtils.toByteArray(is), chunkId++);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    is.close();
                }
            }
        }

        if (target != null) {
            target.save(generated);
//            Docx4J.save(target, generated, Docx4J.FLAG_NONE);
            return generated;
        } else {
            return null;
        }
    }
    /**
     * @description  文档混合处理
     **/
    public static byte[] mergeDocx(final List<InputStream> streams) throws IOException, Docx4JException {
        WordprocessingMLPackage target = null;
        File generated = File.createTempFile("函件套打", ".docx");
        int chunkId = 0;
        Iterator<InputStream> it = streams.iterator();
        while (it.hasNext()) {
            InputStream is = it.next();
            if (is != null) {
                try {
                    if (target == null) {
                        long start2 = System.currentTimeMillis();
                        // Copy first (master) document
                        long start3 = System.currentTimeMillis();
                        target = WordprocessingMLPackage.load(is);
                        System.out.println("start3 第一页 花费时间 ：" +(System.currentTimeMillis()-start3));
                        System.out.println("第一页 花费时间 ：" +(System.currentTimeMillis()-start2));
                    } else {
                        long start1 = System.currentTimeMillis();
                        MainDocumentPart documentPart = target.getMainDocumentPart();
                        // 另起一页，换页
                        addPageBreak(documentPart);
                        insertDocx(documentPart, IOUtils.toByteArray(is), chunkId++);
                        System.out.println("另起一页 花费时间 ：" +(System.currentTimeMillis()-start1));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    is.close();
                }
            }
        }

        if (target != null) {
            long start = System.currentTimeMillis();
            target.save(generated);
            System.out.println("save 花费时间 ：" +(System.currentTimeMillis()-start));
            return getFileBytes(generated);
        } else {
            return null;
        }
    }


    /**
     * 获得指定文件的byte数组
     */
    public static byte[] getFileBytes(File file){
        long start = System.currentTimeMillis();
        byte[] buffer = null;try {
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream(2048);
            byte[] b = new byte[2048];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            buffer = bos.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("花费时间："+(System.currentTimeMillis()-start));
        return buffer;
    }

    /**
     * @description  插入文档
     **/
    private static void insertDocx(MainDocumentPart main, byte[] bytes, int chunkId) {
        try {
            AlternativeFormatInputPart afiPart = new AlternativeFormatInputPart(
                    new PartName("/part" + chunkId + ".docx"));
            // afiPart.setContentType(new ContentType(CONTENT_TYPE));
            afiPart.setBinaryData(bytes);
            Relationship altChunkRel = main.addTargetPart(afiPart);

            CTAltChunk chunk = Context.getWmlObjectFactory().createCTAltChunk();
            chunk.setId(altChunkRel.getId());

            main.addObject(chunk);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * wordML转word，原文件不变，返回转换完成的word文件对象。
     *
     * @param file
     * @return
     * @throws Docx4JException
     * @throws IOException
     */
    public static File wordMLToWord(File file) throws Docx4JException, IOException {
        WordprocessingMLPackage target = WordprocessingMLPackage.load(file);
        File temp = File.createTempFile(file.getName(), ".doc");
        target.save(temp);
        return temp;
    }

    /**
     * 合并wordML文档
     *
     * @param list
     * @param path
     * @throws Docx4JException
     * @throws IOException
     */
    public static void mergeWordML(List<File> list, String path) throws Docx4JException, IOException {
        final List<InputStream> streams = new ArrayList<InputStream>();
        for (int i = 0; i < list.size(); i++) {
            File file = list.get(i);
            file = DocUtil.wordMLToWord(file); // wordML转word
            streams.add(new FileInputStream(file));
        }
        DocUtil.mergeDocx(streams, path);
    }

    /**
     * 把文件转换成Byte[]
     * Mapped File way MappedByteBuffer 可以在处理大文件时，提升性能
     *
     * @param filename
     * @return
     * @throws IOException
     */
    public static byte[] fileToByteArray(String filename) throws IOException {

        RandomAccessFile raf = null;
        FileChannel fc = null;
        try {
            raf = new RandomAccessFile(filename, "r");
            fc = raf.getChannel();
            MappedByteBuffer byteBuffer = fc.map(MapMode.READ_ONLY, 0,
                    fc.size()).load();
            System.out.println(byteBuffer.isLoaded());
            byte[] result = new byte[(int) fc.size()];
            if (byteBuffer.remaining() > 0) {
                byteBuffer.get(result, 0, byteBuffer.remaining());
            }
            return result;
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        } finally {
            try {
                fc.close();
                raf.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Docx4j拥有一个由字节数组创建图片部件的工具方法, 随后将其添加到给定的包中. 为了能将图片添加
     * 到一个段落中, 我们需要将图片转换成内联对象. 这也有一个方法, 方法需要文件名提示, 替换文本,
     * 两个id标识符和一个是嵌入还是链接到的指示作为参数.
     * 一个id用于文档中绘图对象不可见的属性, 另一个id用于图片本身不可见的绘制属性. 最后我们将内联
     * 对象添加到段落中并将段落添加到包的主文档部件.
     *
     * @param word      需要编辑的文件
     * @param imageList 图片对象集合（
     *                  图片对象属性：
     *                  url            图片文件路径
     *                  keyword     文档中的图片占位符
     *                  name        图片文件名
     *                  ）
     * @throws Exception 不幸的createImageInline方法抛出一个异常(没有更多具体的异常类型)
     */
    public static void addImageToPackage(File word,
                                         List<JSONObject> imageList) throws Exception {

        WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.load(word);

        for (int i = 0; i < imageList.size(); i++) {
            JSONObject image = imageList.get(i);

            byte[] bytes = fileToByteArray(image.getString("url"));

            BinaryPartAbstractImage imagePart = BinaryPartAbstractImage.
                    createImagePart(wordMLPackage, bytes);

            int docPrId = 1;
            int cNvPrId = 2;
            Inline inline = imagePart.createImageInline(image.getString("name"),
                    image.getString("keyword"), docPrId, cNvPrId, false);

            P paragraph = addInlineImageToParagraph(inline);

            wordMLPackage.getMainDocumentPart().addObject(paragraph);
        }

        wordMLPackage.save(word);
    }

    /**
     * Docx4j拥有一个由字节数组创建图片部件的工具方法, 随后将其添加到给定的包中. 为了能将图片添加
     * 到一个段落中, 我们需要将图片转换成内联对象. 这也有一个方法, 方法需要文件名提示, 替换文本,
     * 两个id标识符和一个是嵌入还是链接到的指示作为参数.
     * 一个id用于文档中绘图对象不可见的属性, 另一个id用于图片本身不可见的绘制属性. 最后我们将内联
     * 对象添加到段落中并将段落添加到包的主文档部件.
     *
     * @param wordFilePath 文件路径
     * @param imageList    图片对象集合（
     *                     图片对象属性：
     *                     url            图片文件路径
     *                     keyword     文档中的图片占位符
     *                     name        图片文件名
     *                     ）
     * @throws Exception 不幸的createImageInline方法抛出一个异常(没有更多具体的异常类型)
     */
    public static void addImageToPackage(String wordFilePath,
                                         List<JSONObject> imageList) throws Exception {
        addImageToPackage(new File(wordFilePath), imageList);
    }

    /**
     * 创建一个对象工厂并用它创建一个段落和一个可运行块R.
     * 然后将可运行块添加到段落中. 接下来创建一个图画并将其添加到可运行块R中. 最后我们将内联
     * 对象添加到图画中并返回段落对象.
     *
     * @param inline 包含图片的内联对象.
     * @return 包含图片的段落
     */
    private static P addInlineImageToParagraph(Inline inline) {
        // 添加内联对象到一个段落中
        P paragraph = factory.createP();
        R run = factory.createR();
        paragraph.getContent().add(run);
        Drawing drawing = factory.createDrawing();
        run.getContent().add(drawing);
        drawing.getAnchorOrInline().add(inline);
        return paragraph;
    }

    /**
     * 文档结尾添加一个空白页
     *
     * @throws Docx4JException
     */
    public static void addPageBreak(File word) throws Docx4JException {

        WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.load(word);

        MainDocumentPart documentPart = wordMLPackage.getMainDocumentPart();

        Br breakObj = new Br();
        breakObj.setType(STBrType.PAGE);

        P paragraph = factory.createP();
        paragraph.getContent().add(breakObj);
        documentPart.getJaxbElement().getBody().getContent().add(paragraph);
        wordMLPackage.save(word);
    }

    /**
     * 文档结尾添加一个空白页
     *
     * @throws Docx4JException
     */
    public static void addPageBreak(MainDocumentPart documentPart) {
        Br breakObj = new Br();
        breakObj.setType(STBrType.PAGE);

        P paragraph = factory.createP();
        paragraph.getContent().add(breakObj);
        documentPart.getJaxbElement().getBody().getContent().add(paragraph);
    }

    /**
     * 文档结尾添加一个空白页
     *
     * @throws Docx4JException
     */
    public static void addPageBreak(String wordFilePath) throws Docx4JException {
        addPageBreak(new File(wordFilePath));
    }


    public static void main(String[] args) throws Exception {
        File file1 = new File("F:\\新私有化\\导出\\a2.docx");
        File file2 = new File("F:\\新私有化\\导出\\信封模板.docx");
        List<InputStream> list = Lists.newArrayList();
        for (int i = 0; i < 100; i++) {
            if (i%2==0){
                list.add(new FileInputStream(file1));
            }else {
                list.add(new FileInputStream(file2));
            }
        }
        String path = "F:\\新私有化\\导出\\z.docx";
        //DocUtil.mergeWordML(files, path);
        long start = System.currentTimeMillis();
        byte[] bytes = mergeDocx(list);
        System.out.println("end 花费时间："+(System.currentTimeMillis()-start));
        //byte[] bytes = DocUtil.mergeDocx(list);
        FileUtil.uploadFile(bytes,"F:\\新私有化\\导出\\","zz.docx");
    }
}
