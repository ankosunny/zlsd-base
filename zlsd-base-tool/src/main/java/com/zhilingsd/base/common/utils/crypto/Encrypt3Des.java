package com.zhilingsd.base.common.utils.crypto;


import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Encrypt3Des {
	
	private static Logger logger = LoggerFactory.getLogger(Encrypt3Des.class);

	/**
	 * 把源串加密
	 * 
	 * @param src
	 *            源串
	 * @return 加了密
	 */
	public static byte[] encryptEncode(byte[] src, String key) {
		Encryptor encryptor;
		byte[] encr;
		// modified begin 2005 Mar 8

		try {
			String tempKey = new String(key.getBytes());
			encryptor = new Encryptor(tempKey.getBytes("UTF-8"));

		} catch (Exception ex) {
			encryptor = new Encryptor(key.getBytes());
		}
		try {
			encr = encryptor.encrypt(src);
		} catch (Exception ex) {
			encr = encryptor.encrypt(src);
		}

		return encr;
	}

	/**
	 * 把源串解密
	 * 
	 * @param src
	 *            源串
	 * @return 解了密
	 */
	public static byte[] decryptEncode(byte[] src, String key) {
		Encryptor encryptor;
		try {
			String tempKey = new String(key.getBytes());
			encryptor = new Encryptor(tempKey.getBytes("UTF-8"));
		} catch (Exception ex) {
			encryptor = new Encryptor(key.getBytes());
		}
		// byte[] encr = Base64.decode(src);
		try {
			src = encryptor.decrypt(src);
		} catch (Exception ex) {
			src = encryptor.decrypt(src);
		}
		return src;
	}

	/**
	 * 函数功能: 对数据3DES加密 输入参数:
	 * 
	 * @param src
	 *            待加密的数据
	 * @param key1
	 *            第一个密钥
	 * @param key2
	 *            第二个密钥
	 * @param key3
	 *            第三个密钥
	 * @return 输出参数: 返 回 值:String
	 */
	public static String encrypt3DesEncode(String src, String key1,
                                           String key2, String key3) {
		if (src == null || src.length() <= 0 || key1 == null
				|| key1.length() < 1 || key2 == null || key2.length() < 1
				|| key3 == null || key3.length() < 1) {
			new Exception("3Des encrypt is less of key length 3");
		}
		byte[] srcbt = null;
		try {
			srcbt = src.getBytes("UTF-8");
		} catch (Exception e) {
			logger.error("", e);
		}
		if (key1.length() < 8) {
			StringBuffer sb = new StringBuffer();
			int iSize = 8 - key1.length();

			for (int i = 0; i < iSize; i++) {
				sb.append("0");
			}
				
			key1 = key1 + sb.toString();
		}
		if (key2.length() < 8) {
			StringBuffer sb = new StringBuffer();
			int iSize = 8 - key2.length();
			for (int i = 0; i < iSize; i++) {
				sb.append("0");
			}
				
			key2 = key2 + sb.toString();
		}
		if (key3.length() < 8) {
			StringBuffer sb = new StringBuffer();
			int iSize = 8 - key3.length();
			for (int i = 0; i < iSize; i++) {
				sb.append("0");
			}
				
			key3 = key3 + sb.toString();
		}
		srcbt = encryptEncode(srcbt, key1);
		srcbt = encryptEncode(srcbt, key2);
		srcbt = encryptEncode(srcbt, key3);
		byte[] base64 = Base64.encodeBase64(srcbt);
//		byte[] base64 = Base64.encode(srcbt);

		// //直接进行base64编码，不进行加密解密
		// byte[] srcbt=src.getBytes();
		// byte[] base64 = Base64.encode(srcbt);

		return new String(base64);

	}

	/**
	 * 函数功能: 对数据3DES解密 输入参数:
	 * 
	 * @param src
	 *            待解密的数据
	 * @param key1
	 *            第一个密钥
	 * @param key2
	 *            第二个密钥
	 * @param key3
	 *            第三个密钥
	 * @return 输出参数:f 返 回 值:String
	 */
	public static String decrypt3DesEncode(String src, String key1,
                                           String key2, String key3) {
		if (key1 == null || key1.length() < 1 || key2 == null
				|| key2.length() < 1 || key3 == null || key3.length() < 1) {
			new Exception("3Des decrypt is less of key length 3");
		}
		if (key1.length() < 8) {
			StringBuffer sb = new StringBuffer();
			int iSize = 8 - key1.length();
			for (int i = 0; i < iSize; i++) {
				sb.append("0");
			}
			key1 = key1 + sb.toString();
		}
		if (key2.length() < 8) {
			StringBuffer sb = new StringBuffer();
			int iSize = 8 - key2.length();
			for (int i = 0; i < iSize; i++) {
				sb.append("0");
			}
				
			key2 = key2 + sb.toString();
		}
		if (key3.length() < 8) {
			StringBuffer sb = new StringBuffer();
			int iSize = 8 - key3.length();

			for (int i = 0; i < iSize; i++) {
				sb.append("0");
			}
				
			key3 = key3 + sb.toString();
		}
		byte[] encr = Base64.decodeBase64(src.getBytes());
//		byte[] encr = Base64.decode(src);
		encr = decryptEncode(encr, key3);
		encr = decryptEncode(encr, key2);
		encr = decryptEncode(encr, key1);
		String result = null;
		try {
			result = new String(encr, "utf-8");
		} catch (Exception e) {
			logger.error("", e);
		}
		return result;
	}

	/**
	 * 字节数组转换成16进制字符串
	 * 
	 * @param b
	 * @return
	 */
	public static String byte2hex(byte[] b) {// 二行制转字符串
		String hs = "";
		String stmp = "";
		int nSize = b.length;
		for (int n = 0; n < nSize; n++) {
			stmp = byteHEX(b[n]);
			hs = hs + stmp;
		}
		return hs;
	}

	private static String byteHEX(byte ib) {
		char[] digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A',
				'B', 'C', 'D', 'E', 'F' };
		char[] ob = new char[2];
		ob[0] = digit[(ib >>> 4) & 0X0F];
		ob[1] = digit[ib & 0X0F];
		String s = new String(ob);
		return s;
	}

	/**
	 * 16进制字符串转换成字节数组
	 * 
	 * @param hex
	 * @return
	 */
	public static byte[] hex2byte(String hex) {
		int len = (hex.length() / 2);
		byte[] result = new byte[len];
		char[] achar = hex.toCharArray();
		for (int i = 0; i < len; i++) {
			int pos = i * 2;
			result[i] = (byte) (toByte(achar[pos]) << 4 | toByte(achar[pos + 1]));
		}
		return result;
	}

	private static byte toByte(char c) {
		byte b = (byte) "0123456789ABCDEF".indexOf(c);
		return b;
	}

	/**
	 * 函数功能: 对数据3DES加密并转成16进制字符串 输入参数:
	 * 
	 * @param src
	 *            待加密的数据
	 * @param key1
	 *            第一个密钥
	 * 
	 * @param key2
	 *            第二个密钥
	 * 
	 * @param key3
	 *            第三个密钥
	 * 
	 * @return 输出参数: 返 回 值:String
	 */
	public static String encrypt3DesEncodeHex(String src, String key1,
                                              String key2, String key3) {
		if (src == null || "".equals(src)) {
			return "";
		}
		return byte2hex(Encrypt3Des.encrypt3DesEncode(src, key1, key2, key3)
				.getBytes());
	}

	/**
	 * 函数功能: 对转成16进制字符串的密文数据3DES解密 输入参数:
	 * 
	 * @param src
	 *            待解密的数据
	 * @param key1
	 *            第一个密钥
	 * 
	 * @param key2
	 *            第二个密钥
	 * 
	 * @param key3
	 *            第三个密钥
	 * 
	 * @return 输出参数: 返 回 值:String
	 */
	public static String decrypt3DesEncodeHex(String src, String key1,
                                              String key2, String key3) {
		if (src == null || "".equals(src)) {
			return "";
		}
		return Encrypt3Des.decrypt3DesEncode(new String(hex2byte(src)), key1,
				key2, key3);
	}

	public static String byte2hex1(byte[] b) {
		String hs = "";

		String stmp = "";
		int nSize = b.length;
		for (int n = 0; n < nSize; n++) {
			stmp = (Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1) {
				hs = hs + "0" + stmp;
			} else {
				hs = hs + stmp;
			}
		}
		return hs.toUpperCase();
	}

}