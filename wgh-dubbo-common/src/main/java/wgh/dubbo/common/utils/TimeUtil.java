package wgh.dubbo.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.Map;
import java.util.TimeZone;
import java.util.TreeMap;

public class TimeUtil {

	

		// 获取当前时间戳,返回int类型
		public static int getNow() {

			String now = String.valueOf(new Date().getTime());
			now = String.valueOf(Long.parseLong(now) / 1000);
			return Integer.parseInt(now);
		}

		// 获取当前时间戳,返回long类型
		public static long getNow2() {
			return new Date().getTime();
		}

		// 获取当前时间戳
		public static long getNow3() {

			String now = String.valueOf(new Date().getTime());
			now = String.valueOf(Long.parseLong(now) / 1000);
			return Long.parseLong(now);
		}

		// 获取当前时间字符串
		public static String getNowStr() {

			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			String nowstr = sdf.format(new Date());
			return nowstr;
		}

		// 获取当前时间字符串
		public static String getNowStr2() {

			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
			String nowstr = sdf.format(new Date());
			return nowstr;
		}

		// 获取当前时间字符串
		public static String getDateTimeStr(String date) {
			Date _date = getDateByStr(date);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			String nowstr = sdf.format(_date);
			return nowstr;
		}

		// 获取当前时间字符串
		public static String getNormalNowStr() {

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String nowstr = sdf.format(new Date());
			return nowstr;
		}

		// 获取当前时间戳
		public static int getStampByDate(Date date) {

			String now = String.valueOf(date.getTime());
			now = String.valueOf(Long.parseLong(now) / 1000);
			return Integer.parseInt(now);
		}

		// 获取当前时间戳返回long类型
		public static long getStampByDate2(Date date) {

			String now = String.valueOf(date.getTime());
			now = String.valueOf(Long.parseLong(now) / 1000);
			return Integer.parseInt(now);
		}

