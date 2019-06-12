package com.github.surpassm.tool.codec;

import org.apache.tomcat.util.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author mc
 * Create date 2019/4/15 11:18
 * Version 1.0
 * Description
 */
public class AesEncryptUtils {

	private static final String ALGORITHMSTR = "AES/ECB/PKCS5Padding";

	public static String base64Encode(byte[] bytes) {
		return Base64.encodeBase64String(bytes);
	}

	public static byte[] base64Decode(String base64Code) throws Exception {
		return Base64.decodeBase64(base64Code);
	}

	private static byte[] aesEncryptToBytes(String content, String encryptKey) throws Exception {
		KeyGenerator kgen = KeyGenerator.getInstance("AES");
		kgen.init(128);
		Cipher cipher = Cipher.getInstance(ALGORITHMSTR);
		cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(encryptKey.getBytes(), "AES"));
		return cipher.doFinal(content.getBytes("utf-8"));
	}

	public static String aesEncrypt(String content, String encryptKey) throws Exception {
		return base64Encode(aesEncryptToBytes(content, encryptKey));
	}

	private static String aesDecryptByBytes(byte[] encryptBytes, String decryptKey) throws Exception {
		KeyGenerator kgen = KeyGenerator.getInstance("AES");
		kgen.init(128);
		Cipher cipher = Cipher.getInstance(ALGORITHMSTR);
		cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(decryptKey.getBytes(), "AES"));
		byte[] decryptBytes = cipher.doFinal(encryptBytes);
		return new String(decryptBytes);
	}

	public static String aesDecrypt(String encryptStr, String decryptKey) throws Exception {
		return aesDecryptByBytes(base64Decode(encryptStr), decryptKey);
	}

	public static void main(String[] args) throws Exception {
		 String KEY = "d7b85f6e214abcda";
		String content = "你好";
		System.out.println("加密前：" + content);

		String encrypt = aesEncrypt(content, KEY);
		System.out.println(encrypt.length() + ":加密后：" + encrypt);

		String decrypt = aesDecrypt("PABx54k5CYfiD7mARLpybjENOLVJ7mSpj9VfFBdTZExrx6RrAU8GGMNkjmVpwkrZfFJ/QEzNFJmmCYRcC0eeIz4zQzGlNtf/XLLBN3jubtdUNjNqbu3SgDrgkq3NT5uwx+SGduokYdt2bmv4w9gOnVoVN3Kk6WuahFfr+efPJSQBMgWU2yreCBqnRyAScp7eshwggff6kJYf57RJnJNpiwUoxZIUyrBgxNdb/w9o9pJ5GscgSZlqA78V7VngHJ1TU0Jn//QQFnihtS+eiKhWP9t/8FsmOHHASsiaC5BifC+odk5HZEkl3cpVx323uTXFxQzwLPAl9WtAsNdLANJQhCen7zg/MrR6sGZa9Xzv8cJZPuLVg5H/y8DCl4Kpa/b6TS/lcid01KNvZI3nYK8vJ3i6rbn+jwlGICqSG1fAsIx4apNkU2ozC+9bsCpn2zPgJf6xFhqQu/8sJ2XWRm6oLZnZBf3rUKe4/Z4BV8Tx/Tf6g2mEnTSmMbhhLwnH7pn2+xTHFXSaS9SfWcIdshuH5WsruK2WwJaEmtMJvM+62YKMHhErLmLokCrTve/IZJk7ug2DykN96oMU1OS1AVYi7kpJDvrKJSpaQI+1CJQOb9M=", KEY);
		System.out.println("解密后：" + decrypt);
	}


}
