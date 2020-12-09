/**
 * Software License Declaration.
 * <p>
 * zhilingsd.com, Co,. Ltd.
 * Copyright © 2016 All Rights Reserved.
 * <p>
 * Copyright Notice
 * This documents is provided to zhilingsd contracting agent or authorized programmer only.
 * This source code is written and edited by zhilingsd Co,.Ltd Inc specially for financial
 * business contracting agent or authorized cooperative company, in order to help them to
 * install, programme or central control in certain project by themselves independently.
 * <p>
 * Disclaimer
 * If this source code is needed by the one neither contracting agent nor authorized programmer
 * during the use of the code, should contact to zhilingsd Co,. Ltd Inc, and get the confirmation
 * and agreement of three departments managers  - Research Department, Marketing Department and
 * Production Department.Otherwise zhilingsd will charge the fee according to the programme itself.
 * <p>
 * Any one,including contracting agent and authorized programmer,cannot share this code to
 * the third party without the agreement of zhilingsd. If Any problem cannot be solved in the
 * procedure of programming should be feedback to zhilingsd Co,. Ltd Inc in time, Thank you!
 */
package com.zhilingsd.base.common.utils.crypto;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yangxianfeng
 * @version 1.0
 * @className RSAUtils.java
 * @description //TODO
 * @date 2020/11/11 15:07
 */
public class RSAUtils {

    private static final String CHARSET = "UTF-8";
    private static final String RSA_ALGORITHM = "RSA";

    private static final String PRIVATE_KEY = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCyWJbF6IPD+SHQsaxOxzCnbFpk+7nrjccliBY6eW1UsriasGMyBu8D3A5Zs8SOpZLMLvLPRs0g3ZeZPLvq/I9bjr8hia5RZtIv5Rw/mGXH9nz6NTjT0C2idgiHJcdU1esbyj1/RqANU4s8O+rv4fl/x1kVn93B42wWy2K67Po915Q677PpJL9wuF8zNcCGtOYeo1xOag+jcoC4Rsia4OK5Y/L+jIe5c+te4ThkH3JZbBxvsNaH4B2meUdkFuRxBFds5EKnr7z5c5a0qT28K69n73K6Obuhmhq3G+hiNsW9Haczv7bjZXBgAlMP5n1QhBWCpdpVSPOY8l/lwrVpJfu5AgMBAAECggEAHWSOTMW6WaUIlq2BmWYotZ8c8sGt7Y1rQdtA1phGlYvy8sOZRZhw8fDliJEhFuVg4TfnmpOvoPgEUSP6UIsZUVygUXWwfgf1IiGfWzfRpngQNuhomjgYHUtYsnVeebPb0LOvGPfZzvhy3RV+7dQPW6jOHMtBMAA8QzF37xi5UVGccxJRK33Sldy5EzLccuJJH3pDh6d38XZCZwxcO1oRbb3o0uVqm63IF8lW5DTusrdE2PfKbGDuHwMjXLxQoJM3HDd7gKi19443Ng+YoG9x5zZzqfu9p/9wSfgIK8EZn2MhkHW0osemnzl7CtKDa+B77rxiIpA57Nx2qIMmzELgkQKBgQDtIvagW6TacqhhqKmDSKwGU9NSeUYtgLW1bt8IyHvLWbt5LEPi4knzVKjwFVOMvsJkOKNG+E8rm/acYCYTxh3PD3McGinp4G20Uv68Ktyhbvp6U8FEMYb+VVm6nM/n9N2ecFDksphPmA18OWeMAv6SheM8ipCK0lKQUbURQncwNQKBgQDAiGsfUC8YH8I6K+jBK8RgbQpR+17o3lqUB/6mccG41TWnAEhfCYtK7zCcXTYO5ohDbp4tmTRO4Zhw2tmUOzkFeZvqLoWk03p6QRS+YotFp8tmmhtH4AO+2JccHljfnpCk6yyGkkFNpqdCAkh4grvQ+9ohnvVz6dvz9fHtQq6V9QKBgDsE46rfhDUWKlA8AQx6rs3YWRUCt/OlQBRK5tmxxBXFXIJ5AOBb/5m/LVirhiLWa100N3b3YATziTo8N93eBhc9uiF5ZYse307tBRdh9416KH7/j29ggD+WkIGAe8I4UFwZihiRVpUgu+J6CwpuyFtBVWABAJ58YC4pF86W40EtAoGAJ9wWUWVIWrY4f9KXXWayQURR3qcr/woJtpvxHIRwltONSAXG9eYD8H+UkMq7ZlpT26ILHWWabrpF1Rd59DZOl9OVc+YtxPIDaUwP/1pnewRCF/vPqhG2tMwifK/LtoAdcgc+MJ4W5vyZpBDU8B4gxCNgBZXjOxwvbVJ9w6EKhAECgYEAxgNgxdovB+sJM2qcowkF+k8uGxJE9sbIKtOFr/gzf+o8GevMwO2nmYPiEtSNV1/xWHuRi6LhN81s3B1n+R+MEsmlKffPHloYpBDXyBIp2ZgWmu962o7nD+o3qDXTNEsRsPr/IxzAkpRbCsO/wNY1dyNyheBQ7AuYpZ2zyBRnQo0=";



