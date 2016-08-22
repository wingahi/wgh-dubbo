package wgh.dubbo.common.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.Header;
import org.apache.http.HttpException;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.routing.HttpRoutePlanner;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;



/**
 * 支持SSL，Proxy，POST/GET的WebClient (使用HttpClient 4.x)
 *
 */
@SuppressWarnings("deprecation")
public class WebClient {

	private static final Log log = LogFactory.getLog(WebClient.class);
	
	public enum HTTPMethod {
		GET, POST
	}

	private DefaultHttpClient httpClient = new DefaultHttpClient();
	private String url;
	private HTTPMethod method;
	private byte[] content;
	private Map<String, String> headers = new HashMap<String, String>();
	private int responseCode;
	private List<NameValuePair> postParameter = new ArrayList<NameValuePair>();

	private static final Pattern pageEncodingReg = Pattern.compile("content-type.*charset=([^\">\\\\]+)", Pattern.CASE_INSENSITIVE);
	private static final Pattern headerEncodingReg = Pattern.compile("charset=(.+)", Pattern.CASE_INSENSITIVE);

	private String requestBody;
	
	// default with POST
	public WebClient(String url) {
		this(url, HTTPMethod.POST, false, null, 0, false, null, null, null);
	}
	
	// Without proxy
	public WebClient(String url, HTTPMethod method) {
		this(url, method, false, null, 0, false, null, null, null);
	}

	// Proxy without auth
	public WebClient(String url, HTTPMethod method, String proxyHost,
			int proxyPort) {
		this(url, method, true, proxyHost, proxyPort, false, null, null, null);
	}

	// All in one settings
	public WebClient(String url, HTTPMethod method, boolean useProxy,
			String proxyHost, int proxyPort, boolean needAuth, String username,
			String password, String nonProxyReg) {
		setUrl(url);
		setMethod(method);
		if (useProxy) {
			enableProxy(proxyHost, proxyPort, needAuth, username, password, nonProxyReg);
		}
	}

	public void setMethod(HTTPMethod method) {
		this.method = method;
	}
	
	public HTTPMethod getMethod() {
		return this.method;
	}

	public void setUrl(String url) {
		if (isStringEmpty(url)) {
			throw new RuntimeException("[Error] url is empty!");
		}
		this.url = url;
		headers.clear();
		responseCode = 0;
		postParameter.clear();
		content = null;
		if (url.startsWith("https://")) {
			enableSSL();
		} else {
			disableSSL();
		}
	}

	public Map<String, String> getRequestHeaders() {
		return headers;
	}

	public void addPostParameter(String name, String value) {
		this.postParameter.add(new BasicNameValuePair(name, value));
	}

	public void setTimeout(int connectTimeout, int readTimeout) {
		HttpParams params = httpClient.getParams();
		HttpConnectionParams.setConnectionTimeout(params, connectTimeout);
		HttpConnectionParams.setSoTimeout(params, readTimeout);
	}

	private void enableSSL() {
		try {
			SSLContext sslcontext = SSLContext.getInstance("TLS");
			sslcontext.init(null, new TrustManager[] { truseAllManager }, null);
			SSLSocketFactory sf = new SSLSocketFactory(sslcontext);
			sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
			Scheme https = new Scheme("https", sf, 443);
			httpClient.getConnectionManager().getSchemeRegistry().register(https);
		} catch (KeyManagementException e) {
			log.error(e.getMessage(), e);
		} catch (NoSuchAlgorithmException e) {
			log.error(e.getMessage(), e);
		}
	}

	private void disableSSL() {
		SchemeRegistry reg = httpClient.getConnectionManager().getSchemeRegistry();
		if (reg.get("https") != null) {
			reg.unregister("https");
		}
	}

	public void disableProxy() {
		httpClient.getCredentialsProvider().clear();
		httpClient.setRoutePlanner(null);
	}

