package com.daqinzhonggong.sb2.admin.utils;

import java.io.ByteArrayOutputStream;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.Cipher;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;

/**
 * Rsa 工具类，公钥私钥生成，加解密
 **/
@Slf4j
public class RsaUtils {

  private static final String SRC = "5t6FrY3LmDK584jqqyV8RBQXrcW7JPPP";

  public static void main(String[] args) throws Exception {
    log.info("\n");
    RsaKeyPair keyPair = generateKeyPair();
    log.info("公钥：{}", keyPair.getPublicKey());
    log.info("私钥：{}", keyPair.getPrivateKey());
    log.info("\n");
    test1(keyPair);
    log.info("\n");
    test2(keyPair);
    log.info("\n");
  }

  /**
   * 公钥加密私钥解密
   */
  private static void test1(RsaKeyPair keyPair) throws Exception {
    log.info("***************** 公钥加密私钥解密开始 *****************");
    String text1 = encryptByPublicKey(keyPair.getPublicKey(), RsaUtils.SRC);
    String text2 = decryptByPrivateKey(keyPair.getPrivateKey(), text1);
    log.info("加密前：" + RsaUtils.SRC);
    log.info("加密后：" + text1);
    log.info("解密后：" + text2);
    if (RsaUtils.SRC.equals(text2)) {
      log.info("解密字符串和原始字符串一致，解密成功");
    } else {
      log.info("解密字符串和原始字符串不一致，解密失败");
    }
    log.info("***************** 公钥加密私钥解密结束 *****************");
  }

  /**
   * 私钥加密公钥解密
   *
   * @throws Exception /
   */
  private static void test2(RsaKeyPair keyPair) throws Exception {
    log.info("***************** 私钥加密公钥解密开始 *****************");
    String text1 = encryptByPrivateKey(keyPair.getPrivateKey(), RsaUtils.SRC);
    String text2 = decryptByPublicKey(keyPair.getPublicKey(), text1);
    log.info("加密前：" + RsaUtils.SRC);
    log.info("加密后：" + text1);
    log.info("解密后：" + text2);
    if (RsaUtils.SRC.equals(text2)) {
      log.info("解密字符串和原始字符串一致，解密成功");
    } else {
      log.info("解密字符串和原始字符串不一致，解密失败");
    }
    log.info("***************** 私钥加密公钥解密结束 *****************");
  }

  /**
   * 公钥解密
   *
   * @param publicKeyText 公钥
   * @param text          待解密的信息
   * @return /
   * @throws Exception /
   */
  public static String decryptByPublicKey(String publicKeyText, String text) throws Exception {
    X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(
        Base64.decodeBase64(publicKeyText));
    KeyFactory keyFactory = KeyFactory.getInstance("RSA");
    PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
    Cipher cipher = Cipher.getInstance("RSA");
    cipher.init(Cipher.DECRYPT_MODE, publicKey);
    byte[] result = doLongerCipherFinal(Cipher.DECRYPT_MODE, cipher, Base64.decodeBase64(text));
    return new String(result);
  }

  /**
   * 私钥加密
   *
   * @param privateKeyText 私钥
   * @param text           待加密的信息
   * @return /
   * @throws Exception /
   */
  public static String encryptByPrivateKey(String privateKeyText, String text) throws Exception {
    PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(
        Base64.decodeBase64(privateKeyText));
    KeyFactory keyFactory = KeyFactory.getInstance("RSA");
    PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
    Cipher cipher = Cipher.getInstance("RSA");
    cipher.init(Cipher.ENCRYPT_MODE, privateKey);
    byte[] result = doLongerCipherFinal(Cipher.ENCRYPT_MODE, cipher, text.getBytes());
    return Base64.encodeBase64String(result);
  }

  /**
   * 私钥解密
   *
   * @param privateKeyText 私钥
   * @param text           待解密的文本
   * @return /
   * @throws Exception /
   */
  public static String decryptByPrivateKey(String privateKeyText, String text) throws Exception {
    PKCS8EncodedKeySpec pkcs8EncodedKeySpec5 = new PKCS8EncodedKeySpec(
        Base64.decodeBase64(privateKeyText));
    KeyFactory keyFactory = KeyFactory.getInstance("RSA");
    PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec5);
    Cipher cipher = Cipher.getInstance("RSA");
    cipher.init(Cipher.DECRYPT_MODE, privateKey);
    byte[] result = doLongerCipherFinal(Cipher.DECRYPT_MODE, cipher, Base64.decodeBase64(text));
    return new String(result);
  }

  /**
   * 公钥加密
   *
   * @param publicKeyText 公钥
   * @param text          待加密的文本
   * @return /
   */
  public static String encryptByPublicKey(String publicKeyText, String text) throws Exception {
    X509EncodedKeySpec x509EncodedKeySpec2 = new X509EncodedKeySpec(
        Base64.decodeBase64(publicKeyText));
    KeyFactory keyFactory = KeyFactory.getInstance("RSA");
    PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec2);
    Cipher cipher = Cipher.getInstance("RSA");
    cipher.init(Cipher.ENCRYPT_MODE, publicKey);
    byte[] result = doLongerCipherFinal(Cipher.ENCRYPT_MODE, cipher, text.getBytes());
    return Base64.encodeBase64String(result);
  }

  private static byte[] doLongerCipherFinal(int opMode, Cipher cipher, byte[] source)
      throws Exception {
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    if (opMode == Cipher.DECRYPT_MODE) {
      out.write(cipher.doFinal(source));
    } else {
      int offset = 0;
      int totalSize = source.length;
      while (totalSize - offset > 0) {
        int size = Math.min(cipher.getOutputSize(0) - 11, totalSize - offset);
        out.write(cipher.doFinal(source, offset, size));
        offset += size;
      }
    }
    out.close();
    return out.toByteArray();
  }

  /**
   * 构建RSA密钥对
   *
   * @return /
   * @throws NoSuchAlgorithmException /
   */
  public static RsaKeyPair generateKeyPair() throws NoSuchAlgorithmException {
    KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
    keyPairGenerator.initialize(1024);
    KeyPair keyPair = keyPairGenerator.generateKeyPair();
    RSAPublicKey rsaPublicKey = (RSAPublicKey) keyPair.getPublic();
    RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) keyPair.getPrivate();
    String publicKeyString = Base64.encodeBase64String(rsaPublicKey.getEncoded());
    String privateKeyString = Base64.encodeBase64String(rsaPrivateKey.getEncoded());
    return new RsaKeyPair(publicKeyString, privateKeyString);
  }


  /**
   * RSA密钥对对象
   */
  public static class RsaKeyPair {

    private final String publicKey;
    private final String privateKey;

    public RsaKeyPair(String publicKey, String privateKey) {
      this.publicKey = publicKey;
      this.privateKey = privateKey;
    }

    public String getPublicKey() {
      return publicKey;
    }

    public String getPrivateKey() {
      return privateKey;
    }

  }
}