    private static Map<String, String> createKeys(int keySize) {
        //为RSA算法创建一个KeyPairGenerator对象
        KeyPairGenerator kpg;
        try {
            kpg = KeyPairGenerator.getInstance(RSA_ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException("No such algorithm-->[" + RSA_ALGORITHM + "]");
        }

        //初始化KeyPairGenerator对象,密钥长度
        kpg.initialize(keySize);                //keySize 可以为1024
        //生成密匙对
        KeyPair keyPair = kpg.generateKeyPair();
        //得到公钥
        Key publicKey = keyPair.getPublic();
        String publicKeyStr = Base64.encodeBase64URLSafeString(publicKey.getEncoded());
        //得到私钥
        Key privateKey = keyPair.getPrivate();
        String privateKeyStr = Base64.encodeBase64URLSafeString(privateKey.getEncoded());
        Map<String, String> keyPairMap = new HashMap<String, String>();
        keyPairMap.put("publicKey", publicKeyStr);
        keyPairMap.put("privateKey", privateKeyStr);

        return keyPairMap;
    }

    /**
     * 得到公钥
     *
     * @param publicKey 密钥字符串（经过base64编码）
     * @throws Exception
     */
    private static RSAPublicKey getPublicKey(String publicKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
        //通过X509编码的Key指令获得公钥对象
        KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(Base64.decodeBase64(publicKey));
        RSAPublicKey key = (RSAPublicKey) keyFactory.generatePublic(x509KeySpec);
        return key;
    }

    /**
     * 得到私钥
     *
     * @param privateKey 密钥字符串（经过base64编码）
     * @throws Exception
     */
    private static RSAPrivateKey getPrivateKey(String privateKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
        //通过PKCS#8编码的Key指令获得私钥对象
        KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(Base64.decodeBase64(privateKey));
        RSAPrivateKey key = (RSAPrivateKey) keyFactory.generatePrivate(pkcs8KeySpec);
        return key;
    }

    /**
     * 公钥加密
     *
     * @param data
     * @param publicKey
     * @return
     */
    private static String publicEncrypt(String data, RSAPublicKey publicKey) {
        try {
            Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            return Base64.encodeBase64URLSafeString(rsaSplitCodec(cipher, Cipher.ENCRYPT_MODE, data.getBytes(CHARSET), publicKey.getModulus().bitLength()));
        } catch (Exception e) {
            throw new RuntimeException("加密字符串[" + data + "]时遇到异常", e);
        }
    }

    /**
     * 私钥解密
     *
     * @param data
     * @param privateKey
     * @return
     */

    private static String privateDecrypt(String data, RSAPrivateKey privateKey) {
        try {
            Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            return new String(rsaSplitCodec(cipher, Cipher.DECRYPT_MODE, Base64.decodeBase64(data), privateKey.getModulus().bitLength()), CHARSET);
        } catch (Exception e) {
            throw new RuntimeException("解密字符串[" + data + "]时遇到异常", e);
        }
    }

    public static String privateDecrypt(String data) {
        try {
            return privateDecrypt(data, getPrivateKey(PRIVATE_KEY));
        } catch (Exception e) {
            throw new RuntimeException("私钥解析错误", e);
        }

    }

    /**
     * 私钥加密
     *
     * @param data
     * @param privateKey
     * @return
     */

    private static String privateEncrypt(String data, RSAPrivateKey privateKey) {
        try {
            Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, privateKey);
            return Base64.encodeBase64URLSafeString(rsaSplitCodec(cipher, Cipher.ENCRYPT_MODE, data.getBytes(CHARSET), privateKey.getModulus().bitLength()));
        } catch (Exception e) {
            throw new RuntimeException("加密字符串[" + data + "]时遇到异常", e);
        }
    }

