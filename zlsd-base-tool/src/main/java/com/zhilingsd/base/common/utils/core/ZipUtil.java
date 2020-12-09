package com.zhilingsd.base.common.utils.core;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

/**
 * @program: 智灵时代广州研发中心
 * @description:
 * @author: 小蜘蛛(mazhonghao)
 * @create: 2019-07-11 18:17
 **/
@Slf4j
public class ZipUtil {

    public static void zip(String zipFileName, File inputFile) throws IOException {
        ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipFileName));
        doZip(out, inputFile, "");
        out.close();
    }

    private static void doZip(ZipOutputStream out, File file, String base) throws IOException {
        if (file.isDirectory()) {
            //判断此路径是否是一个目录
            File[] files = file.listFiles();
            if (files == null) {
                return;
            }

            if (files.length == 0) {
                out.putNextEntry(new ZipEntry(base + "/"));
                out.closeEntry();
            }

            //获取路径数组
            for (int i = 0; i < files.length; i++) {
                //循环遍历数组中的文件
                System.out.println(base);
                doZip(out, files[i], files[i].getParentFile().getName() + "/" + files[i].getName());
            }
        } else {
            out.putNextEntry(new ZipEntry(base));
            //创建新的进入点
            //创建FileInputStream对象
            FileInputStream in = new FileInputStream(file);
            int b;
            while ((b = in.read()) != -1) {
                //如果没有达到流的尾部
                //将字节写入当前ZIP条目
                out.write(b);
            }
            in.close();//关闭流
        }
    }

    /**
     * 解压文件
     * @param srcFile
     * @param destDirPath
     */
    public static void unZip(File srcFile, String destDirPath) {
        long start = System.currentTimeMillis();

        // 判断源文件是否存在
        if (!srcFile.exists()) {
            throw new RuntimeException(srcFile.getPath() + "所指文件不存在");
        }

        // 开始解压
        ZipFile zipFile = null;
        try {
            zipFile = new ZipFile(srcFile);
            Enumeration<?> entries = zipFile.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = (ZipEntry) entries.nextElement();
                log.debug("解压{}", entry.getName());

                // 如果是文件夹，就创建个文件夹
                if (entry.isDirectory()) {
                    String dirPath = destDirPath + "/" + entry.getName();
                    File dir = new File(dirPath);
                    dir.mkdirs();
                } else {
                    // 如果是文件，就先创建一个文件，然后用io流把内容copy过去
                    File targetFile = new File(destDirPath + "/" + entry.getName());
                    // 保证这个文件的父文件夹必须要存在
                    if (!targetFile.getParentFile().exists()) {
                        targetFile.getParentFile().mkdirs();
                    }
                    targetFile.createNewFile();

                    // 将压缩文件内容写入到这个文件中
                    InputStream is = zipFile.getInputStream(entry);
                    FileOutputStream fos = new FileOutputStream(targetFile);
                    IOUtils.copy(is, fos);
                }
            }
            long end = System.currentTimeMillis();
            log.debug("解压完成，耗时{}ms", end - start);
        } catch (Exception e) {
            throw new RuntimeException("unzip error from ZipUtils", e);
        } finally {
            if (zipFile != null) {
                try {
                    zipFile.close();
                } catch (IOException e) {
                    log.warn("文件关闭异常", e);
                }
            }
        }
    }
}
