/**
 * <p>Author		:	cielo</p>
 * <p>Date 			: 	2016 上午11:28:47</p>
 */
package com.lezic.core.crud.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBHelper {
	public static final String url = "jdbc:mysql://127.0.0.1/lezic";
	public static final String name = "org.gjt.mm.mysql.Driver";
	public static final String user = "root";
	public static final String password = "";

	private static DBHelper instance;

	public Connection conn = null;
	
	public DBHelper(){
		try {
			Class.forName(name);// 指定连接类型
			conn = DriverManager.getConnection(url, user, password);// 获取连接
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	

	public static DBHelper getInstance() throws SQLException, ClassNotFoundException {
		if (instance == null) {
			instance = new DBHelper();
		}
		return instance;
	}

	/**
	 * 查询
	 * 
	 * @param sql
	 * @return
	 * @throws SQLException
	 * @author cielo
	 */
	public ResultSet find(String sql) throws SQLException {
		PreparedStatement pst = conn.prepareStatement(sql);
		return pst.executeQuery();
	}

	public void close() {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			conn = null;
			instance = null;
		}

	}
}
