package controller;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import util.Connect;

public class AuthController {
	public boolean login(String username, String password) {
        String query = "SELECT * FROM users WHERE username = ? AND password = ?";
        Object[] params = { username, password };
        ResultSet rs = Connect.getInstance().execQuery(query, params);
        
        try {
            if (rs != null && rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } 
        // Optional
//        finally {
//            Connect.getInstance().closeResultSet();
//        }
        return false;
    }
	
	public boolean register(String username, String password, String phone_number, String address, String role) {
		String query = "INSERT INTO users (username, password, phone_number, address, role) VALUES (?, ?, ?, ?, ?)";
        Object[] params = { username, password, phone_number, address, role };
        
        if(Connect.getInstance().execUpdate(query, params)) {
        	return true;
        }
        
        return false;
	}
}
