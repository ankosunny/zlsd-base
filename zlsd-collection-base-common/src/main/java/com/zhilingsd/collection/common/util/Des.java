package com.zhilingsd.collection.common.util;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.security.Key;

public class Des {

	/** UTF-8字符集 **/
	public static final String CHARSET_UTF8 = "UTF-8";

	/** GBK字符集 **/
	public static final String CHARSET_GBK = "GBK";

	/**
	 * 默认编码
	 */
	public static final String CHARSET = CHARSET_UTF8;

	/**
	 * ECB加密,不要IV
	 * 
	 * @param key
	 *            密钥
	 * @param data
	 *            明文
	 * @return
	 * @throws Exception
	 */
	public static byte[] des3EncodeECB(byte[] key, byte[] data)
			throws Exception {
		Key deskey = null;
		DESedeKeySpec spec = new DESedeKeySpec(key);
		SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
		deskey = keyfactory.generateSecret(spec);
		Cipher cipher = Cipher.getInstance("desede" + "/ECB/NoPadding");
		cipher.init(Cipher.ENCRYPT_MODE, deskey);
		byte[] bOut = cipher.doFinal(data);
		return bOut;
	}

	/**
	 * 返回base64 编码密文
	 * 
	 * @Title des3EncodeECB
	 * @Description TODO
	 * @param key
	 * @param data
	 * @return
	 * @throws Exception
	 *             byte[]
	 */
	public static String des3EncodeECB(String key, String data)
			throws Exception {
		for (int i = key.length(); i < 24; i++) {
			key += "0";
		}
		// 算出一个加密块差几个
		int paddingNum = 8 - (data.getBytes(CHARSET)).length % 8;
		if (paddingNum != 0||paddingNum!=8) {
			for (int i = 0; i < paddingNum; i++) {
				// 填充null
				data += "\0";
			}
		}

		Key deskey = null;
		DESedeKeySpec spec = new DESedeKeySpec(key.getBytes(CHARSET));
		SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
		deskey = keyfactory.generateSecret(spec);
		Cipher cipher = Cipher.getInstance("desede" + "/ECB/NoPadding");
		cipher.init(Cipher.ENCRYPT_MODE, deskey);
		byte[] bOut = cipher.doFinal(data.getBytes(CHARSET));
		return new String(Base64.encodeBase64(bOut), CHARSET);
	}

	/**
	 * ECB解密,不要IV
	 * 
	 * @param key
	 *            密钥
	 * @param data
	 * 
	 * @return 明文
	 * @throws Exception
	 */
	public static byte[] des3DecodeECB(byte[] key, byte[] data)
			throws Exception {
		Key deskey = null;
		DESedeKeySpec spec = new DESedeKeySpec(key);
		SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
		deskey = keyfactory.generateSecret(spec);
		Cipher cipher = Cipher.getInstance("desede" + "/ECB/NoPadding");
		cipher.init(Cipher.DECRYPT_MODE, deskey);
		byte[] bOut = cipher.doFinal(data);
		return bOut;
	}

	/**
	 * 
	 * @Title des3DecodeECB
	 * @Description TODO
	 * @param key
	 * @param data
	 *            Base64编码的密文
	 * @return
	 * @throws Exception
	 *             byte[]
	 */
	public static String des3DecodeECB(String key, String data)
			throws Exception {
		for (int i = key.length(); i < 24; i++) {
			key += "0";
		}
		Key deskey = null;
		DESedeKeySpec spec = new DESedeKeySpec(key.getBytes(CHARSET));
		SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
		deskey = keyfactory.generateSecret(spec);
		Cipher cipher = Cipher.getInstance("desede" + "/ECB/NoPadding");
		cipher.init(Cipher.DECRYPT_MODE, deskey);
		byte[] bOut = cipher
				.doFinal(Base64.decodeBase64(data.getBytes(CHARSET)));
		String outString = new String(bOut, CHARSET);

		// 去掉结尾的null字节
		int lastIndex = outString.length()-1;
		while (lastIndex > 0) {
			if (outString.charAt(lastIndex) == '\0') {
				lastIndex--;
			} else {// 从结尾开始找到非null字符跳出
				break;
			}
		}
		outString = outString.substring(0, lastIndex+1);
		return outString;
	}

