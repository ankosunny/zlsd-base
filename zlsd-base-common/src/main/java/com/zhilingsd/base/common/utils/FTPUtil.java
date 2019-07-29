package com.zhilingsd.base.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @program: 智灵时代广州研发中心
 * @description:
 * @author: 小蜘蛛(mazhonghao)
 * @create: 2019-07-12 10:26
 **/
@Slf4j
public class FTPUtil {

    public static boolean upLoadFromProduction(String dirFilePath, String ftpFilePath, String ftpFileName, String url, String port, String username, String password) {
        try {
            FileInputStream in = new FileInputStream(new File(dirFilePath));
            int ftpPort = Integer.parseInt(port);
            boolean flag = uploadFile(url, ftpPort, username, password, ftpFilePath, ftpFileName, in);
            log.info("上传结果->{}", flag);
            return flag;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private static boolean uploadFile(String url,// FTP服务器hostname
                                     int port,// FTP服务器端口
                                     String username, // FTP登录账号
                                     String password, // FTP登录密码
                                     String path, // FTP服务器保存目录
                                     String filename, // 上传到FTP服务器上的文件名
                                     InputStream input // 输入流
    ) {
        boolean success = false;
        FTPClient ftp = new FTPClient();
        ftp.setControlEncoding("GBK");
        try {
            int reply;
            ftp.connect(url, port);// 连接FTP服务器
            // 如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器
            ftp.login(username, password);// 登录
            reply = ftp.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftp.disconnect();
                return success;
            }
            ftp.enterLocalPassiveMode();
            ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
            ftp.makeDirectory(path);
            ftp.changeWorkingDirectory(path);
            ftp.storeFile(filename, input);
            input.close();
            ftp.logout();
            success = true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ftp.isConnected()) {
                try {
                    ftp.disconnect();
                } catch (IOException ioe) {
                    log.error("断开连接出错->{}",ioe);
                }
            }
        }
        return success;
    }
}
