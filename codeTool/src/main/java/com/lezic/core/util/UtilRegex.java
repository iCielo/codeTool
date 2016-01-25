/**
 * <p>Author		:	cielo</p>
 * <p>Date 			: 	2016 下午5:24:00</p>
 */
package com.lezic.core.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author cielo
 *
 */
public class UtilRegex {

	public static final String MAIL = "^([a-zA-Z0-9]+[_|\\_|\\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\\_|\\.]?)*[a-zA-Z0-9]+\\.[a-zA-Z]{2,3}$";

	/**
	 * 返回匹配的字符串数组
	 * 
	 * @param s
	 * @param pattern
	 * @return
	 */
	public static List<String> match(String s, String pattern) {
		Matcher m = Pattern.compile(pattern).matcher(s);
		List<String> result = new ArrayList<String>();
		while (m.find()) {
			int num = m.groupCount();
			for (int i = 0; i <= num; i++) {
				result.add(m.group(i));
			}
		}
		return result;
	}

	/**
	 * 获取第一个匹配的结果
	 * 
	 * @param s
	 * @param pattern
	 * @param group
	 * @return
	 * @author cielo
	 */
	public static String matchFirst(String s, String pattern, int group) {
		try {
			Matcher m = Pattern.compile(pattern).matcher(s);
			while (m.find()) {
				return m.group(group);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取第一个匹配的结果
	 * 
	 * @param s
	 * @param pattern
	 * @param group
	 *            默认为0
	 * @return
	 * @author cielo
	 */
	public static String matchFirst(String s, String pattern) {
		return matchFirst(s, pattern, 0);
	}

	/**
	 * 验证是否正确
	 * 
	 * @param s
	 * @param pattern
	 * @return
	 * @author cielo
	 */
	public static boolean validator(String s, String pattern) {
		Matcher m = Pattern.compile(pattern).matcher(s);
		return m.find();
	}
}