	/**
	 * CBC加密
	 * 
	 * @param key
	 *            密钥
	 * @param keyiv
	 *            IV
	 * @param data
	 *            明文
	 * @return Base64编码的密文
	 * @throws Exception
	 */
	public static byte[] des3EncodeCBC(byte[] key, byte[] keyiv, byte[] data)
			throws Exception {
		Key deskey = null;
		DESedeKeySpec spec = new DESedeKeySpec(key);
		SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
		deskey = keyfactory.generateSecret(spec);
		Cipher cipher = Cipher.getInstance("desede" + "/CBC/PKCS5Padding");
		IvParameterSpec ips = new IvParameterSpec(keyiv);
		cipher.init(Cipher.ENCRYPT_MODE, deskey, ips);
		byte[] bOut = cipher.doFinal(data);
		return bOut;
	}

	/**
	 * Base64编码的密文
	 * 
	 * @Title des3EncodeCBC
	 * @Description TODO
	 * @param key
	 * @param keyiv
	 * @param data
	 * @return
	 * @throws Exception
	 *             byte[]
	 */
	public static String des3EncodeCBC(String key, String keyiv, String data)
			throws Exception {
		for (int i = key.length(); i < 24; i++) {
			key += "0";
		}
		if (StringUtils.isEmpty(keyiv)) {
			keyiv = "12345678";
		}
		Key deskey = null;
		DESedeKeySpec spec = new DESedeKeySpec(key.getBytes(CHARSET));
		SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
		deskey = keyfactory.generateSecret(spec);
		Cipher cipher = Cipher.getInstance("desede" + "/CBC/PKCS5Padding");
		IvParameterSpec ips = new IvParameterSpec(keyiv.getBytes(CHARSET));
		cipher.init(Cipher.ENCRYPT_MODE, deskey, ips);
		byte[] bOut = cipher.doFinal(data.getBytes(CHARSET));
		return new String(Base64.encodeBase64(bOut), CHARSET);
	}

	/**
	 * CBC解密
	 * 
	 * @param key
	 *            密钥
	 * @param keyiv
	 *            IV
	 * @param data
	 * 
	 * @return 明文
	 * @throws Exception
	 */
	public static byte[] des3DecodeCBC(byte[] key, byte[] keyiv, byte[] data)
			throws Exception {
		Key deskey = null;
		DESedeKeySpec spec = new DESedeKeySpec(key);
		SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
		deskey = keyfactory.generateSecret(spec);
		Cipher cipher = Cipher.getInstance("desede" + "/CBC/PKCS5Padding");
		IvParameterSpec ips = new IvParameterSpec(keyiv);
		cipher.init(Cipher.DECRYPT_MODE, deskey, ips);
		byte[] bOut = cipher.doFinal(data);
		return bOut;
	}

	/**
	 * 
	 * @Title des3DecodeCBC
	 * @Description TODO
	 * @param key
	 * @param keyiv
	 * @param data
	 *            Base64编码的密文
	 * @return
	 * @throws Exception
	 *             byte[]
	 */
	public static String des3DecodeCBC(String key, String keyiv, String data)
			throws Exception {
		Key deskey = null;
		for (int i = key.length(); i < 24; i++) {
			key += "0";
		}
		if (StringUtils.isEmpty(keyiv)) {
			keyiv = "12345678";
		}
		DESedeKeySpec spec = new DESedeKeySpec(key.getBytes(CHARSET));
		SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
		deskey = keyfactory.generateSecret(spec);
		Cipher cipher = Cipher.getInstance("desede" + "/CBC/PKCS5Padding");
		IvParameterSpec ips = new IvParameterSpec(keyiv.getBytes(CHARSET));
		cipher.init(Cipher.DECRYPT_MODE, deskey, ips);
		byte[] bOut = cipher
				.doFinal(Base64.decodeBase64(data.getBytes(CHARSET)));
		return new String(bOut, CHARSET);
	}

