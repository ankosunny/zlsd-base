package com.zhilingsd.base.common.utils;

import com.zhilingsd.base.common.bean.ZipFilleStream;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.*;
import java.util.zip.CRC32;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;

/**
 * Created by liyang on 2018/3/14
 */
@Slf4j
public class FileUtil {

    public static final long MAX_SIZE = 10485760L;
    public static final String DEPRECIATIO_PATH = "depreciation/";
    public static final String CASEBACK_PATH = "caseback/";
    public static final String VISIT_PATH = "visit_record/";
    public static final String COLLECTION_APPLY_TEMPLATE_PATH = "collection_apply_template/";
    public static final String RESPONSE_VOICE_PATH = "response_voice/";

    public static final String IMG_PATH = "img_path/";

    public static final String NOTICE_ACCESSORY_PATH_SPONSOR = "notice/sponsor";
    public static final String NOTICE_ACCESSORY_PATH_CONSIGNER = "notice/consigner";
    public static final String NOTICE_ACCESSORY_PATH_ASSIGNEE = "notice/assignee";
    public static final String FILE_SAVE_FORSUBNAME = "_";
    public static final String FILE_TYPE_IMAGE_KEY = "image";
    public static final String FILE_TYPE_PACKAGE_KEY = "package";
    public static final String FILE_TYPE_OFFICE_KEY = "office";

    public static final String WORK_ORDER_PATH = "work_order_path/";
    public static final String WORK_ORDER_FEEDBAK = "assignee/importWorkOrder/feedback/";

    //移动端apk上传路径
    public static final String APP_APK_UPLOAD_PATH = "sponsor/apk/";
    /**
     * 定义文件支持所有类型
     */
    private static Map<String, List<String>> fileTypes = new HashMap<String, List<String>>();

    static {
        //image
        fileTypes.put(FILE_TYPE_IMAGE_KEY, Arrays.asList(".jpg", ".png", ".jepg", ".bmp"));
        //压缩包
        fileTypes.put(FILE_TYPE_PACKAGE_KEY, Arrays.asList(".rar", ".zip"));
        //办公文件
        fileTypes.put(FILE_TYPE_OFFICE_KEY, Arrays.asList(".doc", ".docx", ".xls", ".xlsx"));
    }

