//package com.imooc.wechatshop.utils.otherUtils;
//
//import javax.crypto.Cipher;
//import java.security.*;
//import java.security.interfaces.RSAPrivateKey;
//import java.security.interfaces.RSAPublicKey;
//import java.security.spec.PKCS8EncodedKeySpec;
//import java.security.spec.X509EncodedKeySpec;
//import java.util.HashMap;
//import java.util.Map;
//
//public class RSAUtils {
//    /**
//     * 加密算法RSA
//     */
//    public static final String KEY_ALGORITHM = "RSA";
//
//    /**
//     * 签名算法
//     */
//    public static final String SIGNATURE_ALGORITHM = "SHA1WithRSA";
//
//    /**
//     * 获取公钥的key
//     */
//    private static final String PUBLIC_KEY = "RSAPublicKey";
//
//    /**
//     * 获取私钥的key
//     */
//    private static final String PRIVATE_KEY = "RSAPrivateKey";
//
//    /**
//     * RSA最大加密明文大小
//     */
//    private static final int MAX_ENCRYPT_BLOCK = 117;
//
//    /**
//     * RSA最大解密密文大小
//     */
//    private static final int MAX_DECRYPT_BLOCK = 128;
//
//    /**
//     * <p>
//     * 生成密钥对(公钥和私钥)
//     * </p>
//     *
//     * @return
//     * @throws Exception
//     */
//    public static Map<String, Object> genKeyPair() throws Exception {
//        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
//        keyPairGen.initialize(2048);
//        KeyPair keyPair = keyPairGen.generateKeyPair();
//        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
//        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
//        Map<String, Object> keyMap = new HashMap<String, Object>(2);
//        keyMap.put(PUBLIC_KEY, publicKey);
//        keyMap.put(PRIVATE_KEY, privateKey);
//        return keyMap;
//    }
//
//    /**
//     * <p>
//     * 用私钥对信息生成数字签名
//     * </p>
//     *
//     * @param data 已加密数据
//     * @param privateKey 私钥(BASE64编码)
//     *
//     * @return
//     * @throws Exception
//     */
//    public static String sign(byte[] data, String privateKey) throws Exception {
//        byte[] keyBytes = Base64Utils.decode(privateKey);
//        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
//        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
//        PrivateKey privateK = keyFactory.generatePrivate(pkcs8KeySpec);
//        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
//        signature.initSign(privateK);
//        signature.update(data);
//        return Base64Utils.encode(signature.sign());
//    }
//
//    /**
//     * <p>
//     * 校验数字签名
//     * </p>
//     *
//     * @param data 已加密数据
//     * @param publicKey 公钥(BASE64编码)
//     * @param sign 数字签名
//     *
//     * @return
//     * @throws Exception
//     *
//     */
//    public static boolean verify(byte[] data, String publicKey, String sign)
//            throws Exception {
//        byte[] keyBytes = Base64Utils.decode(publicKey);
//        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
//        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
//        PublicKey publicK = keyFactory.generatePublic(keySpec);
//        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
//        signature.initVerify(publicK);
//        signature.update(data);
//        return signature.verify(Base64Utils.decode(sign));
//    }
//
//    /**
//     * <P>
//     * 私钥解密
//     * </p>
//     *
//     * @param encryptedData 已加密数据
//     * @param privateKey 私钥(BASE64编码)
//     * @return
//     * @throws Exception
//     */
//    public static byte[] decryptByPrivateKey(byte[] encryptedData, String privateKey)
//            throws Exception {
//        byte[] keyBytes = Base64Utils.decode(privateKey);
//        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
//        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
//        Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);
//        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
//        cipher.init(Cipher.DECRYPT_MODE, privateK);
//        byte[] decryptedData = cipher.doFinal(encryptedData);
//        return decryptedData;
//    }
//
//    /**
//     * <p>
//     * 公钥解密
//     * </p>
//     *
//     * @param encryptedData 已加密数据
//     * @param publicKey 公钥(BASE64编码)
//     * @return
//     * @throws Exception
//     */
//    public static byte[] decryptByPublicKey(byte[] encryptedData, String publicKey)
//            throws Exception {
//        byte[] keyBytes = Base64Utils.decode(publicKey);
//        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
//        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
//        Key publicK = keyFactory.generatePublic(x509KeySpec);
//        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
//        cipher.init(Cipher.DECRYPT_MODE, publicK);
//        byte[] decryptedData = cipher.doFinal(encryptedData);
//        return decryptedData;
//    }
//
//    /**
//     * <p>
//     * 公钥加密
//     * </p>
//     *
//     * @param data 源数据
//     * @param publicKey 公钥(BASE64编码)
//     * @return
//     * @throws Exception
//     */
//    public static byte[] encryptByPublicKey(byte[] data, String publicKey)
//            throws Exception {
//        byte[] keyBytes = Base64Utils.decode(publicKey);
//        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
//        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
//        Key publicK = keyFactory.generatePublic(x509KeySpec);
//        // 对数据加密
//        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
//        cipher.init(Cipher.ENCRYPT_MODE, publicK);
//        byte[] encryptedData = cipher.doFinal(data);
//        return encryptedData;
//    }
//
//    /**
//     * <p>
//     * 私钥加密
//     * </p>
//     *
//     * @param data 源数据
//     * @param privateKey 私钥(BASE64编码)
//     * @return
//     * @throws Exception
//     */
//    public static byte[] encryptByPrivateKey(byte[] data, String privateKey)
//            throws Exception {
//        byte[] keyBytes = Base64Utils.decode(privateKey);
//        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
//        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
//        Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);
//        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
//        cipher.init(Cipher.ENCRYPT_MODE, privateK);
//        byte[] encryptedData = cipher.doFinal(data);
//        return encryptedData;
//    }
//
//    /**
//     * <p>
//     * 获取私钥
//     * </p>
//     *
//     * @param keyMap 密钥对
//     * @return
//     * @throws Exception
//     */
//    public static String getPrivateKey(Map<String, Object> keyMap)
//            throws Exception {
//        Key key = (Key) keyMap.get(PRIVATE_KEY);
//        return Base64Utils.encode(key.getEncoded());
//    }
//
//    /**
//     * <p>
//     * 获取公钥
//     * </p>
//     *
//     * @param keyMap 密钥对
//     * @return
//     * @throws Exception
//     */
//    public static String getPublicKey(Map<String, Object> keyMap)
//            throws Exception {
//        Key key = (Key) keyMap.get(PUBLIC_KEY);
//        return Base64Utils.encode(key.getEncoded());
//    }
//}
