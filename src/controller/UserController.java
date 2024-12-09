package controller;

import java.sql.ResultSet;
import java.sql.SQLException;

import model.User;
import util.Connect;

public class UserController {
	private IdGenerator idGenerator;
	private User authUser;
	
	public UserController() {
		this.idGenerator = new IdGenerator();
	}

	public String login(String username, String password) {
		if (username.equals("admin") && password.equals("admin")) return "Admin";
		else if (username.isEmpty() || password.isEmpty()) return "Username or Password cannot empty";
		
        String query = "SELECT * FROM users WHERE username = ? AND password = ?";
        Object[] params = { username, password };
        ResultSet rs = Connect.getInstance().execQuery(query, params);
        
        try {
            if (rs != null && rs.next()) {
            	this.authUser = new User(rs.getString("id"), rs.getString("username"), rs.getString("password"), rs.getString("phoneNumber"), rs.getString("address"), rs.getString("role"));
                return "Success";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } 
        // Optional
//        finally {
//            Connect.getInstance().closeResultSet();
//        }
        
        return "Invalid Credentials";
    }
	
	public boolean register(String username, String password, String phone_number, String address, String role) {
		if(!checkAccountValidation(username, password, phone_number, address, role)) {
			return false;
		}
		
		String userId = this.idGenerator.generateId("users", "US");
		
		String query = "INSERT INTO users (id, username, password, phone_number, address, role) VALUES (?, ?, ?, ?, ?, ?)";
        Object[] params = { userId, username, password, phone_number, address, role };
        
        if(Connect.getInstance().execUpdate(query, params)) {
        	this.authUser = new User(userId, username, password, phone_number, address, role);
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

	public User getAuthUser() {
		return authUser;
	}
}