    /**
     * 公钥解密
     *
     * @param data
     * @param publicKey
     * @return
     */

    private static String publicDecrypt(String data, RSAPublicKey publicKey) {
        try {
            Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, publicKey);
            return new String(rsaSplitCodec(cipher, Cipher.DECRYPT_MODE, Base64.decodeBase64(data), publicKey.getModulus().bitLength()), CHARSET);
        } catch (Exception e) {
            throw new RuntimeException("解密字符串[" + data + "]时遇到异常", e);
        }
    }

    private static byte[] rsaSplitCodec(Cipher cipher, int opmode, byte[] datas, int keySize) {
        int maxBlock = 0;
        if (opmode == Cipher.DECRYPT_MODE) {
            maxBlock = keySize / 8;
        } else {
            maxBlock = keySize / 8 - 11;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] buff;
        int i = 0;
        try {
            while (datas.length > offSet) {
                if (datas.length - offSet > maxBlock) {
                    buff = cipher.doFinal(datas, offSet, maxBlock);
                } else {
                    buff = cipher.doFinal(datas, offSet, datas.length - offSet);
                }
                out.write(buff, 0, buff.length);
                i++;
                offSet = i * maxBlock;
            }
        } catch (Exception e) {
            throw new RuntimeException("加解密阀值为[" + maxBlock + "]的数据时发生异常", e);
        }
        byte[] resultDatas = out.toByteArray();
        IOUtils.closeQuietly(out);
        return resultDatas;
    }


    public static void main (String[] args) throws Exception {
        Map<String, String> keyMap = createKeys(1024);
        String publicKey = keyMap.get("publicKey");
        String privateKey = keyMap.get("privateKey");
        System.out.println("公钥: \n\r" + publicKey);
        System.out.println("私钥： \n\r" + privateKey);

        System.out.println("公钥加密——私钥解密");
        String str = "站在大明门前守卫的禁卫军，事先没有接到\n" +
                "有关的命令，但看到大批盛装的官员来临，也就\n" +
                "以为确系举行大典，因而未加询问。进大明门即\n" +
                "为皇城。文武百官看到端门午门之前气氛平静，\n" +
                "城楼上下也无朝会的迹象，既无几案，站队点名\n" +
                "的御史和御前侍卫“大汉将军”也不见踪影，不免\n" +
                "心中揣测，互相询问：所谓午朝是否讹传？";
        System.out.println("\r明文：\r\n" + str);
        System.out.println("\r明文大小：\r\n" + str.getBytes().length);
        //公钥加密
        String encodedData = publicEncrypt(str, getPublicKey(publicKey));

        System.out.println("密文：\r\n" + encodedData);
        //私钥解密
        String decodedData = privateDecrypt(encodedData, getPrivateKey(privateKey));

        System.out.println("解密后文字: \r\n" + decodedData);


//        String key = "MIIEowIBAAKCAQEAsliWxeiDw/kh0LGsTscwp2xaZPu5643HJYgWOnltVLK4mrBj\nMgbvA9wOWbPEjqWSzC7yz0bNIN2XmTy76vyPW46/IYmuUWbSL+UcP5hlx/Z8+jU4\n09AtonYIhyXHVNXrG8o9f0agDVOLPDvq7+H5f8dZFZ/dweNsFstiuuz6PdeUOu+z\n6SS/cLhfMzXAhrTmHqNcTmoPo3KAuEbImuDiuWPy/oyHuXPrXuE4ZB9yWWwcb7DW\nh+AdpnlHZBbkcQRXbORCp6+8+XOWtKk9vCuvZ+9yujm7oZoatxvoYjbFvR2nM7+2\n42VwYAJTD+Z9UIQVgqXaVUjzmPJf5cK1aSX7uQIDAQABAoIBAB1kjkzFulmlCJat\ngZlmKLWfHPLBre2Na0HbQNaYRpWL8vLDmUWYcPHw5YiRIRblYOE355qTr6D4BFEj\n+lCLGVFcoFF1sH4H9SIhn1s30aZ4EDboaJo4GB1LWLJ1Xnmz29Czrxj32c74ct0V\nfu3UD1uozhzLQTAAPEMxd+8YuVFRnHMSUSt90pXcuRMy3HLiSR96Q4end/F2QmcM\nXDtaEW296NLlaputyBfJVuQ07rK3RNj3ymxg7h8DI1y8UKCTNxw3e4CotfeONzYP\nmKBvcec2c6n7vaf/cEn4CCvBGZ9jIZB1tKLHpp85ewrSg2vge+68YiKQOezcdqiD\nJsxC4JECgYEA7SL2oFuk2nKoYaipg0isBlPTUnlGLYC1tW7fCMh7y1m7eSxD4uJJ\n81So8BVTjL7CZDijRvhPK5v2nGAmE8Ydzw9zHBop6eBttFL+vCrcoW76elPBRDGG\n/lVZupzP5/TdnnBQ5LKYT5gNfDlnjAL+koXjPIqQitJSkFG1EUJ3MDUCgYEAwIhr\nH1AvGB/COivowSvEYG0KUfte6N5alAf+pnHBuNU1pwBIXwmLSu8wnF02DuaIQ26e\nLZk0TuGYcNrZlDs5BXmb6i6FpNN6ekEUvmKLRafLZpobR+ADvtiXHB5Y356QpOss\nhpJBTaanQgJIeIK70PvaIZ71c+nb8/Xx7UKulfUCgYA7BOOq34Q1FipQPAEMeq7N\n2FkVArfzpUAUSubZscQVxVyCeQDgW/+Zvy1Yq4Yi1mtdNDd292AE84k6PDfd3gYX\nPboheWWLHt9O7QUXYfeNeih+/49vYIA/lpCBgHvCOFBcGYoYkVaVILviegsKbshb\nQVVgAQCefGAuKRfOluNBLQKBgCfcFlFlSFq2OH/Sl11mskFEUd6nK/8KCbab8RyE\ncJbTjUgFxvXmA/B/lJDKu2ZaU9uiCx1lmm66RdUXefQ2TpfTlXPmLcTyA2lMD/9a\nZ3sEQhf7z6oRtrTMInyvy7aAHXIHPjCeFub8maQQ1PAeIMQjYAWV4zscL21SfcOh\nCoQBAoGBAMYDYMXaLwfrCTNqnKMJBfpPLhsSRPbGyCrTha/4M3/qPBnrzMDtp5mD\n4hLUjVdf8Vh7kYui4TfNbNwdZ/kfjBLJpSn3zx5aGKQQ18gSKdmYFprvetqO5w/q\nN6g10zRLEbD6/yMcwJKUWwrDv8DWNXcjcoXgUOwLmKWds8gUZ0KN\n";
        String key = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCyWJbF6IPD+SHQ\n" +
                "saxOxzCnbFpk+7nrjccliBY6eW1UsriasGMyBu8D3A5Zs8SOpZLMLvLPRs0g3ZeZ\n" +
                "PLvq/I9bjr8hia5RZtIv5Rw/mGXH9nz6NTjT0C2idgiHJcdU1esbyj1/RqANU4s8\n" +
                "O+rv4fl/x1kVn93B42wWy2K67Po915Q677PpJL9wuF8zNcCGtOYeo1xOag+jcoC4\n" +
                "Rsia4OK5Y/L+jIe5c+te4ThkH3JZbBxvsNaH4B2meUdkFuRxBFds5EKnr7z5c5a0\n" +
                "qT28K69n73K6Obuhmhq3G+hiNsW9Haczv7bjZXBgAlMP5n1QhBWCpdpVSPOY8l/l\n" +
                "wrVpJfu5AgMBAAECggEAHWSOTMW6WaUIlq2BmWYotZ8c8sGt7Y1rQdtA1phGlYvy\n" +
                "8sOZRZhw8fDliJEhFuVg4TfnmpOvoPgEUSP6UIsZUVygUXWwfgf1IiGfWzfRpngQ\n" +
                "NuhomjgYHUtYsnVeebPb0LOvGPfZzvhy3RV+7dQPW6jOHMtBMAA8QzF37xi5UVGc\n" +
                "cxJRK33Sldy5EzLccuJJH3pDh6d38XZCZwxcO1oRbb3o0uVqm63IF8lW5DTusrdE\n" +
                "2PfKbGDuHwMjXLxQoJM3HDd7gKi19443Ng+YoG9x5zZzqfu9p/9wSfgIK8EZn2Mh\n" +
                "kHW0osemnzl7CtKDa+B77rxiIpA57Nx2qIMmzELgkQKBgQDtIvagW6TacqhhqKmD\n" +
                "SKwGU9NSeUYtgLW1bt8IyHvLWbt5LEPi4knzVKjwFVOMvsJkOKNG+E8rm/acYCYT\n" +
                "xh3PD3McGinp4G20Uv68Ktyhbvp6U8FEMYb+VVm6nM/n9N2ecFDksphPmA18OWeM\n" +
                "Av6SheM8ipCK0lKQUbURQncwNQKBgQDAiGsfUC8YH8I6K+jBK8RgbQpR+17o3lqU\n" +
                "B/6mccG41TWnAEhfCYtK7zCcXTYO5ohDbp4tmTRO4Zhw2tmUOzkFeZvqLoWk03p6\n" +
                "QRS+YotFp8tmmhtH4AO+2JccHljfnpCk6yyGkkFNpqdCAkh4grvQ+9ohnvVz6dvz\n" +
                "9fHtQq6V9QKBgDsE46rfhDUWKlA8AQx6rs3YWRUCt/OlQBRK5tmxxBXFXIJ5AOBb\n" +
                "/5m/LVirhiLWa100N3b3YATziTo8N93eBhc9uiF5ZYse307tBRdh9416KH7/j29g\n" +
                "gD+WkIGAe8I4UFwZihiRVpUgu+J6CwpuyFtBVWABAJ58YC4pF86W40EtAoGAJ9wW\n" +
                "UWVIWrY4f9KXXWayQURR3qcr/woJtpvxHIRwltONSAXG9eYD8H+UkMq7ZlpT26IL\n" +
                "HWWabrpF1Rd59DZOl9OVc+YtxPIDaUwP/1pnewRCF/vPqhG2tMwifK/LtoAdcgc+\n" +
                "MJ4W5vyZpBDU8B4gxCNgBZXjOxwvbVJ9w6EKhAECgYEAxgNgxdovB+sJM2qcowkF\n" +
                "+k8uGxJE9sbIKtOFr/gzf+o8GevMwO2nmYPiEtSNV1/xWHuRi6LhN81s3B1n+R+M\n" +
                "EsmlKffPHloYpBDXyBIp2ZgWmu962o7nD+o3qDXTNEsRsPr/IxzAkpRbCsO/wNY1\n" +
                "dyNyheBQ7AuYpZ2zyBRnQo0=";
        key = key.replaceAll("\n", "");
        System.out.println(key);
        String data = "C9cd8phVIfnjbisDIBUr3enAkug61uViUwAzxbOTmFHMrrP6C75ZLA1gsiOI4yeP3FYyUNy+r8e/QQDv5rtXYHiA54kclVt4WxsUEiFtn0/b43ushS+gWIldS2z1679k+ACpN6EgPzBwNeZO47OsDD38AeOrmlud29TXxd/jp95fqDPFdfRDJKB9jsy92HZY5qCH4hfySl0wXDBexbbZPHl0kU+WMPtFT/uZHOUSL3lV0Cul4sGnk5VM6e0eKRN/hvE4pRmdY8U5g6VPyTTd4eFLXRirpYd8heyFh02bIlZ5eWqPJ5jwIYTysaJOT/7wRsAGUAyY4P4tvMexu5JYfg=";
        String decodedData2 = privateDecrypt(data, getPrivateKey(key));
        System.out.println(decodedData2);


    }


}