package util;

import java.sql.*;

public class Connect {
	public final String USERNAME = "root";
	public final String PASSWORD = "";
	public final String DATABASE = "calouseif";
	public final String HOST = "localhost:3306";
	public final String CONNECTION = String.format("jdbc:mysql://%s/%s", HOST, DATABASE);

	public Connection con;
	public Statement st;
	private static Connect connect;

	private ResultSet rs;
	private ResultSetMetaData rsm;

	public static Connect getiInstance() {
		if (connect == null)
			return new Connect();
		else
			return connect;
	}

	private Connect() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(CONNECTION, USERNAME, PASSWORD);
			st = con.createStatement();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}

	private ResultSet execQuery(String query) {
		try {
			rs = st.executeQuery(query);
			rsm = rs.getMetaData();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rs;
	}

	public void execUpdate(String query) {
		try {
			st.execute(query);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
