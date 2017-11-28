package com.nike.reporting.util;

import java.sql.*;

public class UpdatePassword {

	
	
	// JDBC driver name and database URL
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost/nrtdev";

	// Database credentials
	static final String USER = "root";
	static final String PASS = "root";

	public static void main(String[] args) throws Exception {
		
		System.out.println(LoginUtil.decrypt("Yr1hHqkzjFTV8ztQIoWZvw=="));
		System.exit(0);
		Connection conn = null;
		PreparedStatement stmt1 = null;
		PreparedStatement stmt2 = null;

		conn = DriverManager.getConnection(DB_URL, USER, PASS);
		String sql1 = "select id, password from nrt_user";

		String sql2 = "UPDATE nrt_user set password=? where id=?";

		stmt1 = conn.prepareStatement(sql1);
		stmt2 = conn.prepareStatement(sql2);
		ResultSet rs = stmt1.executeQuery(sql1);
		while (rs.next()) {
			System.out.println(rs.getString(2));
			System.out.println(LoginUtil.encrypt(rs.getString(2)));
			stmt2.setString(1, LoginUtil.encrypt(rs.getString(2)));
			stmt2.setInt(2, (rs.getInt(1)));
			stmt2.execute();

		}
	}
}
