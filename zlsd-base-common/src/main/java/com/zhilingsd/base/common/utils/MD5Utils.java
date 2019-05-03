package com.zhilingsd.base.common.utils;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class MD5Utils {
    // 全局数组
    private final static String[] strDigits = {"0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};
    private static final char[] HEX_DIGITS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
    private static MessageDigest messageDigest = null;

    static {
        try {
            messageDigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

//	public static final String PASSWORD_SALT_DEFAULT = ResourceUtil
//			.getValue("PASSWORD_SALT_DEFAULT"); /* 密码盐 */

    // private static final Integer SALT_LENGTH =
    // PASSWORD_SALT_DEFAULT.length();

    // 返回形式为数字跟字符串
    private static String byteToArrayString(byte bByte) {
        int iRet = bByte;
        // System.out.println("iRet="+iRet);
        if (iRet < 0) {
            iRet += 256;
        }
        int iD1 = iRet / 16;
        int iD2 = iRet % 16;
        return strDigits[iD1] + strDigits[iD2];
    }

    // 转换字节数组为16进制字串
    private static String byteToString(byte[] bByte) {
        StringBuffer sBuffer = new StringBuffer();
        for (int i = 0; i < bByte.length; i++) {
            sBuffer.append(byteToArrayString(bByte[i]));
        }
        return sBuffer.toString();
    }

    public static String GetMD5Code(String strObj) {
        String resultString = null;
        try {
            resultString = strObj;
            MessageDigest md = MessageDigest.getInstance("MD5");
            // md.digest() 该函数返回值为存放哈希值结果的byte数组
            resultString = byteToString(md.digest(strObj.getBytes()));
        } catch (NoSuchAlgorithmException ex) {
        }
        return resultString;
    }

    /**
     * 获得加密后的16进制形式口令,默认的加盐方式为123456789012
     *
     * @param password
     * @return
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     */
//	public static String getEncryptedPwd(String password) {
//
//		try {
//			return getEncryptedPwd(PASSWORD_SALT_DEFAULT, password);
//		} catch (NoSuchAlgorithmException e) {
//			log.error("NoSuchAlgorithmException", e);
//		} catch (UnsupportedEncodingException e) {
//			log.error("UnsupportedEncodingException", e);
//		} catch (Exception e) {
//			log.error("未知异常：", e);
//		}
//		return password;
//	}
    public static String getEncryptedPwd(String saltText, String password)
            throws NoSuchAlgorithmException, UnsupportedEncodingException {
        // 声明加密后的口令数组变量
        byte[] pwd = null;
        // 声明盐数组变量
        String saltConst = saltText;
        int saltLength = saltConst.length();
        byte[] salt = new byte[saltLength];
        salt = saltConst.getBytes(StandardCharsets.UTF_8);

        // 声明消息摘要对象
        MessageDigest md = null;
        // 创建消息摘要
        md = MessageDigest.getInstance("SHA");
        // 将盐数据传入消息摘要对象
        md.update(salt);
        // 将口令的数据传给消息摘要对象
        md.update(password.getBytes(StandardCharsets.UTF_8));
        // 获得消息摘要的字节数组
        byte[] digest = md.digest();

        // 因为要在口令的字节数组中存放盐，所以加上盐的字节长度
        pwd = new byte[digest.length + saltLength];
        // 将盐的字节拷贝到生成的加密口令字节数组的前12个字节，以便在验证口令时取出盐
        System.arraycopy(salt, 0, pwd, 0, saltLength);
        // 将消息摘要拷贝到加密口令字节数组从第13个字节开始的字节
        System.arraycopy(digest, 0, pwd, saltLength, digest.length);
        // 将字节数组格式加密后的口令转化为16进制字符串格式的口令
        return byteToHexString(pwd);
    }

    /**
     * 将指定byte数组转换成16进制字符串
     *
     * @param b
     * @return
     */
    public static String byteToHexString(byte[] b) {
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            String hex = Integer.toHexString(b[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            hexString.append(hex.toUpperCase());
        }
        return hexString.toString();
    }

    /**
     * 字符串MD5签名
     * // @SuppressLint("DefaultLocale")
     *
     * @param str
     * @return
     */
    public static String getMD5String(String str) {
        return getMD5String(str.getBytes()).toLowerCase();
    }

    /**
     * 字节数组MD5签名
     *
     * @param bytes
     * @return
     */
    public static String getMD5String(byte[] bytes) {
        messageDigest.update(bytes);
        return bytesToHex(messageDigest.digest());
    }

    /**
     * @param bytes
     * @return
     */
    private static String bytesToHex(byte[] bytes) {
        return bytesToHex(bytes, 0, bytes.length);
    }

    /**
     * @param bytes
     * @param start
     * @param end
     * @return
     */
    private static String bytesToHex(byte[] bytes, int start, int end) {
        StringBuilder sb = new StringBuilder();

        for (int i = start; i < start + end; i++) {
            sb.append(byteToHex(bytes[i]));
        }

        return sb.toString();
    }

    /**
     * @param bt
     * @return
     */
    private static String byteToHex(byte bt) {
        return HEX_DIGITS[(bt & 0xf0) >> 4] + "" + HEX_DIGITS[bt & 0xf];
    }


}
