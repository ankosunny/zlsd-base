package com.zhilingsd.collection.common.util;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * RSA 工具类
 * 
 * <pre>
 * 密钥长度
 * </pre>
 * 
 * <div style="color:red;" >1024bit</div>
 * <p>
 * 
 * @ClassName: RSA
 * @Description: TODO
 * @mail xuwh10815@hundsun.com
 * @date 2015-12-16 下午5:42:06
 * @version V1.0
 * 
 */
public class RSA {
	/**
	 * 密钥长度(bit)
	 */
	public static final int KEY_LENGTH = 1024;
	/**
	 * <p>
	 * 单次加密最大明文长度，这里仅仅指1024bit 长度密钥
	 * 
	 * <pre>
	 * 本文介绍RSA加解密中必须考虑到的密钥长度、明文长度和密文长度问题，对第一次接触RSA的开发人员来说，RSA算是比较复杂的算法，天缘以后还会补充几篇RSA基础知识专题文章，用最简单最通俗的语言描述RSA，让各位了解RSA算法本身其实也很简单，RSA的复杂度是因为数学家把效率和安全也考虑进去的缘故。
	 * 本文先只谈密钥长度、明文长度和密文长度的概念知识，RSA的理论及示例等以后再谈。提到密钥，我们不得不提到RSA的三个重要大数：公钥指数e、私钥指数d和模值n。这三个大数是我们使用RSA时需要直接接触的，理解了本文的基础概念，即使未接触过RSA的开发人员也能应对自如的使用RSA相关函数库，无需深入了解e、d、n是如何生成的，只需要知道我该如何用、要注意什么。
	 * 一、密钥长度
	 * 1、密钥是指谁？
	 * 首先我们说的“密钥”是指谁？由于RSA密钥是（公钥+模值）、（私钥+模值）分组分发的，单独给对方一个公钥或私钥是没有任何用处，所以我们说的“密钥”其实是它们两者中的其中一组。但我们说的“密钥长度”一般只是指模值的位长度。目前主流可选值：1024、2048、3072、4096...
	 * 2、模值主流长度是多少？
	 * 目前主流密钥长度至少都是1024bits以上，低于1024bit的密钥已经不建议使用（安全问题）。那么上限在哪里？没有上限，多大都可以使用。所以，主流的模值是1024位，实际运算结果可能会略小于1024bits，注意，这个值不是绝对的，跟素数的生成算法有关系，只是告诉素数生成器“帮我生成一个接近1024位的素数而已”，然后生成器“好，给您一个，这个差不多1024位”。
	 * 素数生成器这么厉害？说生成1024位就会出个1024位的大整数？真实的情况是素数生成器也只是在1024bits对应的整数附近进行“摸索”而已，大家其实都不容易，又要快又要准确又要随机性，那么素数生成器也只能应付一下，找到1024位的算是好运，没找到1024位，1023位也照样送出来：）。
	 * 3、公钥指数如何确定？
	 * 公钥指数是随意选的，但目前行业上公钥指数普遍选的都是65537（0x10001，5bits），该值是除了1、3、5、17、257之外的最小素数，为什么不选的大一点？当然可以，只是考虑到既要满足相对安全、又想运算的快一点（加密时），PKCS#1的一个建议值而已。
	 * 有意的把公钥指数选的小一点，但是对应私钥指数肯定很大，意图也很明确，大家都要用公钥加密，所以大家时间很宝贵，需要快一点，您一个人私钥解密，时间长一点就多担待，少数服从多数的典型应用。
	 * 4、私钥指数如何确定？
	 * 公钥指数随意选，那么私钥就不能再随意选了，只能根据算法公式（ed%k=1，k=(p-1)(q-1)）进行运算出来。那么私钥指数会是多少位？根据ed关系，私钥d=(x*k+1)/e，所以单看这个公式，私钥指数似乎也不是唯一结果，可能大于也可能小于1024bits的，但我们习惯上也是指某个小于1024bits的大整数。
	 * 包括前文的公钥指数，在实际运算和存储时为方便一般都是按照标准位长进行使用，前面不足部分补0填充，所以，使用保存和转换这些密钥需要注意统一缓冲区的长度。
	 * 二、明文长度
	 * 网上有说明文长度小于等于密钥长度（Bytes）-11，这说法本身不太准确，会给人感觉RSA 1024只能加密117字节长度明文。实际上，RSA算法本身要求加密内容也就是明文长度m必须0<m<n，也就是说内容这个大整数不能超过n，否则就出错。那么如果m=0是什么结果？普遍RSA加密器会直接返回全0结果。如果m>n，运算就会出错？！那怎么办？且听下文分解。
	 * 所以，RSA实际可加密的明文长度最大也是1024bits，但问题就来了：
	 * 如果小于这个长度怎么办？就需要进行padding，因为如果没有padding，用户无法确分解密后内容的真实长度，字符串之类的内容问题还不大，以0作为结束符，但对二进制数据就很难理解，因为不确定后面的0是内容还是内容结束符。
	 * 只要用到padding，那么就要占用实际的明文长度，于是才有117字节的说法。我们一般使用的padding标准有NoPPadding、OAEPPadding、PKCS1Padding等，其中PKCS#1建议的padding就占用了11个字节。
	 * 如果大于这个长度怎么办？很多算法的padding往往是在后边的，但PKCS的padding则是在前面的，此为有意设计，有意的把第一个字节置0以确保m的值小于n。
	 * 这样，128字节（1024bits）-减去11字节正好是117字节，但对于RSA加密来讲，padding也是参与加密的，所以，依然按照1024bits去理解，但实际的明文只有117字节了。
	 * 关于PKCS#1 padding规范可参考：RFC2313 chapter 8.1，我们在把明文送给RSA加密器前，要确认这个值是不是大于n，也就是如果接近n位长，那么需要先padding再分段加密。除非我们是“定长定量自己可控可理解”的加密不需要padding。
	 * 三、密文长度
	 * 密文长度就是给定符合条件的明文加密出来的结果位长，这个可以确定，加密后的密文位长跟密钥的位长度是相同的，因为加密公式：
	 * C=(P^e)%n
	 * 所以，C最大值就是n-1，所以不可能超过n的位数。尽管可能小于n的位数，但从传输和存储角度，仍然是按照标准位长来进行的，所以，即使我们加密一字节的明文，运算出来的结果也要按照标准位长来使用（当然了，除非我们能再采取措施区分真实的位长，一般不在考虑）。
	 * 至于明文分片多次加密，自然密文长度成倍增长，但已不属于一次加密的问题，不能放到一起考虑。
	 * </pre>
	 * 
	 * @see <a href="http://tools.ietf.org/html/rfc2313">RFC2313</a>
	 */
	public static final int MAX_ENCRYPT_BLOCK = 117;
	/**
	 * <p>
	 * 单次解密最大密文长度，这里仅仅指1024bit 长度密钥
	 * </p>
	 * 
	 * @see #MAX_ENCRYPT_BLOCK
	 */
	public static final int MAX_DECRYPT_BLOCK = 128;

