package wgh.dubbo.extend.service.server.impl.weixin.utils;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

/**
 * Http客户端工具类<br/>
 * 这是内部调用类，请不要在外部调用。
 * 
 * @author miklchen
 *
 */
public class HttpClientUtil
{

	public static final String SunX509 = "SunX509";
	public static final String JKS = "JKS";
	public static final String PKCS12 = "PKCS12";
	public static final String TLS = "TLS";

	/**
	 * get HttpURLConnection
	 * 
	 * @param strUrl
	 *            url地址
	 * @return HttpURLConnection
	 * @throws IOException
	 */
	public static HttpURLConnection getHttpURLConnection(String strUrl) throws IOException
	{
		URL url = new URL(strUrl);
		HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
		return httpURLConnection;
	}

	/**
	 * get HttpsURLConnection
	 * 
	 * @param strUrl
	 *            url地址
	 * @return HttpsURLConnection
	 * @throws IOException
	 */
	public static HttpsURLConnection getHttpsURLConnection(String strUrl) throws IOException
	{
		URL url = new URL(strUrl);
		HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();
		return httpsURLConnection;
	}

	/**
	 * 获取不带查询串的url
	 * 
	 * @param strUrl
	 * @return String
	 */
	public static String getURL(String strUrl)
	{

		if (null != strUrl)
		{
			int indexOf = strUrl.indexOf("?");
			if (-1 != indexOf)
			{
				return strUrl.substring(0, indexOf);
			}

			return strUrl;
		}

		return strUrl;

	}

	/**
	 * 获取查询串
	 * 
	 * @param strUrl
	 * @return String
	 */
	public static String getQueryString(String strUrl)
	{

		if (null != strUrl)
		{
			int indexOf = strUrl.indexOf("?");
			if (-1 != indexOf)
			{
				return strUrl.substring(indexOf + 1, strUrl.length());
			}

			return "";
		}

		return strUrl;
	}

	/**
	 * 查询字符串转换成Map<br/>
	 * name1=key1&name2=key2&...
	 * 
	 * @param queryString
	 * @return
	 */
	public static Map queryString2Map(String queryString)
	{
		if (null == queryString || "".equals(queryString))
		{
			return null;
		}

		Map m = new HashMap();
		String[] strArray = queryString.split("&");
		for (int index = 0; index < strArray.length; index++)
		{
			String pair = strArray[index];
			HttpClientUtil.putMapByPair(pair, m);
		}

		return m;

	}

	/**
	 * 把键值添加至Map<br/>
	 * pair:name=value
	 * 
	 * @param pair
	 *            name=value
	 * @param m
	 */
	public static void putMapByPair(String pair, Map m)
	{

		if (null == pair || "".equals(pair))
		{
			return;
		}

		int indexOf = pair.indexOf("=");
		if (-1 != indexOf)
		{
			String k = pair.substring(0, indexOf);
			String v = pair.substring(indexOf + 1, pair.length());
			if (null != k && !"".equals(k))
			{
				m.put(k, v);
			}
		} else
		{
			m.put(pair, "");
		}
	}

	/**
	 * BufferedReader转换成String<br/>
	 * 注意:流关闭需要自行处理
	 * 
	 * @param reader
	 * @return String
	 * @throws IOException
	 */
	public static String bufferedReader2String(BufferedReader reader) throws IOException
	{
		StringBuffer buf = new StringBuffer();
		String line = null;
		while ((line = reader.readLine()) != null)
		{
			buf.append(line);
			buf.append("\r\n");
		}

		return buf.toString();
	}

	/**
	 * 处理输出<br/>
	 * 注意:流关闭需要自行处理
	 * 
	 * @param out
	 * @param data
	 * @param len
	 * @throws IOException
	 */
	public static void doOutput(OutputStream out, byte[] data, int len) throws IOException
	{
		int dataLen = data.length;
		int off = 0;
		while (off < dataLen)
		{
			if (len >= dataLen)
			{
				out.write(data, off, dataLen);
			} else
			{
				out.write(data, off, len);
			}

			// 刷新缓冲区
			out.flush();

			off += len;

			dataLen -= len;
		}

	}