		/** 通过时间获取时间戳 (精确到秒)**/
		public static long getStampByFomat(String date) {
			Date resultdate = new Date();
			String now = "";
			try {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				resultdate = sdf.parse(date);
				now = String.valueOf(resultdate.getTime());
				now = String.valueOf(Long.parseLong(now) / 1000);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return Long.parseLong(now);
		}
		
		
		/** 通过时间获取时间戳 （精确到毫秒）**/
		public static long getStampByFomatMills(String date) {
			Date resultdate = new Date();
			String now = "";
			try {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				resultdate = sdf.parse(date);
				now = String.valueOf(resultdate.getTime());
				now = String.valueOf(Long.parseLong(now) );
			} catch (Exception e) {
				e.printStackTrace();
			}
			return Long.parseLong(now);
		}

		public static Date getDateByFomat(String format, String date) {
			Date resultdate = new Date();
			try {
				SimpleDateFormat sdf = new SimpleDateFormat(format);
				resultdate = sdf.parse(date);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return resultdate;
		}

		public static Date getDateByStr(String date) {
			Date resultdate = new Date();
			try {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				resultdate = sdf.parse(date);
			} catch (Exception e) {
				// e.printStackTrace();
			}
			return resultdate;
		}

		// 获取一天的结束实际那
		public static Date getBeginDate() {
			Date resultdate = new Date();
			try {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
				resultdate = sdf.parse(sdf2.format(new Date()) + " 00:00:00");
			} catch (Exception e) {
				e.printStackTrace();
			}
			return resultdate;
		}

		// 获取一天的结束时间
		public static Date getEndDate() {
			Date resultdate = new Date();
			try {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
				resultdate = sdf.parse(sdf2.format(new Date()) + " 23:59:59");
			} catch (Exception e) {
				e.printStackTrace();
			}
			return resultdate;
		}

		public static Date getintDate(String times) {
			Date resultdate = new Date();
			try {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
				resultdate = sdf.parse(sdf2.format(new Date()) + " " + times);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return resultdate;
		}

		public static boolean validateDateTime(String date) {
			try {
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				df.parse(date);
				return true;
			} catch (Exception e) {
				// e.printStackTrace();
				return false;
			}
		}

		public static Date addMINUTE(Date date, int inc) {

			Date resultdate = new Date();
			Calendar calendar = Calendar.getInstance();

			try {

				calendar.setTime(date);
				calendar.add(Calendar.MINUTE, inc);
				resultdate = calendar.getTime();

			} catch (Exception e) {
				e.printStackTrace();
			}

			return resultdate;
		}

		public static Date addDate(Date date, int inc) {

			Date resultdate = new Date();
			Calendar calendar = Calendar.getInstance();

			try {

				calendar.setTime(date);
				calendar.add(Calendar.DATE, inc);
				resultdate = calendar.getTime();

			} catch (Exception e) {
				e.printStackTrace();
			}

			return resultdate;
		}

		public static Date addMonth(Date date, int inc) {

			Date resultdate = new Date();
			Calendar calendar = Calendar.getInstance();

			try {

				calendar.setTime(date);
				calendar.add(Calendar.MONTH, inc);
				resultdate = calendar.getTime();

			} catch (Exception e) {
				e.printStackTrace();
			}

			return resultdate;
		}

		public static Date addYear(Date date, int inc) {

			Date resultdate = new Date();
			Calendar calendar = Calendar.getInstance();

			try {

				calendar.setTime(date);
				calendar.add(Calendar.YEAR, inc);
				resultdate = calendar.getTime();

			} catch (Exception e) {
				e.printStackTrace();
			}

			return resultdate;
		}

		public static Date getTimeByStamp(int stamp) {

			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			long lstamp = Long.parseLong(String.valueOf(stamp));
			lstamp = lstamp * 1000;
			Date date = new Date();
			date.setTime(lstamp);
			return date;
		}

		public static String getTimeStrByStamp(int stamp, String format) {

			SimpleDateFormat df = new SimpleDateFormat(format);
			long lstamp = Long.parseLong(String.valueOf(stamp));
			lstamp = lstamp * 1000;
			Date date = new Date();
			date.setTime(lstamp);
			return df.format(date);
		}

		public static String getTimeStrByStamp(int stamp) {

			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			long lstamp = Long.parseLong(String.valueOf(stamp));
			lstamp = lstamp * 1000;
			Date date = new Date();
			date.setTime(lstamp);
			return df.format(date);
		}

		/*** 获取当前 **/
		public static String getTimeStrByDate(Date date, String format) {

			SimpleDateFormat df = new SimpleDateFormat(format);
			return df.format(date);
		}

		public static String getTimeStr(Date date, String fomat) {

			SimpleDateFormat df = new SimpleDateFormat(fomat);
			return df.format(date);
		}

		public static String getDateTimeStr(Date date) {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			return df.format(date);
		}

		public static String getDateStr(Date date) {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			return df.format(date);
		}

		public static String getDateStr(String date) {
			Date _date = getDate(date);
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			return df.format(_date);
		}

		// 获取一天的开始时间
		public static Date getDate(String date) {
			Date resultdate = new Date();
			try {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				resultdate = sdf.parse(date.substring(0, 10) + " 00:00:00");
			} catch (Exception e) {
				e.printStackTrace();
			}
			return resultdate;
		}

		// 获取一天的结束时间
		public static String getBeginDate(String date) {
			String resultdate = null;
			try {
				if (date.length() > 10) {
					resultdate = date.substring(0, 10) + " 00:00:00";
				} else {
					resultdate = date + " 00:00:00";
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return resultdate;
		}

		// 获取一天的结束时间
		public static String getEndDate(String date) {
			String resultdate = null;
			try {
				if (date.length() > 10) {
					resultdate = date.substring(0, 10) + " 23:59:59";
				} else {
					resultdate = date + " 23:59:59";
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return resultdate;
		}

		// 对比时间，当第一个时间比第二个时间大，那么就返回正数
		public static long compareTime(String timeone, String timesecond) {
			long timeonel = getStampByFomat(timeone);
			long timesecondl = getStampByFomat(timeone);
			return (timeonel - timesecondl);
		}

		public static boolean checkTime(int now, int old, int min) {
			int vtime = min * 60;
			if (now - old < vtime) {
				return true;
			}
			return false;
		}

		/**
		 * 计算两个日期之间相差的天数
		 * 
		 * @param smdate
		 *            较小的时间
		 * @param bdate
		 *            较大的时间
		 * @return 相差天数
		 * @throws ParseException
		 * @throws ParseException
		 */
		public static int daysBetween(Date smdate, Date bdate)
				throws ParseException {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			smdate = sdf.parse(sdf.format(smdate));
			bdate = sdf.parse(sdf.format(bdate));
			Calendar cal = Calendar.getInstance();
			cal.setTime(smdate);
			long time1 = cal.getTimeInMillis();
			cal.setTime(bdate);
			long time2 = cal.getTimeInMillis();
			long between_days = (time2 - time1) / (1000 * 3600 * 24);

			return Integer.parseInt(String.valueOf(between_days));
		}

		/**
		 *字符串的日期格式的计算
		 */
		public static int daysBetween(String smdate, String bdate)
				throws ParseException {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Calendar cal = Calendar.getInstance();
			cal.setTime(sdf.parse(smdate));
			long time1 = cal.getTimeInMillis();
			cal.setTime(sdf.parse(bdate));
			long time2 = cal.getTimeInMillis();
			long between_days = (time2 - time1) / (1000 * 3600 * 24);

			return Integer.parseInt(String.valueOf(between_days));
		}

		// 获取改时间字符串日期，并转为整形
		public static Long getLongStr(String date) {
			String nowstr = date.replace("-", "").replace(":", "").replace("-", "")
					.replace(" ", "");
			return Long.valueOf(nowstr);
		}

		/**
		 * 获得当月的第一天
		 * 
		 * @return 当月的第一天时间字符串
		 */
		public static Date getCurMonthFirstDay() {
			Date resultdate = new Date();
			try {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM");
				resultdate = sdf.parse(sdf2.format(new Date()) + "-01 00:00:00");
			} catch (Exception e) {
				e.printStackTrace();
			}
			return resultdate;
		}

		/**
		 * 获取该季度的开始,结束时间
		 * 
		 * @param date
		 * @return 季度：0-开始日期，1-结束日期
		 */
		public static String[] getCurSeasonStartDate(String date) {
			String[] resultdate = new String[2];
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(getDate(date));
			int year = calendar.get(Calendar.YEAR);
			int month = calendar.get(Calendar.MONTH) + 1;
			if (month == 1 || month == 2 || month == 3) {
				resultdate[0] = year + "-01-01 00:00:00";
				resultdate[1] = year + "-03-31 59:59:59";
			} else if (month == 4 || month == 5 || month == 6) {
				resultdate[0] = year + "-04-01 00:00:00";
				resultdate[1] = year + "-06-30 59:59:59";
			} else if (month == 7 || month == 8 || month == 9) {
				resultdate[0] = year + "-07-01 00:00:00";
				resultdate[1] = year + "-09-30 59:59:59";
			} else if (month == 10 || month == 11 || month == 12) {
				resultdate[0] = year + "-10-01 00:00:00";
				resultdate[1] = year + "-12-31 59:59:59";
			}
			return resultdate;
		}

	

		private static String[] getMonthTimes(String datetime, Calendar calendar) {
			String[] dates = new String[2];
			String _datetime;
			int daynum;
			_datetime = getBeginDate(datetime);
			calendar.setTime(getDate(_datetime));
			daynum = calendar.get(Calendar.DAY_OF_MONTH);
			dates[0] = getDateTimeStr(addDate(calendar.getTime(), -1 * daynum + 1));
			dates[1] = getEndDate(getDateTimeStr(addDate(calendar.getTime(),
					calendar.getActualMaximum(Calendar.DAY_OF_MONTH) - daynum)));
			return dates;
		}

		private static String[] getQuanrtyTimes(String datetime) {
			String[] dates;
			String _datetime;
			_datetime = getBeginDate(datetime);
			dates = getCurSeasonStartDate(_datetime);
			return dates;
		}

		private static String[] getYearTimes(String datetime, Calendar calendar) {
			String[] dates = new String[2];
			String _datetime;
			_datetime = getBeginDate(datetime);
			calendar.setTime(getDate(_datetime));
			dates[0] = calendar.get(Calendar.YEAR) + "-01-01 00:00:00";
			dates[1] = calendar.get(Calendar.YEAR) + "-12-31 59:59:59";
			return dates;
		}

		public static String[] getWeekTimes(String datetime, Calendar calendar) {
			String[] dates = new String[2];
			String _datetime;
			int daynum;
			_datetime = getBeginDate(datetime);
			calendar.setTime(getDate(_datetime));
			daynum = calendar.get(Calendar.DAY_OF_WEEK);
			dates[0] = getDateTimeStr(addDate(calendar.getTime(), -1 * daynum + 1));
			dates[1] = getEndDate(getDateTimeStr(addDate(calendar.getTime(),
					7 - daynum)));
			return dates;
		}

		/**
		 * 获取该季度,1-(1-3月),2-(4-6),3-(7-9),4-(10-12)
		 * 
		 * @param date
		 * @return 季度：0-开始日期，1-结束日期
		 */
		public static Integer getCurSeasonNum(String date) {
			Integer quarty = 0;
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(getDate(getBeginDate(date)));
			int year = calendar.get(Calendar.YEAR);
			int month = calendar.get(Calendar.MONTH) + 1;
			if (month == 1 || month == 2 || month == 3) {
				quarty = 1;
			} else if (month == 4 || month == 5 || month == 6) {
				quarty = 2;
			} else if (month == 7 || month == 8 || month == 9) {
				quarty = 3;
			} else if (month == 10 || month == 11 || month == 12) {
				quarty = 4;
			}
			return quarty;
		}

		/**
		 * 获取该季度,1-(1-3月),2-(4-6),3-(7-9),4-(10-12)
		 * 
		 * @param date
		 * @return 季度：0-开始日期，1-结束日期
		 */
		public static Integer getCurSeasonNum(Date date) {
			Integer quarty = 0;
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			int year = calendar.get(Calendar.YEAR);
			int month = calendar.get(Calendar.MONTH) + 1;
			if (month == 1 || month == 2 || month == 3) {
				quarty = 1;
			} else if (month == 4 || month == 5 || month == 6) {
				quarty = 2;
			} else if (month == 7 || month == 8 || month == 9) {
				quarty = 3;
			} else if (month == 10 || month == 11 || month == 12) {
				quarty = 4;
			}
			return quarty;
		}

		/**
		 * 获取该季度,1-(1-3月),2-(4-6),3-(7-9),4-(10-12)
		 * 
		 * @param date
		 * @return 季度：0-开始日期，1-结束日期
		 */
		public static int getWeekOfYear(Date date) {
			int weeknum = 0;
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);

			// 如果是12月份，换计算方法
			if (calendar.get(Calendar.MONTH) == 11) {
				weeknum = calendar.get(Calendar.WEEK_OF_YEAR);
				if (weeknum == 1) {
					weeknum = 53;
				}
			} else {
				weeknum = calendar.get(Calendar.WEEK_OF_YEAR);
			}
			System.err.println(calendar.get(Calendar.DAY_OF_MONTH) + ":" + weeknum);
			return weeknum;
		}

		// 得到该时间段日历
		public static Map getDayCalendars(Date startdate, Date enddate, Object obj) {
			Map dayMap = new TreeMap(new Comparator<String>() {
				@Override
				public int compare(String obj1, String obj2) {
					// 降序排序
					return obj1.compareTo(obj2);
				}
			});
			for (int i = 1; enddate.compareTo(startdate) >= 0; i++) {
				// 保存一天的数据
				String strdate = TimeUtil.getDateStr(enddate);

				dayMap.put(strdate, obj);

				enddate = TimeUtil.addDate(enddate, -1);
			}
			return dayMap;
		}

		// 得到该时间段日历
		public static Map getMonthCalendars(Date startdate, Date enddate, Object obj) {
			Map dayMap = new TreeMap(new Comparator<String>() {
				@Override
				public int compare(String obj1, String obj2) {
					// 降序排序
					return obj1.compareTo(obj2);
				}
			});
			Calendar calendar = Calendar.getInstance();
			for (int i = 1; enddate.compareTo(startdate) >= 0; i++) {
				// 保存一天的数据
				calendar.setTime(enddate);
				int month = calendar.get(Calendar.MONTH) + 1;
				String value = String.valueOf(calendar.get(Calendar.YEAR)) + "-"
						+ ((month < 10 ? "0" : "") + String.valueOf(month));
				dayMap.put(value, obj);
				enddate = TimeUtil.addDate(enddate, -1);
			}
			return dayMap;
		}

		public static String getDateTimeStrByStamp(long stamp, String format) {

			SimpleDateFormat df = new SimpleDateFormat(format);
			long lstamp = Long.parseLong(String.valueOf(stamp));
			Date date = new Date();
			date.setTime(lstamp);
			return df.format(date);
		}
		public static void main(String args[]) {

			/*
			 * System.out.println("deletefromshopwhereid=?".split("from")[1]
			 * .split("where")[0]); System.out
			 * .println("updateshopsetfds".split("update")[1].split("set")[0]);
			 * System.out.println("insertintoshop(fda".split("insertinto")[1]
			 * .split("\\(")[0]);
			 */
			// System.out.println(TimeUtil.getTimeStrByStamp(1408423716,"yyyy-MM-dd HH:mm:ss"));
			// System.out.println("差距："+(getStampByDate(TimeUtil.getDateByFomat("yyyy-MM-dd HH:mm","2014-06-28 00:32"))-getStampByDate(TimeUtil.getDateByFomat("yyyy-MM-dd HH:mm","2014-06-28 00:31"))));
			// System.out.println("差距："+ TimeUtil.getNow());
			// System.out.println("2014-06-28:00:"+getStampByDate(TimeUtil.getDateByFomat("yyyy-MM-dd HH:mm:ss","2014-09-10 23:59:59")));
			// System.out.println("2014-06-28:12:"+getStampByDate(TimeUtil.getDateByFomat("yyyy-MM-dd HH:mm:ss","2014-06-28 12:00:00")));
			// System.out.println("2014-06-28:18:"+getStampByDate(TimeUtil.getDateByFomat("yyyy-MM-dd HH:mm:ss","2014-06-28 18:59:58")));
			// System.out.println("2014-06-28:21:"+getStampByDate(TimeUtil.getDateByFomat("yyyy-MM-dd HH:mm:ss","2014-06-28 21:59:58")));

			// System.out.println(getTimeStr(getTimeByStamp(1403866798),"yyyy-MM-dd HH:mm:ss"));
			//			 
			// for(int i=0; i<10;i++){
			// System.out.println("2014-06-0"+i+":"+getStampByDate(TimeUtil.getDateByFomat("yyyy-MM-dd HH:mm:ss","2014-06-0"+i+" 00:00:00")));
			// }
			//			  
			// for(int i=10; i<30;i++){
			// System.out.println("2014-06-0"+i+":"+getStampByDate(TimeUtil.getDateByFomat("yyyy-MM-dd HH:mm:ss","2014-06-"+i+" 00:00:00")));
			// }
			/*
			 * SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			 * Date d1; try { d1 = sdf.parse("2012-09-08 10:10:10"); Date d2 =
			 * sdf.parse("2012-09-15 00:00:00");
			 * System.out.println(daysBetween("2012-09-08 10:10:10",
			 * "2012-09-15 00:00:00")); } catch (ParseException e) { // TODO
			 * Auto-generated catch block e.printStackTrace(); }
			 */
			// 测试周
			/*
			 * for (String string : getYearStartEndDateTime(getNormalNowStr(), 4)) {
			 * System.out.print("周"+string+"\n"); } System.out.println("\n"); for
			 * (String string : getYearStartEndDateTime("2015-09-18", 4)) {
			 * System.out.print("周"+string+"\n"); }
			 * 
			 * System.out.println("\n"); for (String string :
			 * getYearStartEndDateTime(getNormalNowStr(), 5)) {
			 * System.out.print("月"+string+"\n"); } System.out.println("\n"); for
			 * (String string : getYearStartEndDateTime("2015-09", 5)) {
			 * System.out.print("月"+string+"\n"); } System.out.println("\n"); for
			 * (String string : getYearStartEndDateTime("2015-09-18", 5)) {
			 * System.out.print("月"+string+"\n"); }
			 * 
			 * System.out.println("\n"); for (String string :
			 * getYearStartEndDateTime(getNormalNowStr(), 14)) {
			 * System.out.print("季"+string+"\n"); } System.out.println("\n"); for
			 * (String string : getYearStartEndDateTime("2015-09", 14)) {
			 * System.out.print("季"+string+"\n"); } System.out.println("\n"); for
			 * (String string : getYearStartEndDateTime("2015-09-18", 14)) {
			 * System.out.print("季"+string+"\n"); }
			 * 
			 * System.out.println("\n");
			 * 
			 * for (String string : getYearStartEndDateTime(getNormalNowStr(), 15))
			 * { System.out.print("年"+string+"\n"); } System.out.println("\n"); for
			 * (String string : getYearStartEndDateTime("2015", 15)) {
			 * System.out.print("年"+string+"\n"); } System.out.println("\n"); for
			 * (String string : getYearStartEndDateTime("2015-09", 15)) {
			 * System.out.print("年"+string+"\n"); } System.out.println("\n"); for
			 * (String string : getYearStartEndDateTime("2015-09-18", 15)) {
			 * System.out.print("年"+string+"\n"); }
			 */
			//boolean ss = validateDateTime("2015-09-17 14:01");
			//System.out.println(ss);
			Calendar calendar=Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
			calendar.setTime(TimeUtil.getEndDate());
			System.out.println("1、"+calendar.getTime());
			
			Calendar calendar1=Calendar.getInstance();
			calendar1.setTime(TimeUtil.getEndDate());
			System.out.println("2、"+calendar1.getTime());
		}

	

}