	public void enableProxy(final String proxyHost, final int proxyPort,
			boolean needAuth, String username, String password,
			final String nonProxyHostRegularExpression) {
		if (needAuth) {
			httpClient.getCredentialsProvider().setCredentials(
					new AuthScope(proxyHost, proxyPort),
					new UsernamePasswordCredentials(username, password));
		}
		// Simple proxy setting, can't handle non-proxy-host
		// httpClient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY,new
		// HttpHost(proxyHost, proxyPort));
		httpClient.setRoutePlanner(new HttpRoutePlanner() {
			@Override
			public HttpRoute determineRoute(HttpHost target,
					HttpRequest request, HttpContext contenxt)
					throws HttpException {
				HttpRoute proxyRoute = new HttpRoute(target, null,
						new HttpHost(proxyHost, proxyPort), "https".equalsIgnoreCase(target.getSchemeName()));
				if (nonProxyHostRegularExpression == null) {
					return proxyRoute;
				}
				Pattern pattern = Pattern.compile(nonProxyHostRegularExpression,
								Pattern.CASE_INSENSITIVE);
				Matcher m = pattern.matcher(target.getHostName());
				if (m.find()) {
					return new HttpRoute(target, null, target, "https".equalsIgnoreCase(target.getSchemeName()));
				} else {
					return proxyRoute;
				}
			}
		});
	}

	private void fetch() throws IOException {
		if (url == null || method == null) {
			throw new RuntimeException(
					"Fetch exception: URL and Method is null");
		}
		httpClient.getParams().setParameter(ClientPNames.COOKIE_POLICY,
				CookiePolicy.BROWSER_COMPATIBILITY);
		HttpResponse response = null;
		HttpUriRequest req = null;
		if (method.equals(HTTPMethod.GET)) {
			req = new HttpGet(url);
		} else {
			req = new HttpPost(url);
			if(Utils.isNotEmpty(requestBody)){
				((HttpPost) req).setEntity(new StringEntity(requestBody));
			}else{
				((HttpPost) req).setEntity(new UrlEncodedFormEntity(
						this.postParameter, "UTF-8"));
			}
		}
		for (Entry<String, String> e : headers.entrySet()) {
			req.addHeader(e.getKey(), e.getValue());
		}

		//
		// Turn off "except" http header, some proxy server and web server do
		// not support it, may cause "417 Expectation Failed"
		//
		// HttpClient's doc says: 100-continue handshake should be used with
		// caution, as it may cause problems with HTTP servers and proxies that
		// do not support HTTP/1.1 protocol.
		//
		req.getParams().setBooleanParameter(CoreProtocolPNames.USE_EXPECT_CONTINUE, false);
		response = httpClient.execute(req);
		Header[] header = response.getAllHeaders();
		headers.clear();
		for (Header h : header) {
			headers.put(h.getName(), h.getValue());
		}
		content = EntityUtils.toByteArray(response.getEntity());
		responseCode = response.getStatusLine().getStatusCode();
	}

	private boolean isStringEmpty(String s) {
		return s == null || s.trim().length() == 0;
	}

	public int getResponseCode() throws IOException {
		if (responseCode == 0) {
			fetch();
		}
		return responseCode;
	}

	public Map<String, String> getResponseHeaders() throws IOException {
		if (responseCode == 0) {
			fetch();
		}
		return headers;
	}

	public byte[] getByteArrayContent() throws IOException {
		if (content == null) {
			fetch();
		}
		return content;
	}

	public String getTextContent() throws IOException {
		if (content == null) {
			fetch();
		}
		if (content == null) {
			throw new RuntimeException("[Error] Can't fetch content!");
		}
		String headerContentType = null;
		if ((headerContentType = headers.get("Content-Type")) != null) {
			// use http header encoding
			Matcher m1 = headerEncodingReg.matcher(headerContentType);
			if (m1.find()) {
				return new String(content, m1.group(1));
			}
		}
		// Use html's encoding
		String html = new String(content);
		Matcher m2 = pageEncodingReg.matcher(html);
		if (m2.find()) {
			html = new String(content, m2.group(1));
		}else {
			html = new String(content, "utf-8");
		}

		return html;
	}