	/**
	 * 获取SSLContext
	 * 
	 * @param trustFile
	 * @param trustPasswd
	 * @param keyFile
	 * @param keyPasswd
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws KeyStoreException
	 * @throws IOException
	 * @throws CertificateException
	 * @throws UnrecoverableKeyException
	 * @throws KeyManagementException
	 */
	public static SSLContext getSSLContext(FileInputStream trustFileInputStream, String trustPasswd, FileInputStream keyFileInputStream, String keyPasswd) throws NoSuchAlgorithmException, KeyStoreException, CertificateException, IOException, UnrecoverableKeyException, KeyManagementException
	{

		// ca
		TrustManagerFactory tmf = TrustManagerFactory.getInstance(HttpClientUtil.SunX509);
		KeyStore trustKeyStore = KeyStore.getInstance(HttpClientUtil.JKS);
		trustKeyStore.load(trustFileInputStream, HttpClientUtil.str2CharArray(trustPasswd));
		tmf.init(trustKeyStore);

		final char[] kp = HttpClientUtil.str2CharArray(keyPasswd);
		KeyManagerFactory kmf = KeyManagerFactory.getInstance(HttpClientUtil.SunX509);
		KeyStore ks = KeyStore.getInstance(HttpClientUtil.PKCS12);
		ks.load(keyFileInputStream, kp);
		kmf.init(ks, kp);

		SecureRandom rand = new SecureRandom();
		SSLContext ctx = SSLContext.getInstance(HttpClientUtil.TLS);
		ctx.init(kmf.getKeyManagers(), tmf.getTrustManagers(), rand);

		return ctx;
	}

	/**
	 * 获取CA证书信息
	 * 
	 * @param cafile
	 *            CA证书文件
	 * @return Certificate
	 * @throws CertificateException
	 * @throws IOException
	 */
	public static Certificate getCertificate(File cafile) throws CertificateException, IOException
	{
		CertificateFactory cf = CertificateFactory.getInstance("X.509");
		FileInputStream in = new FileInputStream(cafile);
		Certificate cert = cf.generateCertificate(in);
		in.close();
		return cert;
	}

	/**
	 * 字符串转换成char数组
	 * 
	 * @param str
	 * @return char[]
	 */
	public static char[] str2CharArray(String str)
	{
		if (null == str)
			return null;

		return str.toCharArray();
	}

	/**
	 * 存储ca证书成JKS格式
	 * 
	 * @param cert
	 * @param alias
	 * @param password
	 * @param out
	 * @throws KeyStoreException
	 * @throws NoSuchAlgorithmException
	 * @throws CertificateException
	 * @throws IOException
	 */
	public static void storeCACert(Certificate cert, String alias, String password, OutputStream out) throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException
	{
		KeyStore ks = KeyStore.getInstance("JKS");

		ks.load(null, null);

		ks.setCertificateEntry(alias, cert);

		// store keystore
		ks.store(out, HttpClientUtil.str2CharArray(password));

	}

	public static InputStream String2Inputstream(String str)
	{
		return new ByteArrayInputStream(str.getBytes());
	}

