package com.zhilingsd.base.common.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
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
            if (files.length == 0) {
                out.putNextEntry(new ZipEntry(base+"/"));
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
}
