package com.zhilingsd.base.common.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.*;

/**
 * @program: 智灵时代广州研发中心
 * @description:
 * @author: 小蜘蛛(mazhonghao)
 * @create: 2019-07-13 19:41
 **/
@Slf4j
public class FileHelper {

    public static void downloader(String dirPath, String fileName, byte[] content) {
        BufferedInputStream bis = null;
        FileOutputStream fos = null;
        BufferedOutputStream output = null;
        String filePath = dirPath + fileName;
        try {
            File dir = new File(dirPath);
            dir.mkdirs();
            ByteArrayInputStream byteInputStream = new ByteArrayInputStream(content);
            bis = new BufferedInputStream(byteInputStream);
            File file = new File(filePath);
            fos = new FileOutputStream(file);
            // 实例化OutputString 对象
            output = new BufferedOutputStream(fos);
            byte[] buffer = new byte[1024];
            int length = bis.read(buffer);
            while (length != -1) {
                output.write(buffer, 0, length);
                length = bis.read(buffer);
            }
            output.flush();
        } catch (Exception e) {
            log.error("输出文件流时抛异常，filePath={}", content, e);
        } finally {
            try {
                bis.close();
                fos.close();
                output.close();
            } catch (IOException e0) {
                log.error("文件处理失败，filePath={}", filePath, e0);
            }
        }
    }

    public static void cleaner(String filePath) {
        try {
            File file = new File(filePath);
            if (file.isDirectory()) {
                dirCleaner(filePath);
            } else {
                file.delete();
            }
        } catch (Exception e) {
            log.error("删除文件失败，原因是->{}", e);
        }
    }

    private static void dirCleaner(String dirPath) {
        File dir = new File(dirPath);
        File[] files = dir.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                dirCleaner(file.getPath());
            } else {
                file.delete();
            }
        }
        dir.delete();
    }
}
