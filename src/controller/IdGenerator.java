package controller;

import java.sql.ResultSet;
import java.sql.SQLException;

import util.Connect;

public class IdGenerator {
	public static String generateId(String table, String prefix) {
		String idField = table.substring(0, table.length() - 1) + "Id";
		String query = "SELECT " + idField + " FROM " + table + " ORDER BY " + idField + " DESC LIMIT 1";
		String lastId = "";
		
        try {
        	ResultSet rs = Connect.getInstance().execQuery(query, new Object[]{});
			if (rs.next()) lastId = rs.getString(idField);
		} catch (SQLException e) {
			e.printStackTrace();
		}
        
        if (lastId.isEmpty()) return prefix + String.format("%03d", 1);
        
		return prefix + String.format("%03d", Integer.parseInt(lastId.substring(2)) + 1);
	}
}
