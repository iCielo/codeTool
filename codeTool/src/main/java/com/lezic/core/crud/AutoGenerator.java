/**
 * <p>Author		:	cielo</p>
 * <p>Date 			: 	2016 下午2:04:47</p>
 */
package com.lezic.core.crud;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.lezic.app.sys.user.entity.SysUser;
import com.lezic.core.crud.annotation.WebField;
import com.lezic.core.crud.db.Column;
import com.lezic.core.crud.db.DBHelper;
import com.lezic.core.util.UtilClass;
import com.lezic.core.util.UtilData;
import com.lezic.core.util.UtilDate;
import com.lezic.core.util.UtilFile;
import com.lezic.core.util.UtilVelocity;

/**
 * @author cielo
 *
 */
public class AutoGenerator {

	public static Logger logger = LogManager.getLogger();

	/**
	 * 获取字段集合
	 * 
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @author cielo
	 */
	public static List<Column> getColumnListByTable(String tableName, String tableSchema)
			throws ClassNotFoundException, SQLException {
		DBHelper dbHelper = DBHelper.getInstance();

		StringBuffer sb = new StringBuffer();
		sb.append("select ");
		sb.append("COLUMN_NAME");
		sb.append(",COLUMN_KEY");
		sb.append(",COLUMN_COMMENT");
		sb.append(",IS_NULLABLE");
		sb.append(",DATA_TYPE");
		sb.append(",CHARACTER_MAXIMUM_LENGTH");
		sb.append(" from information_schema.`COLUMNS` ");
		sb.append(" where table_name = '" + tableName + "'");
		sb.append(" and table_schema = '" + tableSchema + "'");
		logger.debug(sb.toString());
		ResultSet ret = dbHelper.find(sb.toString());
		List<Column> columns = new ArrayList<Column>();

		while (ret.next()) {
			int i = 1;
			Column column = new Column();
			String columnName = ret.getString(i++);
			column.setName(columnName);
			column.setFieldName(UtilData.toCamel(columnName));
			column.setMethodName(UtilData.firstUpperCase(column.getFieldName()));
			String columnKey = ret.getString(i++);
			if ("PRI".equals(columnKey)) {
				column.setPrimaryKey(true);
				column.setUnique(true);
			} else {
				column.setPrimaryKey(false);
			}
			String columnComment = ret.getString(i++);
			column.setComment(columnComment);
			String isNullable = ret.getString(i++);
			column.setNullable("YES".equals(isNullable));
			String dataType = ret.getString(i++);
			if ("datetime".equals(dataType)) {
				dataType = "Date";
			} else {
				dataType = "String";
			}
			column.setDataType(dataType);
			column.setLength(ret.getInt(i++));
			logger.debug(column.toString());
			columns.add(column);
		}
		ret.close();
		dbHelper.close();
		return columns;
	}

	/**
	 * 生成实体类
	 * 
	 * @param packageName
	 *            包名
	 * @param tableName
	 *            表名
	 * @param tableSchema
	 *            数据库名 表名
	 * @param outPath
	 *            输出路径 如:D:\
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 * @author cielo
	 */
	public static void processEntity(String packageName, String tableName, String tableSchema, String outPath)
			throws ClassNotFoundException, SQLException, IOException {
		// String packageName = "com.lezic.app.sys.user.entity";
		logger.info("由数据库表 " + tableName + " 反向生成代码......");
		tableName = tableName.toLowerCase();
		String entityName = UtilData.firstUpperCase(UtilData.toCamel(tableName));
		Date now = new Date();

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("columns", AutoGenerator.getColumnListByTable(tableName, tableSchema));
		params.put("packageName", packageName);
		params.put("tableName", tableName);
		params.put("entityName", entityName);
		params.put("now", UtilDate.formatDate(now, UtilDate.P_YYYYMMDDHHMMSS));

		String entityTemplate = UtilClass.getRootPath() + File.separator + "template" + File.separator + "entity.txt";
		String template = UtilFile.getContent(entityTemplate);
		String content = UtilVelocity.process(template, params);
		String destFile = outPath + File.separator + entityName + ".java";
		UtilFile.write(destFile, content);
		logger.info("自动生成代码成功！生成路径：" + destFile);
	}

	/**
	 * 由类、模板生成代码
	 * 
	 * @param cl
	 * @param template
	 * @author cielo
	 * @throws IOException
	 */
	public static void processTemplate(Class<?> cl, String templatePath, String outPath) throws IOException {
		String entityName = cl.getSimpleName();
		logger.info("由实体类 " + entityName + " 生成代码......");
		Field[] fields = cl.getDeclaredFields();
		List<Map<String,Object>> fieldList = new LinkedList<Map<String,Object>>();
		for (int i = 0; i < fields.length; i++) {
			WebField webField = fields[i].getAnnotation(WebField.class);
			if (webField != null) {
				Map<String,Object> item = new HashMap<String, Object>();
				item.put("label", webField.label());
				item.put("placeholder", webField.placeholder());
				item.put("nullable", webField.nullable());
				item.put("unique", webField.unique());
				item.put("length", webField.length());
				item.put("name", fields[i].getName());
				fieldList.add(item);
			}
		}
		Date now = new Date();

		Map<String, Object> params = new HashMap<String, Object>();

		params.put("entityName", entityName);
		params.put("fields", fieldList);
		params.put("now", UtilDate.formatDate(now, UtilDate.P_YYYYMMDDHHMMSS));

		String template = UtilFile.getContent(templatePath);
		String content = UtilVelocity.process(template, params);
		String destFile = outPath + File.separator + entityName + ".java";
		UtilFile.write(destFile, content);
		logger.info("自动生成代码成功！生成路径：" + destFile);
	}

	public static void main(String[] args) throws ClassNotFoundException, SQLException, IOException {
//		 AutoGenerator.processEntity("com.lezic.app.sys.user.entity", "sys_user","lezic", "D:\\");
		String templatePath = UtilClass.getRootPath() + File.separator + "template" + File.separator + "addPage.jsp";
		AutoGenerator.processTemplate(SysUser.class, templatePath, "D:\\");
	}
}
