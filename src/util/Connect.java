package util;

import java.sql.*;

public class Connect {
	public final String USERNAME = "root";
	public final String PASSWORD = "";
	public final String DATABASE = "calouseif";
	public final String HOST = "localhost:3306";
	public final String CONNECTION = String.format("jdbc:mysql://%s/%s", HOST, DATABASE);

	public Connection con;
	public PreparedStatement ps;
	private static Connect connect;

	private ResultSet rs;
	private ResultSetMetaData rsm;

	private Connect() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(CONNECTION, USERNAME, PASSWORD);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static Connect getInstance() {
		if (connect == null) connect = new Connect();
		return connect;
	}

	public ResultSet execQuery(String query, Object[] params) {
		try {
            PreparedStatement ps = con.prepareStatement(query);

            for (int i = 0; i < params.length; i++) {
                ps.setObject(i + 1, params[i]);
            }

            rs = ps.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rs;
	}

	public boolean execUpdate(String query, Object[] params) {
        try {
            PreparedStatement ps = con.prepareStatement(query);

            for (int i = 0; i < params.length; i++) {
                ps.setObject(i + 1, params[i]);
            }

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return false;
    }
}