	public static void main(String[] args) throws Exception {
		// String content =
		// "L7RON+bdd5s9qPQk/6wrIRNncmap9cqvig93i+B57U+Jp3dhZ63IezrayXgY2MFXGpx7KiPJq8nqReurLRnrMfnYD98+J2LCtJy9C4WI3xZIdsvBjLfIq75RK7SH7FgwW4KxCW1Qd1QO6pTmaOY0VxrmUpKbh5GyvPP7bd0yizw=";
		// String privateKey =
		// "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAKAtmtxGez5901NLwNI5AkNzVyWFneul2E+J7N2gFGxT+THQyGQKYm916w7fOib+ZYNfrDH2xD2CibF1tGSq4iXDTBOCOELpho8RZrVnHRKlv9Z8JkDOIxGV7JgzCa5AQoPWZXb1iS/fwL7Gb3o1k0hxlVRLEJ4mEJHM/m992eOBAgMBAAECgYBKDku24+8/KTV3IWL4ztq5DK6R7oDg3kBwJbiZjtVicECaQ4kil5qDJeWTbqmYNa4BW05lm02Hkr7OiTjqAgej6pQzmGBu9DZNyOIK3Xr6Q83cpGzVFr9RP2l4uxB6KYdTZPizgzZg9Ijno6/0I6pwacLWSl2DjHJOF323NvF7QQJBANIHl1EXakA/FsnUt5tByn7sySXNupPiF+UqIQUCHCdBM795GME1y9Snjl98gm3LxGlUX1Yxl5A0z15R4ZTRmN0CQQDDPLpE5fr97QzifWDax0+Y0K7CLKUnZH6NA9MZRN16bLTutLyYerGC9HO3sI+6RfGPtkKbr9TJyUkoyW0w13j1AkEAySVmAM6o/oCtZXyonzOiKG7DscXW83xsIBh56R79MYcbdTwXviq0XKRgEQjgrSCiqbGZYKY8HmrFUvKeVXVqrQJADmaSTrav9r7fbm1VEY4V/u2Vllj4rZkqBHi9v2Vm+Lq/9cN7DbwvD7u8NVZeo5y5HqKQ8y7bAS9JqCqDVVDvaQJAdqcbypKkKPMVks+Xy24+msqT1+w7AJfxS+NmSJBrPeNwdsjoAPmJljXHJ5aJAAgyLMENNmi8Spb2bt2gQhfPZg==";
		// String keystr = RSA.rsaDecrypt(content, privateKey);
		// System.out.println(keystr);
		// keystr = keystr.substring(0, 24);
		// byte[] src =
		// "c1379584935b610bb4146a788e0efba88aa77dae65855ab1d9ae9fe6a96762ab29739d689ed494a16576de3ee195bd00debf99d9afe93c3ef89f4054097aeb597d19dae3a3d8c9190dec7825e8f3fc12d87eadfea59f2df937d6706a2bf5923f0e398b806db93886bb6e85103f82cb0d77a7b4f7d6508623de84296c22a02d110f953c7d8a32eb1bf871bc19e4a4761df6c9f6537075d6a4"
		// .getBytes();
		// byte[] key = keystr.getBytes("UTF8");
		// src = Base64.decodeBase64(src);
		// System.out.println(new String(src));
		// String mcontentString = new String(des3DecodeECB(key, src));
		// System.out.println(mcontentString);

		// // java des3 encode
		// byte[] text1 = "hello".getBytes();
		// for (byte b : text1) {
		// System.out.println(b);
		// }
		// System.out.println();
		// byte[] text = des3EncodeECB("0123456789abcd0123456789".getBytes(),
		// "hello".getBytes());
		// for (byte b : text) {
		// System.out.println(b);
		// }
		// System.out.println(new String((Base64.encodeBase64(text))));
		//
		// String textString = des3EncodeECB("38d88257801234f96966d2855659e486",
		// "{'busiNo':'010002','busiObject':{'messagePojo':{'contentString':'test%20msg%20push%20by%20appmvc','msgType':'PERSONAL'},'targetPojo':{'userIds':['001']}}}");
		// System.out.println(textString);
		// // java des3 decode
		// String mingwen = des3DecodeECB("38d88257801234f96966d2855659e486",
		// textString);
		// System.out.println(mingwen);
	}

}
