package wgh.dubbo.common.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class RSAEncryptUtil {
 
	/**
	 * 开发者私钥 (注意：JAVA使用的是PKCS8的编码，如果私钥不是PKCK8的编码，请使用Openssl进行PKCS8编码)
	 * */
	  public final static String DEV_PRIVATE_KEY =
			  "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBANNmKYz0JAibPPf0"+ "\n" +
			  "oaFAGEqe8uyYz3e/sKyjlouVkrue3XY0f5KFpTz4ZHo8496T/ZjeHlcw4GIgfU31"+ "\n" +
			  "aYEwQNGVs2MYiD56lQ/qBQs+XnjW12ZUOkyxtx49e5J350qt3kG+P7jKE8RdqFO3"+ "\n" +
			  "ahFvDP5l5AzYfvT+zgVPkLq9vEWtAgMBAAECgYAwNDuFSKZuz/c3EzFH87WWTmhW"+ "\n" +
			  "SwID7FH9C9BoQ9MRtUgKjC4K2y6ymHFQ7lGHj3dkREHm395Kgd4QyMUmEDq3JZJg"+ "\n" +
			  "/evzKCIFhRA3r/0C2iIxJt0iDAZCe+cULTeGOJZONJ2lUZ/MpZguDLvlTQMGRyAa"+ "\n" +
			  "m4jJOEVyj2bo3kW2gQJBAPRy4/jYzHwsJ8m39KOhdEM2XQGvw3Lb2Jz+5NAImKJD"+ "\n" +
			  "2eAASmxnIwms8WtpdDDI2yXwXgZ9rQwM3BAnmil3N8cCQQDdY3e3244ntWsFEWRx"+ "\n" +
			  "RTEhhW8g9dOkIKRsy1c+az6mzCx6RqlOySpH1t4Z8pnNMtBM/7aRz1CzdmxREHRc"+ "\n" +
			  "p17rAkBPzFmjWJKYTonGjeisqf4cGtkNveTdz+rMhWEIkGXTQrcTKsUg0iJb/Drr"+ "\n" +
			  "R4eIjWkM34SyNcCR2HjWRTdMJgDtAkEAlxSuOnZGiKqxof/Af9wsLygUMnYQPE1a"+ "\n" +
			  "aDRMEXi2hLWJFNjr6aw2glgLscFxXCt1I1bOjKrh89a5DIkiH6jIiQJAG+GDPKL6"+ "\n" +
			  "5UiBWwFqZE1qV6A1KFQ2LTS9ME7t3Qp5HfjeCyw8xkftcUig0ER5XvgmKgzRKbRl"+ "\n" +
			  "42BH7CLGeFQ0MQ=="+ "\n";
	 

	/**
	 * 公钥
	 **/
	  public final static String HMB_PUBLIC_KEY =
		  "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDIvSyeSJcyj0qIesw/Kx5iWnOB"+ "\n" +
		  "ZlRLfRLepvDTqvRSleyYCV8W/475b2I53aI6dTUoPd0IBX2t3v8wEDE/MgZxGSVd"+ "\n" +
		  "n8EF/nD/xSJzPxqeL9YZ3GxWXJoJekfUQZoDkjK+aWvxYdIKSEULuBipSWFaMB8P"+ "\n" +
		  "vw1mHQcRI0etyFDVGwIDAQAB" + "\n";
	

	  
	  
	  /**
	   * RSA加密方法
	   * @param data 待加密数据
	   * @param publicKey 公钥字符串
	   * @return
	   * @throws Exception
	   */
	    public static String encryptByPublicKey(byte[] data, String publicKey)  
	            throws Exception {  
	        byte[] keyBytes = Base64Util.decode(publicKey);  
	        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);  
	        KeyFactory keyFactory = KeyFactory.getInstance("RSA");  
	        Key publicK = keyFactory.generatePublic(x509KeySpec);  
	        // 对数据加密  
	        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");  
	        cipher.init(Cipher.ENCRYPT_MODE, publicK);  
	        int inputLen = data.length;  
	        ByteArrayOutputStream out = new ByteArrayOutputStream();  
	        int offSet = 0;  
	        byte[] cache;  
	        int i = 0;  
	        // 对数据分段加密  
	        while (inputLen - offSet > 0) {  
	            if (inputLen - offSet > 117) {  
	                cache = cipher.doFinal(data, offSet, 117);  
	            } else {  
	                cache = cipher.doFinal(data, offSet, inputLen - offSet);  
	            }  
	            out.write(cache, 0, cache.length);  
	            i++;  
	            offSet = i * 117;  
	        }  
	        byte[] encryptedData = out.toByteArray();  
	        out.close();  
	        return Base64Util.encode(encryptedData);
	    }  
	    
	    /**
		   * RSA解密方法
		   * @param data 待解密数据
		   * @param publicKey 私钥字符串
		   * @return
		   * @throws Exception
		   */
	    public static String decryptByPrivateKey(String encryptedString, String privateKey)  
	            throws Exception {  
	    	byte[] encryptedData = Base64Util.decode(encryptedString);
 	        byte[] keyBytes = Base64Util.decode(privateKey);  
	        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);  
	        KeyFactory keyFactory = KeyFactory.getInstance("RSA");  
	        Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);  
	        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding"); 
	        cipher.init(Cipher.DECRYPT_MODE, privateK);  
	        int inputLen = encryptedData.length;  
	        ByteArrayOutputStream out = new ByteArrayOutputStream();  
	        int offSet = 0;  
	        byte[] cache;  
	        int i = 0;  
	        // 对数据分段解密  
	        while (inputLen - offSet > 0) {  
	            if (inputLen - offSet > 128) {  
	                cache = cipher.doFinal(encryptedData, offSet, 128);  
	            } else {  
	                cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);  
	            }  
	            out.write(cache, 0, cache.length);  
	            i++;  
	            offSet = i * 128;  
	        }  
	        byte[] decryptedData = out.toByteArray();  
	        out.close();  
	        return new String(decryptedData,"UTF-8");  
	    } 

}
