package com.zhilingsd.base.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

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

    public static boolean upload(byte[] content, String ftpFilePath, String ftpFileName, String url, String port, String username, String password) {
        try {
            InputStream in = new ByteArrayInputStream(content);
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
            // 连接FTP服务器
            ftp.connect(url, port);
            // 如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器
            ftp.login(username, password);
            reply = ftp.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftp.disconnect();
                return success;
            }
            ftp.enterLocalPassiveMode();
            ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
            //如果没有目录，需要一级一级创建目录
            if (!ftp.changeWorkingDirectory(path)){
                String[] arr = path.split("/");
                //循环生成子目录
                for (String s : arr) {
                    //尝试切入目录
                    if (ftp.changeWorkingDirectory(s)){
                        continue;
                    }else{
                        ftp.makeDirectory(s);
                        ftp.changeWorkingDirectory(s);
                    }
                }
            }
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


//    public static boolean downloadFile(String url, int port, String username, String password,
//                                       String filePath, String fileName, OutputStream out) throws IOException {
//        FTPClient ftpClient = new FTPClient();
//        ftpClient.setControlEncoding("GBK");
//        try {
//            ftpClient.connect(url, port);
//            ftpClient.login(username, password);
//            ftpClient.enterLocalPassiveMode();  // 设置被动模式，开通一个端口来传输数据
//            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
//            // 判断是否存在该目录
//            if (!ftpClient.changeWorkingDirectory(filePath)) {
//                log.error("ftp下载文件失败, 无法进入目录:[{}]", filePath);
//                return false;
//            }
//            // 判断该目录下是否有文件
//            String[] fs = ftpClient.listNames(fileName);
//            for (String ff : fs) {
//                ftpClient.retrieveFile(ff, out);
//            }
//            return true;
//        } finally {
//            ftpClient.logout();
//            if (ftpClient.isConnected()) {
//                try {
//                    ftpClient.disconnect();
//                } catch (IOException ioe) {
//                    log.error("断开连接出错", ioe);
//                }
//            }
//        }
//    }

    /**
     * 从ftp下载单个文件到指定outStream
     *
     * @param url ftp地址
     * @param port ftp端口
     * @param username ftp用户名
     * @param password ftp密码
     * @param filePath 文件地址
     * @return 是否成功下载
     * @throws IOException IOException
     */
    public static byte[] downloadFile(String url, int port, String username, String password,
                                       String filePath) throws IOException {
        FTPClient ftpClient = new FTPClient();
        ftpClient.setControlEncoding("GBK");
        try {
            ftpClient.connect(url, port);
            ftpClient.login(username, password);
            ftpClient.enterLocalPassiveMode();  // 设置被动模式，开通一个端口来传输数据
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            // 判断是否存在该目录
//            if (!ftpClient.changeWorkingDirectory(filePath)) {
//                log.error("ftp下载文件失败, 无法进入目录:[{}]", filePath);
//                return null;
//            }
            // 判断该目录下是否有文件
//            String[] fs = ftpClient.listNames(fileName);
//            if (fs.length>0){
//                InputStream inputStream = ftpClient.retrieveFileStream(fs[0]);
//            }else {
//                return null;
//            }
            InputStream input = ftpClient.retrieveFileStream(filePath);
            byte[] bytes = new byte[input.available()];
            input.read(bytes);
            return bytes;
        }catch (Exception ex){
            log.error("下载失败", ex);
            return null;
        }
        finally {
            ftpClient.logout();
            if (ftpClient.isConnected()) {
                try {
                    ftpClient.disconnect();
                } catch (IOException ioe) {
                    log.error("断开连接出错", ioe);
                }
            }
        }
    }
}