	/**
	 * 以get方式请求
	 * 
	 * @param url
	 * @param charset
	 * @return
	 * @throws Exception
	 */
	public static String doGet(String url, String charset) throws Exception
	{
		HttpClient httpClient = new HttpClient();
		httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(30000);
		httpClient.getHttpConnectionManager().getParams().setSoTimeout(30000);
		GetMethod getMethod = new GetMethod(url);
		getMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, charset);
		int code = httpClient.executeMethod(getMethod);
		if (code != 200)
		{
			return null;
		}
		StringBuffer resContent = new StringBuffer();
		InputStream inputStream = null;
		inputStream = getMethod.getResponseBodyAsStream();
		BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, charset));
		String tempStr = null;
		while ((tempStr = reader.readLine()) != null)
		{
			resContent.append(tempStr);
		}
		getMethod.releaseConnection();
		return resContent.toString();
	}

	/**
	 * POST方式请求
	 * 
	 * @param url
	 *            请求地址
	 * @param params
	 *            请求参数
	 * @param charset
	 *            请求编码
	 * @return
	 * @throws Exception
	 */
	public static String doPost(String url, Map<String, String> params, String charset) throws Exception
	{
		HttpClient httpClient = new HttpClient();
		httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(30000);
		httpClient.getHttpConnectionManager().getParams().setSoTimeout(30000);
		PostMethod post = new PostMethod(url);
		// 设置参数
		Iterator<Entry<String, String>> iterator = params.entrySet().iterator();

		NameValuePair[] nvps = new NameValuePair[params.size()];
		int index = 0;
		while (iterator.hasNext())
		{
			Entry<String, String> elem = iterator.next();
			NameValuePair nvp = new NameValuePair(elem.getKey(), elem.getValue());
			nvps[index++] = nvp;
		}
		post.setRequestBody(nvps);
		post.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, charset);
		int statuscode = httpClient.executeMethod(post);
		if ((statuscode == HttpStatus.SC_MOVED_TEMPORARILY) || (statuscode == HttpStatus.SC_MOVED_PERMANENTLY) || (statuscode == HttpStatus.SC_SEE_OTHER) || (statuscode == HttpStatus.SC_TEMPORARY_REDIRECT))
		{
			// 读取新的 URL 地址
			Header header = post.getResponseHeader("location");
			if (header != null)
			{
				String newuri = header.getValue();
				if ((newuri == null) || (newuri.equals("")))
					newuri = "/";
				GetMethod redirect = new GetMethod(newuri);
				httpClient.executeMethod(redirect);
				String responseTemp = redirect.getResponseBodyAsString();
				redirect.releaseConnection();
				return responseTemp;
			} else
				throw new Exception();
		}
		String responseBodyAsString = post.getResponseBodyAsString();
		post.releaseConnection();
		return responseBodyAsString;
	}
	/**
	 * POST方式请求获取重定向地址
	 * 
	 * @param url
	 *            请求地址
	 * @param params
	 *            请求参数
	 * @param charset
	 *            请求编码
	 */
	public static String doPostGetRedirectAddress(String url, Map<String, String> params, String charset) throws Exception
	{
		HttpClient httpClient = new HttpClient();
		httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(30000);
		httpClient.getHttpConnectionManager().getParams().setSoTimeout(30000);
		PostMethod post = new PostMethod(url);
		// 设置参数
		Iterator<Entry<String, String>> iterator = params.entrySet().iterator();
		NameValuePair[] nvps = new NameValuePair[params.size()];
		int index = 0;
		while (iterator.hasNext())
		{
			Entry<String, String> elem = iterator.next();
			NameValuePair nvp = new NameValuePair(elem.getKey(), elem.getValue());
			nvps[index++] = nvp;
		}
		post.setRequestBody(nvps);
		post.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, charset);
		int statuscode = httpClient.executeMethod(post);
		if ((statuscode == HttpStatus.SC_MOVED_TEMPORARILY) || (statuscode == HttpStatus.SC_MOVED_PERMANENTLY) || (statuscode == HttpStatus.SC_SEE_OTHER) || (statuscode == HttpStatus.SC_TEMPORARY_REDIRECT))
		{
			// 读取新的 URL 地址
			Header header = post.getResponseHeader("location");
			if (header != null)
			{
				String newuri = header.getValue();
				if ((newuri == null) || (newuri.equals("")))
					newuri = "/";
				 return newuri;
			}
		}
		return url;
	}
	// 汉字范围 (中文)
	public static String chinaToUnicode(String str)
	{
		String result = "";
		for (int i = 0; i < str.length(); i++)
		{
			int chr1 = (char) str.charAt(i);
			if (chr1 >= 19968 && chr1 <= 171941)
			{
				result += "\\u" + Integer.toHexString(chr1);
			} else
			{
				result += str.charAt(i);
			}
		}
		return result;
	}

	public static void main(String[] args) throws Exception
	{
		Map<String, String> params = new HashMap<String, String>();
		// ?token=&isExit=false
		params.put("token", "B/5ZSDEKKztanmUCoOJuM63r1CWiH6P9VZIzmB3KqsOfC7eEzdlY5Q==");
		params.put("isExit", "false");
		String url = "http://121.41.117.81:8080/fmb-openapi-server/rest/userEachHelp/join_support_plan_info";
		System.out.println(doPost(url, params, "utf-8"));

	}
}
