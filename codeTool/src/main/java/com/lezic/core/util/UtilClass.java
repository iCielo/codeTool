/**
 * <p>Author		:	cielo</p>
 * <p>Date 			: 	2016 下午2:05:32</p>
 */
package com.lezic.core.util;

import java.net.URLDecoder;

import com.lezic.app.sys.user.entity.SysUser;

/**
 * @author cielo
 *
 */
public class UtilClass {

	private static String classPath = getRootPath();

	/**
	 * 获取类环境的根路径
	 * 
	 * @return
	 */
	public static String getRootPath() {
		if (classPath != null && !"".equals(classPath)) {
			return classPath;
		}
		String path = UtilClass.class.getResource("/").getPath();
		if (path.indexOf("%") >= 0) {
			try {
				path = URLDecoder.decode(path, "utf-8");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return path;
	}

	/**
	 * 获取类的包路径
	 * @param cl
	 * @return
	 * @author cielo
	 */
	public static String getPackagePath(Class<?> cl) {
		String rootPath = UtilClass.getRootPath();
		String clPath = cl.getResource("").getPath();
		return clPath.substring(rootPath.length());
	}

	public static void main(String[] args) {
		String str = UtilClass.getPackagePath(SysUser.class);
		System.out.println(str);
	}

}
