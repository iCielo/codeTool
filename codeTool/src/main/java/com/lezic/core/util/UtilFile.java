/**
 * <p>Author		:	cielo</p>
 * <p>Date 			: 	2016 下午2:39:47</p>
 */
package com.lezic.core.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author cielo
 *
 */
public class UtilFile {
	
	/** 日志 */
	private static Logger logger = LogManager.getLogger(UtilFile.class);

	/**
	 * 获取文件内容字符串对象
	 * 
	 * @param filename
	 *            包含文件名的全路径
	 * @param filename
	 * @return
	 * @author cielo
	 * @throws IOException
	 */
	public static String getContent(String filename) throws IOException {
		StringBuffer sb = new StringBuffer();
		File file = new File(filename);
		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "utf-8"));
		String str = null;
		while ((str = reader.readLine()) != null) {
			sb.append(str);
			sb.append("\n");
		}
		reader.close();
		return sb.toString();
	}

	/**
	 * 将字符串内容写入文件保存
	 * 
	 * @param filename
	 *            包含文件名的全路径
	 * @param content
	 * @author cielo
	 * @throws IOException
	 */
	public static void write(String filename, String content) throws IOException {
		File file = new File(filename);
		File parent = file.getParentFile();
		if (!parent.exists()) {
			parent.mkdirs();
		}
		if (!file.exists()) {
			file.createNewFile();
		}
		OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(filename, false), "UTF-8");
		osw.write(content);
		osw.close();
	}

	/**
	 * 删除文件或文件夹以及子文件夹和子文件等 【注意】请谨慎调用该方法，避免删除重要文件
	 * 
	 * @param file
	 *            void
	 * @throws @author
	 *             cielo 2013-2-20
	 */
	public static void deleteFile(File file) {
		if (file.exists()) {
			if (file.isFile()) {// 文件
				logger.info(file.getAbsolutePath() + " 删除中...");
				file.delete();
				logger.info("删除成功！");
				return;
			} else {// 文件夹
				File[] files = file.listFiles();
				for (int i = 0; i < files.length; i++) {
					deleteFile(files[i]);
				}
				file.delete();
			}
		} else {
			logger.info(file.getAbsolutePath() + " 文件不存在！");
		}
	}

	/**
	 * 删除空文件夹、空的子文件夹
	 * 
	 * @param file
	 * @author cielo
	 */
	public static void deleteEmptyDirectory(File file) {
		if (file != null && file.exists() && file.isDirectory()) {
			File[] files = file.listFiles();
			if (files != null && files.length > 0) {
				for (int i = 0; i < files.length; i++) {
					deleteEmptyDirectory(files[i]);
				}
				// 子文件夹里的删除完后，重新获取。判断空的子文件删除后，该文件夹是否为空
				files = file.listFiles();
			}
			if (files == null || files.length == 0) {
				String absolutePath = file.getAbsolutePath();
				file.delete();
				logger.info("删除空文件夹！路径：" + absolutePath);
			}
		}
	}
}
