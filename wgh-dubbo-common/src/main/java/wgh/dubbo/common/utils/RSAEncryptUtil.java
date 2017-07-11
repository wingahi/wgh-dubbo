package wgh.dubbo.common.utils;

import java.io.ByteArrayOutputStream;
import java.security.Key;
import java.security.KeyFactory;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

public class RSAEncryptUtil {
 
	/**
	 * 开发者私钥 (注意：JAVA使用的是PKCS8的编码，如果私钥不是PKCK8的编码，请使用Openssl进行PKCS8编码)
	 * */
	  public final static String DEV_PRIVATE_KEY ="";

	/**
	 * 公钥
	 **/
	  public final static String HMB_PUBLIC_KEY ="";
	

	  
	  
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