	/**
	 * 加密
	 */
	// public static final String SIGN_TYPE_RSA = "RSA";

	/**
	 * 加密算法
	 */
	public static final String ALGORITHM_RSA = "RSA";

	/**
	 * 算法/模式/填充
	 */
	public static final String CIPHER_TRANSFORMATION_RSA = "RSA/ECB/PKCS1Padding";

	/**
	 * 签名算法
	 */
	public static final String SIGN_ALGORITHMS = "SHA1WithRSA";

	/** UTF-8字符集 **/
	public static final String CHARSET_UTF8 = "UTF-8";

	/** GBK字符集 **/
	public static final String CHARSET_GBK = "GBK";

	public static final String CHARSET = CHARSET_UTF8;

	/**
	 * 得到公钥
	 * 
	 * @param key
	 *            密钥字符串（经过base64编码）
	 * @param charset
	 * @throws Exception
	 */
	public static PublicKey getPublicKey(String key, String charset)
			throws Exception {
		byte[] keyBytes = Base64.decodeBase64(key.getBytes(charset));

		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM_RSA);
		PublicKey publicKey = keyFactory.generatePublic(keySpec);
		return publicKey;
	}

	/**
	 * 得到私钥
	 * 
	 * @param key
	 *            密钥字符串（经过base64编码）
	 * @param charset
	 * @throws Exception
	 */
	public static PrivateKey getPrivateKey(String key, String charset)
			throws Exception {
		byte[] keyBytes;
		keyBytes = Base64.decodeBase64(key.getBytes(charset));

		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM_RSA);
		PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
		return privateKey;
	}

	/**
	 * 得到密钥字符串（经过base64编码）
	 * 
	 * @return
	 */
	public static String getKeyString(Key key) throws Exception {
		byte[] keyBytes = key.getEncoded();
		String s = new String(Base64.encodeBase64(keyBytes), CHARSET);
		return s;
	}

	/**
	 * 
	 * 公钥加密
	 * 
	 * @param content
	 *            待加密内容
	 * @param publicKey
	 *            公钥
	 * @param charset
	 *            字符集，如UTF-8, GBK, GB2312
	 * @return 密文内容
	 * @throws Exception
	 */
	public static String rsaEncrypt(String content, String publicKey,
                                    String charset) throws Exception {
		try {
			PublicKey pubKey = getPublicKey(publicKey, charset);
			Cipher cipher = Cipher.getInstance(CIPHER_TRANSFORMATION_RSA);
			cipher.init(Cipher.ENCRYPT_MODE, pubKey);
			byte[] data = StringUtils.isEmpty(charset) ? content.getBytes()
					: content.getBytes(charset);
			int inputLen = data.length;
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			int offSet = 0;
			byte[] cache;
			int i = 0;
			// 对数据分段加密
			while (inputLen - offSet > 0) {
				if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
					cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
				} else {
					cache = cipher.doFinal(data, offSet, inputLen - offSet);
				}
				out.write(cache, 0, cache.length);
				i++;
				offSet = i * MAX_ENCRYPT_BLOCK;
			}
			byte[] encryptedData = Base64.encodeBase64(out.toByteArray());
			out.close();

			return StringUtils.isEmpty(charset) ? new String(encryptedData)
					: new String(encryptedData, charset);
		} catch (Exception e) {
			throw new Exception(
					"error occured in rsaEncrypt: EncryptContent = " + content
							+ ",charset = " + charset, e);
		}
	}

	/**
	 * 
	 * 公钥加密
	 * 
	 * @param content
	 *            待加密内容
	 * @param publicKey
	 *            公钥
	 * @param charset
	 *            字符集，如UTF-8, GBK, GB2312
	 * @return 密文内容
	 * @throws Exception
	 */
	public static String rsaEncrypt(String content, String publicKey)
			throws Exception {
		return rsaEncrypt(content, publicKey, RSA.CHARSET);
	}

	/**
	 * 私钥解密
	 * 
	 * @param content
	 *            待解密内容
	 * @param privateKey
	 *            私钥
	 * @param charset
	 *            字符集，如UTF-8, GBK, GB2312
	 * @return 明文内容
	 * @throws Exception
	 */
	public static String rsaDecrypt(String content, String privateKey,
                                    String charset) throws Exception {
		try {
			PrivateKey priKey = getPrivateKey(privateKey, charset);
			Cipher cipher = Cipher.getInstance(CIPHER_TRANSFORMATION_RSA);
			cipher.init(Cipher.DECRYPT_MODE, priKey);
			byte[] encryptedData = StringUtils.isEmpty(charset) ? Base64
					.decodeBase64(content.getBytes()) : Base64
					.decodeBase64(content.getBytes(charset));
			int inputLen = encryptedData.length;
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			int offSet = 0;
			byte[] cache;
			int i = 0;
			// 对数据分段解密
			while (inputLen - offSet > 0) {
				if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
					cache = cipher.doFinal(encryptedData, offSet,
							MAX_DECRYPT_BLOCK);
				} else {
					cache = cipher.doFinal(encryptedData, offSet, inputLen
							- offSet);
				}
				out.write(cache, 0, cache.length);
				i++;
				offSet = i * MAX_DECRYPT_BLOCK;
			}
			byte[] decryptedData = out.toByteArray();
			out.close();

			return StringUtils.isEmpty(charset) ? new String(decryptedData)
					: new String(decryptedData, charset);
		} catch (Exception e) {
			throw new Exception("error occured in rsaDecrypt: EncodeContent = "
					+ content + ",charset = " + charset, e);
		}
	}

	/**
	 * 私钥解密
	 * 
	 * @param content
	 *            待解密内容
	 * @param privateKey
	 *            私钥
	 * @return 明文内容
	 * @throws Exception
	 */
	public static String rsaDecrypt(String content, String privateKey)
			throws Exception {
		return rsaDecrypt(content, privateKey, RSA.CHARSET);
	}

	/**
	 * 获得密钥对
	 * 
	 * @Title creatKeyPair
	 * @Description TODO
	 * @return
	 * @throws NoSuchAlgorithmException
	 *             KeyPair
	 */
	public static KeyPair creatKeyPair() throws NoSuchAlgorithmException {
		KeyPairGenerator keyPairGen = KeyPairGenerator
				.getInstance(RSA.ALGORITHM_RSA);
		// 密钥位数
		keyPairGen.initialize(KEY_LENGTH);
		// 密钥对
		KeyPair keyPair = keyPairGen.generateKeyPair();
		return keyPair;
	}

	public static void main(String[] args) throws NoSuchAlgorithmException {
//		try {
//			System.out.println(Cipher.getInstance(CIPHER_TRANSFORMATION_RSA)
//					.getBlockSize());
//			System.out.println(Cipher.getInstance(CIPHER_TRANSFORMATION_RSA)
//					.getAlgorithm());
//			System.out.println(Cipher.getInstance(CIPHER_TRANSFORMATION_RSA)
//					.getProvider());
//
//		} catch (NoSuchPaddingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		try {
//			KeyPair keyPair = creatKeyPair();
//			String pubkeyString = getKeyString(keyPair.getPublic());
//			String prikeyString = getKeyString(keyPair.getPrivate());
//			System.out.println("pubkeyString:" + pubkeyString);
//			System.out.println("prikeyString:" + prikeyString);
//			String pString = "车票，代购请提前一天联系，预售时间请在线、保持手机畅通，附上各车站预售时间";
//			String cString = rsaEncrypt(pString, pubkeyString);
//			System.out.println("cString>>>>>>>>>>>:" + cString);
//			long starttime = System.currentTimeMillis();
//			System.out.println("start decode 10s:" + starttime);
//
//			for (int i = 0; i < 10; i++) {
//				String temString = rsaDecrypt(cString, prikeyString);
//				System.out.println("明文>>>>>>>>>:" + temString);
//			}
//
//			long endtime = System.currentTimeMillis();
//			System.out.println("end decode:" + endtime);
//			System.out.println("completed in:" + (endtime - starttime));
//
//		} catch (Exception e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}
	}

}
