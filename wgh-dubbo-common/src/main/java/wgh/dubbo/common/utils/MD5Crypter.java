package wgh.dubbo.common.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;

/**
 * @author yinlinsheng
 * MD5默认使用GBK编码
 *
 */
public class MD5Crypter {
	
	private final static String DEFAULT_CHARSET = "gbk";
	
	private final static String UTF8 = "utf8";

	//============================= gbk ===========================
	/**
	 * MD5散列，32位大写，默认按GBK取位。
	 * @param originalString
	 * @return
	 */
	public static String MD5Encode(String originalString) {
		if (Utils.isEmpty(originalString)) {
			return null;
		}
		return MD5Encode(originalString, DEFAULT_CHARSET).toUpperCase();
	}
	
	/**
	 * MD5散列，32位小写，默认按GBK取位。
	 * @param originalString
	 * @return
	 */
	public static String MD5EncodeToLowerCase(String originalString) {
		if (Utils.isEmpty(originalString)) {
			return null;
		}
		return MD5Encode(originalString, DEFAULT_CHARSET).toLowerCase();
	}
	
	/**
	 * 
	 * @param originalString
	 * @param charset
	 * @return
	 */
	public static String MD5Encode(String originalString, String charset) {
		if (Utils.isEmpty(originalString)) {
			return null;
		}

		try {
			return DigestUtils.md5Hex(originalString.getBytes(charset)).toString();
		} catch (UnsupportedEncodingException e) {
			return null;
		}
	}
	
	//============================= UTF-8 ===========================
	/**
	 * MD5散列原文 -- UTF-8 大写32位
	 * @param originalString
	 * @return
	 */
	public static String MD5EncodeForUtf8(String originalString) {
		if (Utils.isEmpty(originalString)) {
			return null;
		}

		try {
			return DigestUtils.md5Hex(originalString.getBytes(UTF8)).toString().toUpperCase();
		} catch (UnsupportedEncodingException e) {
			return null;
		}
	}
	
	/**
	 * MD5散列原文 -- UTF-8 小写32位
	 * @param originalString
	 * @return
	 */
	public static String MD5EncodeForUtf8LC(String originalString) {
		if (Utils.isEmpty(originalString)) {
			return null;
		}

		try {
			return DigestUtils.md5Hex(originalString.getBytes(UTF8)).toString().toLowerCase();
		} catch (UnsupportedEncodingException e) {
			return null;
		}
	}

	/**
	 * MD5散列原文数组，串列所有原文数组 大写32位。
	 * @param originalStrings
	 * @return
	 */
	public static String MD5EncodeForUtf8(String... originalStrings) {
		if (Utils.isNull(originalStrings)) {
			return null;
		}

		StringBuffer strCache = new StringBuffer();

		for (int i = 0; i < originalStrings.length; i++) {
			strCache.append(originalStrings[i]);
		}

		return MD5EncodeForUtf8(strCache.toString());
	}
	
	/**
	 * MD5散列原文数组，串列所有原文数组 小写32位。
	 * @param originalStrings
	 * @return
	 */
	public static String MD5EncodeForUtf8LC(String... originalStrings) {
		if (Utils.isNull(originalStrings)) {
			return null;
		}

		StringBuffer strCache = new StringBuffer();

		for (int i = 0; i < originalStrings.length; i++) {
			strCache.append(originalStrings[i]);
		}

		return MD5EncodeForUtf8LC(strCache.toString());
	}
	
	/**
	 * 先MD5散列成16进制,再进行Base64加密
	 * @param originalStrings
	 * @return
	 */
	public final static String MD5andBase64(String originalStrings) {
		try {
			byte[] strTemp = originalStrings.getBytes();
			MessageDigest mdTemp = MessageDigest.getInstance("MD5");
			mdTemp.update(strTemp);
			byte[] md = mdTemp.digest();
			return new String(Base64.encodeBase64(md));
		} catch (Exception e) {
			return null;
		}
	}

	public static void main(String[] args) {
		System.out.println(MD5Encode("337ouernnj2015"));
	}
}
