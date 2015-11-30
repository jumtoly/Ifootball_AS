package com.ifootball.app.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class StringUtil {

	/**
	 * Encode URL using "UTF-8" format.
	 * 
	 * @param value
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String encodeURL(String value)
			throws UnsupportedEncodingException {
		if (!isEmpty(value)) {
			return URLEncoder.encode(value, "UTF-8");
		}
		return "";
	}

	/**
	 * Decode URL using "UTF-8" format.
	 * 
	 * @param value
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String decodeURL(String value)
			throws UnsupportedEncodingException {
		if (!isEmpty(value)) {
			return URLDecoder.decode(value, "UTF-8");
		}
		return "";
	}

	/**
	 * check if the string is empty
	 * 
	 * @param value
	 * @return
	 */
	public static boolean isEmpty(String value) {
		return value == null || value.trim().equals("") || value.length() == 0;
	}

	/**
	 * Check the string whether is long data type
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isLong(String str) {
		if ("0".equals(str.trim())) {
			return true;
		}
		Pattern pattern = Pattern.compile("^[^0]\\d*");
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}

	/**
	 * Check the string whether is float data type
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isFloat(String str) {
		if (isLong(str)) {
			return true;
		}
		Pattern pattern = Pattern.compile("\\d*\\.{1}\\d+");
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}

	/**
	 * Check the string whether is float data type with precison
	 * 
	 * @param str
	 * @param precision
	 * @return
	 */
	public static boolean isFloat(String str, int precision) {
		String regStr = "\\d*\\.{1}\\d{" + precision + "}";
		Pattern pattern = Pattern.compile(regStr);
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}

	public static boolean isPositiveInteger(String str) {
		if (StringUtil.isEmpty(str)) {
			return false;
		}
		Pattern pattern = Pattern.compile("^[0-9]*[1-9][0-9]*$");
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}

	
	/**
	 * 
	 * Check the string whether is number data type
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNumber(String str) {
		if (isLong(str)) {
			return true;
		}
		Pattern pattern = Pattern.compile("(-)?(\\d*)\\.{0,1}(\\d*)");
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}

	/**
	 * 
	 * Check the string whether is email data type
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isEmail(String str) {
		Pattern pattern = Pattern
				.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
		Matcher isEMail = pattern.matcher(str);
		if (!isEMail.matches()) {
			return false;
		}
		return true;
	}

	/*
	 * 验证身份证号码是否合法
	 */
	public static boolean isIdentityNo(String str) {
		if (str != null) {
			str = str.toLowerCase();
		}
		Pattern pattern = Pattern
				.compile("^(\\d{18,18}|\\d{15,15}|\\d{17,17}x)$");
		Matcher isIdentityNo = pattern.matcher(str);
		return isIdentityNo.matches();
	}
	
	/*
	 * 验证输入是否为电话号码或手机号码
	 */
	public static boolean isValidatePhone(String phone) {

		String regEx = "^\\d{3,4}-\\d{7,8}(-\\d{3,4})?|1\\d{10}$";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(phone);
		return m.matches();
	}
	
	/**
	 * 验证输入是否为手机号码 
	 * @param phone
	 * @return
	 */
	public static boolean isPhone(String phone) {
		String regEx = "^1\\d{10}$";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(phone);
		return m.matches();
	}

	/**
	 * Format the specified String with param placeholder list. ex. the string
	 * like format "I am {0},and she comes from {1}."
	 * 
	 * @param str
	 * @param args
	 *            the param placeholder list.
	 * @return the formated string
	 */
	public static String format(String str, Object... args) {

		if (StringUtil.isEmpty(str) || args.length == 0) {
			return str;
		}

		String result = str;
		Pattern pattern = Pattern.compile("\\{(\\d+)\\}");
		Matcher matcher = pattern.matcher(str);
		while (matcher.find()) {
			int index = Integer.parseInt(matcher.group(1));
			if (index < args.length) {
				result = result
						.replace(matcher.group(), args[index].toString());
			}
		}

		return result;
	}

	/**
	 * Format price
	 * 
	 * @param price
	 * @return format like "￥123.00" or "￥-123.00"
	 */
	public static String priceToString(double price) {
		String priceString = new DecimalFormat("###,###,###.##").format(Math
				.abs(price));
		if (!priceString.contains(".")) {
			priceString = priceString + ".00";
		} else {
			DecimalFormat format = new DecimalFormat("###,###,###.00");
			priceString = format.format(Math.abs(price));
		}
		if (priceString.indexOf(".") == 0) {
			priceString = "0" + priceString;
		}
		if (price < 0) {
			return "￥-" + priceString;
		} else if (price < 1) {
			return "￥" + priceString;
		} else {
			return "￥" + priceString;
		}
	}
	
	/**
	 * Format price
	 * 
	 * @param price
	 * @return format like "￥123.00" or "-￥123.00"
	 */
	public static String priceToString2(double price) {
		String priceString = new DecimalFormat("###,###,###.##").format(Math
				.abs(price));
		if (!priceString.contains(".")) {
			priceString = priceString + ".00";
		} else {
			DecimalFormat format = new DecimalFormat("###,###,###.00");
			priceString = format.format(Math.abs(price));
		}
		if (priceString.indexOf(".") == 0) {
			priceString = "0" + priceString;
		}
		if (price < 0) {
			return "-￥" + priceString;
		} else if (price < 1) {
			return "￥" + priceString;
		} else {
			return "￥" + priceString;
		}
	}

	/**
	 * format point
	 * 
	 * @param point
	 * @return
	 */
	public static String getPointToString(float point) {
		String priceString = new DecimalFormat("###,###,###.##").format(Math
				.abs(point));
		return priceString;
	}

	/**
	 * format number to price
	 * 
	 * @param number
	 * @return
	 */
	public static String getPriceByFloat(float number) {

		String mynumber = String.valueOf(number);
		if (!mynumber.contains(".")) {
			mynumber = mynumber + ".00";
		} else {
			DecimalFormat format = new DecimalFormat("#.00");
			mynumber = format.format(number);
			if (mynumber.indexOf(".") == 0) {
				mynumber = "0" + mynumber;
			}

		}

		return mynumber;

	}

	/**
	 * 
	 * @param number
	 * @return
	 */
	public static String getPriceByDouble(double number) {

		String mynumber = String.valueOf(number);
		if (!mynumber.contains(".")) {
			mynumber = mynumber + ".00";
		} else {
			DecimalFormat format = new DecimalFormat("#.00");
			mynumber = format.format(number);
			if (mynumber.indexOf(".") == 0) {
				mynumber = "0" + mynumber;
			}
		}

		return mynumber;
	}

	/**
	 * get price from string
	 * 
	 * @param inputString
	 * @return
	 */
	public static float stringToPrice(String inputString) {
		if (isEmpty(inputString))
			return 0.0f;
		if (inputString.contains("￥")) {
			inputString = inputString.replace("￥", "");
		}
		inputString = inputString.replaceAll(",", "");
		return Float.parseFloat(inputString);
	}
}