	public DefaultHttpClient getHttpClient() {
		return httpClient;
	}

	// SSL handler (ignore untrusted hosts)
	private static TrustManager truseAllManager = new X509TrustManager() {
		@Override
		public X509Certificate[] getAcceptedIssuers() {
			return null;
		}

		@Override
		public void checkServerTrusted(X509Certificate[] chain, String authType)
				throws CertificateException {
		}

		@Override
		public void checkClientTrusted(X509Certificate[] chain, String authType)
				throws CertificateException {
		}
	};
	
	
	public String getRequestBody() {
		return requestBody;
	}

	public void setRequestBody(String requestBody) {
		this.requestBody = requestBody;
	}

	// 测试
	public static void main(String[] args) throws Exception { 
		//String url = "http://api.seebaobei.com/Getdeviceinfo.aspx";
		//String url = "http://14.31.15.16/jxpt_alpha/SyncData/Service/Crm/impl_user.ashx";
		/*String url = "http://14.31.15.16/jxpt_alpha/SyncData/Service/Crm/impl_user.ashx";
		String url = "http://14.31.15.16/jxpt_alpha/SyncData/Service/Crm/impl_user.ashx";
		WebClient web = new WebClient(url, HTTPMethod.POST);
		web.addPostParameter("streaming_no", "10001");
		web.addPostParameter("app_code", "140201516002");
		web.addPostParameter("parent_mobile", "13099998888");
		web.addPostParameter("parent_name", "洪武帝");
		web.addPostParameter("biz_id", "9896");
		web.addPostParameter("class_id", "635400904435334833");
		web.addPostParameter("student_name", "建文帝");
		web.addPostParameter("province_code", "GX");
		web.addPostParameter("version", "1.0");
		web.addPostParameter("hashcode", "f7e1a326c83d23a1188929768f83318b");

		web.addPostParameter("deviceid", "MKHNN13C03586");
		web.addPostParameter("timestamp", Utils.getTimeStamp2String());

		
		System.out.println(url + " -> return \n" + web.getTextContent());*/
//		String url = "http://14.31.15.16/jxpt_alpha/SyncData/Service/Crm/impl_class.ashx";
//		WebClient web = new WebClient(url, HTTPMethod.POST);
//		web.addPostParameter("timestamp", "20140707151023");
//		web.addPostParameter("app_code", "140201516002");
//		web.addPostParameter("parent_mobile", "18912345678");
//		web.addPostParameter("mobile_type", "1");
//		web.addPostParameter("parent_name", "牛二家长");
//		web.addPostParameter("student_name", "牛二");
//		web.addPostParameter("biz_id", "9896");
//		web.addPostParameter("class_id", "635400904435334833");		
//		web.addPostParameter("class_name", "二（1）班");
//		web.addPostParameter("grade_name", "二年级");
//		web.addPostParameter("province_code", "GX");
//		web.addPostParameter("version", "1.0");
//		web.addPostParameter("hashcode", "0A6E2BB5986CFEE9FBCE16681990B4AC");
		
		String url = "http://14.31.15.16/jxpt_alpha/SyncData/Service/REST/message_list.ashx";
		WebClient web = new WebClient(url, HTTPMethod.POST);
		web.addPostParameter("timestamp", "20140912110027");
		web.addPostParameter("caller", "yxtfj");
		web.addPostParameter("hashcode", "FDCE1CD8BB4D27DB82633A256A3AA52A");
		web.addPostParameter("message_id", "0");
		web.addPostParameter("user_id", "635422470753055135"); 
		web.addPostParameter("row_count", "10");
		
		String resStr = web.getTextContent();
		System.out.println(url + " -> return \n" + web.getTextContent());
		
		JSONObject jsonObj = JSON.parseObject(resStr);
		
		System.out.println(JSON.toJSONString(jsonObj));
	}
}