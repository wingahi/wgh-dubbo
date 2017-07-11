/**
 * 
 */
package wgh.dubbo.common.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;

/**
 * @author 文观海 股票信息请求工具类
 */
public class StockUtil {

	static String httpUrl = "http://apis.baidu.com/apistore/stockservice/stock";
	static String apiKey = "";

	/**
	 * 请求股票数据
	 * 
	 * @param gupiao
	 *            股票代码
	 * @param list
	 *            列表类型
	 * @return json字符串
	 */
	public static String requestStockJson(String stockId, String list) {
		BufferedReader reader = null;
		String result = null;
		StringBuffer sbf = new StringBuffer();
		String _httpUrl = httpUrl + "?stockid=" + stockId + "&list=" + list;

		try {
			URL url = new URL(_httpUrl);
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setRequestMethod("GET");
			// 填入apikey到HTTP header
			connection.setRequestProperty("apikey", apiKey);
			connection.connect();
			InputStream is = connection.getInputStream();
			reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			String strRead = null;
			while ((strRead = reader.readLine()) != null) {
				sbf.append(strRead);
			}
			reader.close();
			result = sbf.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 
	 * @param _httpUrl
	 *            请求接口路径
	 * @param gupiao
	 *            股票代码
	 * @param list
	 *            列表类型
	 * @return json字符串
	 */
	public static String requestStockJson(String _httpUrl, String stockId,
			String list) {
		httpUrl = _httpUrl;
		return requestStockJson(stockId, list);
	}

	/**
	 * 将json字符串转为map对象
	 * 
	 * @param stockId
	 * @param list
	 * @return
	 */
	public static Map<String, Object> requestStockEntity(String stockId,
			String list) {
		String result = requestStockJson(stockId.toLowerCase(), list);
		Map<String, Object> stockMap = null;
		if (!StrUtil.isEmpty(result)) {
			result = result.replace("52hdot", "hdot52").replace("52ldot",
					"ldot52");
			stockMap = JSONObject.parseObject(result, Map.class);
		}
		return stockMap;
	}

	/**
	 * 将json字符串转为map对象
	 * 
	 * @param stockId
	 * @param list
	 * @return
	 */
	public static JSONObject requestStockInfo(String stockId, String list) {
		String result = requestStockJson(stockId.toLowerCase(), list);
		result = result.replace("52hdot", "hdot52").replace("52ldot", "ldot52");
		JSONObject jsonObject = JSONObject.parseObject(result);
		return jsonObject;
	}

	public static boolean judgeTradeStockTimes(String datetime) {
		boolean result = false;
		Date thisdate = TimeUtil.getDateByStr(datetime);

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(thisdate);
		int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
		if (dayOfWeek > 1 && dayOfWeek < 7) {// 在周一-周五
			// 只允许在开市时间有买卖记录
			int hours = calendar.get(Calendar.HOUR_OF_DAY);
			int minutes = calendar.get(Calendar.MINUTE);
			// 9：30-11：30、13：00-15：30
			int time = hours * 100 + minutes;
			if ((time >= 930 && time <= 1130) || (time >= 1300 && time <= 1530)) {
				result = true;
			}
		}
		return result;
	}

	
}
