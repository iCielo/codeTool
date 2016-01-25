/**
 * <p>Author		:	cielo</p>
 * <p>Date 			: 	2016 上午11:43:51</p>
 */
package com.lezic.core.crud.db;

/**
 * @author cielo
 *
 */
public class Column {

	/**
	 * java字段名
	 */
	private String fieldName;

	/**
	 * java方法名
	 */
	private String methodName;

	/**
	 * 数据库列名
	 */
	private String name;

	/**
	 * 是否主键
	 */
	private boolean primaryKey;

	/**
	 * 是否唯一
	 */
	private boolean unique;

	/**
	 * 是否可空
	 */
	private boolean nullable;

	/**
	 * 列注解
	 */
	private String comment;

	/**
	 * 列类型
	 */
	private String dataType;

	/**
	 * 长度
	 */
	private int length;

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isPrimaryKey() {
		return primaryKey;
	}

	public void setPrimaryKey(boolean primaryKey) {
		this.primaryKey = primaryKey;
	}

	public boolean isUnique() {
		return unique;
	}

	public void setUnique(boolean unique) {
		this.unique = unique;
	}

	public boolean isNullable() {
		return nullable;
	}

	public void setNullable(boolean nullable) {
		this.nullable = nullable;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	@Override
	public String toString() {
		return "Column [fieldName=" + fieldName + ", methodName=" + methodName + ", name=" + name + ", primaryKey="
				+ primaryKey + ", unique=" + unique + ", nullable=" + nullable + ", comment=" + comment + ", dataType="
				+ dataType + ", length=" + length + "]";
	}

}