    /**
     * 递归删除目录下的所有文件及子目录下所有文件
     *
     * @param dir 将要删除的文件目录
     */
    public static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            //递归删除目录中的子目录下
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        // 目录此时为空，可以删除
        return dir.delete();
    }

    /**
     * 获取文件支持类型map
     *
     * @return
     */
    public static Map<String, List<String>> getFileTypes() {
        return fileTypes;
    }

    /**
     * 检测文件是否为空
     *
     * @param file
     * @return
     */
    public static boolean isEmptyFile(MultipartFile file) {
        return file == null || file.isEmpty() || file.getOriginalFilename() == null
                || StringUtils.isBlank(file.getOriginalFilename()) || file.getSize() == 0;
    }

    /**
     * 判断文件列表中是否有空
     *
     * @param list
     * @return
     */
    public static boolean hasEmptyFile(List<MultipartFile> list) {
        if (list == null || list.isEmpty()) {
            return false;
        }
        for (MultipartFile file : list) {
            if (isEmptyFile(file)) {
                return true;
            }
        }
        return false;
    }

    public static boolean checkImageType(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        fileName = fileName.substring(fileName.lastIndexOf(".")).toLowerCase();
        return fileTypes.get(FILE_TYPE_IMAGE_KEY).contains(fileName);
    }

    public static boolean checkPackageType(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        fileName = fileName.substring(fileName.lastIndexOf(".")).toLowerCase();
        return fileTypes.get(FILE_TYPE_PACKAGE_KEY).contains(fileName);
    }

    public static boolean checkOfficeType(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        fileName = fileName.substring(fileName.lastIndexOf(".")).toLowerCase();
        return fileTypes.get(FILE_TYPE_OFFICE_KEY).contains(fileName);
    }

    /**
     * 检测文件类型是否是支持上传的类型
     *
     * @param file
     * @return
     */
    public static boolean checkType(MultipartFile file) {
        if (checkImageType(file)) {
            return true;
        } else if (checkPackageType(file)) {
            return true;
        } else {
            return checkOfficeType(file);
        }
    }

    public static boolean checkTypeList(List<MultipartFile> list) {
        if (list == null || list.isEmpty()) {
            return false;
        }
        for (MultipartFile file : list) {
            if (!checkType(file)) {
                return false;
            }
        }
        return true;
    }

    public static boolean checkTypeList(MultipartFile[] files) {
        if (files == null || files.length == 0) {
            return false;
        }
        for (int i = 0; i < files.length; i++) {
            if (!checkType(files[i])) {
                return false;
            }
        }
        return true;
    }

    public static File uploadAndReturnFile(MultipartFile file, String rootPath, String path) throws Exception {
        if (file == null) {
            return null;
        }
        String fileName = UUID.randomUUID().toString() + FILE_SAVE_FORSUBNAME + file.getOriginalFilename();
        String filePath = rootPath + path;
        File localFile = new File(filePath);
        if (!localFile.exists()) {
            localFile.mkdirs();
        }
        File up = new File(localFile + "/" + fileName);
        up.createNewFile();
        file.transferTo(up);
        return up;
    }

    public static List<File> uploadBatchAndReturnFiles(List<MultipartFile> list, String rootPath, String path) throws Exception {
        List<File> files = new ArrayList<File>();
        if (list == null || list.isEmpty()) {
            return files;
        }
        File f = null;
        for (MultipartFile file : list) {
            f = uploadAndReturnFile(file, rootPath, path);
            files.add(f);
        }
        return files;
    }

    public static void uploadFile(byte[] file, String filePath, String fileName) throws Exception {

        File targetFile = new File(filePath);
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        try (FileOutputStream out = new FileOutputStream(filePath + fileName)) {
            out.write(file);
            out.flush();
        } catch (Exception e) {
            log.error("写文件异常：", e);
            throw e;
        }

    }

    public static boolean deleteFile(String fileName) {
        File file = new File(fileName);
        // 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
        if (file.exists() && file.isFile()) {
            return file.delete();
        } else {
            return false;
        }
    }

    public static String renameToUUID(String fileName) {
        return UUID.randomUUID() + "." + fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    /**
     * 判断文件是否超出规定大小
     */
    public static boolean checkFileSize(MultipartFile[] uFile) {
        long uFileSize = 0;
        for (MultipartFile file : uFile) {
            uFileSize = uFileSize + file.getSize();
        }
        return uFileSize > MAX_SIZE;
    }

    public static boolean checkFileSize(MultipartFile file) {
        long uFileSize = file.getSize();
        return uFileSize > MAX_SIZE;
    }


    public static File uploadStandardAndReturnFile(MultipartFile file, String rootPath, String path, String fileName) throws Exception {
        if (file == null) {
            return null;
        }
        String filePath = rootPath + path;
        File localFile = new File(filePath);
        if (!localFile.exists()) {
            localFile.mkdirs();
        }
        File up = new File(localFile + "/" + fileName);
        up.createNewFile();
        file.transferTo(up);
        return up;
    }

    public static ResponseEntity<byte[]> zipByte(List<ZipFilleStream> streams, String zipName) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ZipOutputStream zout = new ZipOutputStream(out);
        zout.setEncoding("GBK");

        InputStream in = null;
        try {
            byte[] buf = new byte[1024];
            int len;
            for (ZipFilleStream stream : streams) {
                in = new ByteArrayInputStream(stream.getContent());
                zout.putNextEntry(new ZipEntry(stream.getFileName()));
                while ((len = in.read(buf)) > 0) {
                    zout.write(buf, 0, len);
                }
                zout.closeEntry();
            }
            zout.close();
            return SpringWebFileUtil.download(out.toByteArray(), zipName);
        } finally {
            if (zout != null) {
                zout.close();
            }
            if (in != null) {
                in.close();
            }
            if (out != null) {
                out.close();
            }
        }
    }

    public static ResponseEntity<byte[]> zipFile(List<String> fileUrls, String zipName) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ZipOutputStream zout = new ZipOutputStream(out);
        zout.setEncoding("GBK");

        FileInputStream in = null;
        try {
            byte[] buf = new byte[1024];
            int len;
            for (String url : fileUrls) {
                File file = new File(url);
                String fileName = file.getName();
                in = new FileInputStream(file);
                zout.putNextEntry(new ZipEntry(fileName));
                while ((len = in.read(buf)) > 0) {
                    zout.write(buf, 0, len);
                }
                zout.closeEntry();
            }
            zout.close();
            return SpringWebFileUtil.download(out.toByteArray(), zipName);
        } finally {
            if (zout != null) {
                zout.close();
            }
            if (in != null) {
                in.close();
            }
            if (out != null) {
                out.close();
            }
        }
    }

    public static byte[] fileToBytes(String filePath) {
        byte[] buffer = null;
        File file = new File(filePath);

        FileInputStream fis = null;
        ByteArrayOutputStream bos = null;

        try {
            fis = new FileInputStream(file);
            bos = new ByteArrayOutputStream();

            byte[] b = new byte[1024];

            int n;

            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }

            buffer = bos.toByteArray();
        } catch (FileNotFoundException ex) {
            log.error("fileToBytes -> FileNotFoundException", ex);
        } catch (IOException ex) {
            log.error("fileToBytes -> IOException", ex);
        } finally {
            try {
                if (null != bos) {
                    bos.close();
                }
            } catch (IOException ex) {
                log.error("fileToBytes -> IOException", ex);
            } finally {
                try {
                    if (null != fis) {
                        fis.close();
                    }
                } catch (IOException ex) {
                    log.error("fileToBytes -> IOException", ex);
                }
            }
        }

        return buffer;
    }

    public static String getCrc32(byte[] content) {
        CRC32 crc32 = new CRC32();
        crc32.update(content, 0, content.length);
        return String.valueOf(crc32.getValue());
    }
}
