package wgh.dubbo.common.utils;

import java.io.IOException;
import java.net.URLEncoder;
import java.security.SecureRandom;

import javassist.bytecode.ByteArray;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.DESedeKeySpec;
import javax.sql.rowset.serial.SerialArray;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.util.ByteArrayBuffer;
import org.apache.zookeeper.server.util.SerializeUtils;
import org.springframework.util.StringUtils;

import com.alibaba.dubbo.common.io.Bytes;
import com.sun.org.apache.commons.beanutils.converters.StringArrayConverter;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;


/**
 * 
 * @author yinls
 * 3DES加密解密
 *
 */
public class TripleDESCrypter {
	private final static String DES = "DES";
	/**
	 * 3DESECB加密,key必须是长度大于等于 3*8 = 24 位
	 * @param src：数据源
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static String encryptThreeDESECB(String src, String key) throws Exception {
		//从原始密匙数据创建一个DESedeKeySpec对象 
		DESedeKeySpec dks = new DESedeKeySpec(key.getBytes("UTF-8")); 
		// 创建一个密匙工厂，然后用它把DESKeySpec对象转换成
		// 一个SecretKey对象
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");  
	    SecretKey securekey = keyFactory.generateSecret(dks);  
	    // Cipher对象实际完成解密操作
	    Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");  
	    cipher.init(Cipher.ENCRYPT_MODE, securekey);  
	    byte[] retByte=cipher.doFinal(src.getBytes());
	    //Base64.encode(retByte, Base64.DEFAULT);
		return new String(Base64.encodeBase64(retByte));
	}
	
	/**
	 * 3DESECB解密,key必须是长度大于等于 3*8 = 24 位
	 * @param src：数据源
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static String decryptThreeDESECB(String src, String key) throws Exception {
		//--解密的key  
	    DESedeKeySpec dks = new DESedeKeySpec(key.getBytes("UTF-8"));  
	    SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");  
	    SecretKey securekey = keyFactory.generateSecret(dks);  
	      
	    //--Chipher对象解密  
	    Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");  
	    cipher.init(Cipher.DECRYPT_MODE, securekey);
	    byte[] retByte = cipher.doFinal(Base64.decodeBase64(Bytes.base642bytes(src)));
	    
	    return new String(retByte);
	}
	
	/**
     * Description 根据键值进行加密
     * @param data 
     * @param key  加密键byte数组
     * @return
     * @throws Exception
     */
    public static String encrypt(String data, String key) throws Exception {
        byte[] bt = encrypt(data.getBytes(), key.getBytes());
        String strs = new BASE64Encoder().encode(bt);
        return strs;
    }

    /**
     * Description 根据键值进行解密
     * @param data
     * @param key  加密键byte数组
     * @return
     * @throws IOException
     * @throws Exception
     */
    public static String decrypt(String data, String key) throws IOException,Exception {
        if (data == null)
            return null;
        BASE64Decoder decoder = new BASE64Decoder();
        byte[] buf = decoder.decodeBuffer(data);
        byte[] bt = decrypt(buf,key.getBytes());
        return new String(bt);
    }
 
    /**
     * Description 根据键值进行加密
     * @param data
     * @param key  加密键byte数组
     * @return
     * @throws Exception
     */
    private static byte[] encrypt(byte[] data, byte[] key) throws Exception {
        // 生成一个可信任的随机数源
        SecureRandom sr = new SecureRandom();
 
        // 从原始密钥数据创建DESKeySpec对象
        DESKeySpec dks = new DESKeySpec(key);
 
        // 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
        SecretKey securekey = keyFactory.generateSecret(dks);
 
        // Cipher对象实际完成加密操作
        Cipher cipher = Cipher.getInstance(DES);
 
        // 用密钥初始化Cipher对象
        cipher.init(Cipher.ENCRYPT_MODE, securekey, sr);
 
        return cipher.doFinal(data);
    }
     
     
    /**
     * Description 根据键值进行解密
     * @param data
     * @param key  加密键byte数组
     * @return
     * @throws Exception
     */
    private static byte[] decrypt(byte[] data, byte[] key) throws Exception {
        // 生成一个可信任的随机数源
        SecureRandom sr = new SecureRandom();
 
        // 从原始密钥数据创建DESKeySpec对象
        DESKeySpec dks = new DESKeySpec(key);
 
        // 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
        SecretKey securekey = keyFactory.generateSecret(dks);
 
        // Cipher对象实际完成解密操作
        Cipher cipher = Cipher.getInstance(DES);
 
        // 用密钥初始化Cipher对象
        cipher.init(Cipher.DECRYPT_MODE, securekey, sr);
 
        return cipher.doFinal(data);
    }


	
    public static void main(String[] args) throws Exception {
//    	System.out.println(TripleDESCrypter.encryptThreeDESECB("111", "national"));
//    	System.out.println(TripleDESCrypter.decryptThreeDESECB("zcQjNUvaRnU=", "ABCDABCDABCD123412341234"));
//    	System.out.println(TripleDESCrypter.encrypt("111", "national"));
    	String xmString = new String("国家人口".getBytes("UTF-8")); 
    	System.out.println("utf-8 编码：" + xmString) ;  
    	String xmlUTF8 = URLEncoder.encode(xmString, "UTF-8"); 
    	System.out.println("utf-8 编码：" + xmlUTF8) ;  
    }  
}