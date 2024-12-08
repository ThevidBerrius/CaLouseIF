package controller;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import util.Connect;

public class UserController {
	public boolean login(String username, String password) {
		if(username.isEmpty() || password.isEmpty()) {
			System.out.println("Username or Password is empty");
			return false;
		}
		
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
		if(!checkAccountValidation(username, password, phone_number, address, role)) {
			return false;
		}
		
		String query = "INSERT INTO users (username, password, phone_number, address, role) VALUES (?, ?, ?, ?, ?)";
        Object[] params = { username, password, phone_number, address, role };
        
        if(Connect.getInstance().execUpdate(query, params)) {
        	return true;
        }
        
        return false;
	}
	
	public boolean checkAccountValidation(String username, String password, String phone_number, String address, String role) {
		// Username
		if(username == null || username.length() < 3) {
			return false;
		} 
		
		// Password
		if(password == null || password.length() < 8) {
			return false;
		} 
		
		String specialCharacters = "!@#$%^&*";
		boolean isSpecialChar = false;
		for (char c : password.toCharArray()) {
			if (specialCharacters.contains(String.valueOf(c))) {
				isSpecialChar = true;
				break;
			}
		}
		if (!isSpecialChar) return false;
		
		// Phone Number
		String remainingNumber = phone_number.substring(3);
		if(phone_number.isEmpty() || !phone_number.startsWith("+62") || remainingNumber.length() < 9) {
			return false;
		}
		
		for (char c : remainingNumber.toCharArray()) {
			if(!Character.isDigit(c)) return false;
		}
		
		// Address
		if(address.isEmpty()) {
			return false;
		} 
		
		// Role
		if(role.isEmpty()) {
			return false;
		}
		
		return true;
	}
}
