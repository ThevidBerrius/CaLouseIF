package controller;

import java.sql.ResultSet;
import java.sql.SQLException;

import main.SceneManager;
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
            	this.authUser = new User(rs.getString("userId"), rs.getString("username"), rs.getString("password"), rs.getString("phoneNumber"), rs.getString("address"), rs.getString("role"));
            	return this.authUser.getRole();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } 
        
        return "Invalid Credentials";
    }
	
	public String register(String username, String password, String phone_number, String address, String role) {
		String message = checkAccountValidation(username, password, phone_number, address, role);
		
		if (!message.equals("Validation Success")) return message;
		
		String userId = this.idGenerator.generateId("users", "US");
		User newUser = new User(userId, username, password, phone_number, address, role);
		
		String query = "INSERT INTO users (userId, username, password, phoneNumber, address, role) VALUES (?, ?, ?, ?, ?, ?)";
        Object[] params = { newUser.getUserId(), newUser.getUsername(), newUser.getPassword(), newUser.getPhoneNumber(), newUser.getAddress(), newUser.getRole() };
        
        if(Connect.getInstance().execUpdate(query, params)) {
        	this.authUser = newUser;
        	return "Success";
        }
        
        return "Error Insert to Database";
	}
	
	public String checkAccountValidation(String username, String password, String phone_number, String address, String role) {
		// Username
		if(username == null || username.length() < 3) {
			return "Username must not be empty and length below 3";
		} 
		
		// Password
		if(password == null || password.length() < 8) {
			return "Password must not be empty and length below 8";
		} 
		
		String specialCharacters = "!@#$%^&*";
		boolean isSpecialChar = false;
		for (char c : password.toCharArray()) {
			if (specialCharacters.contains(String.valueOf(c))) {
				isSpecialChar = true;
				break;
			}
		}
		if (!isSpecialChar) return "Password must have special character";
		
		// Phone Number
		String remainingNumber = (phone_number.length() > 3) ? phone_number.substring(3) : "";
		if(phone_number.isEmpty() || !phone_number.startsWith("+62")) {
			return "Phone Number must start by '+62'";
		} else if (remainingNumber.length() < 9) {
			return "Phone Number length must be 11";
		}
		
		for (char c : remainingNumber.toCharArray()) {
			if(!Character.isDigit(c)) return "Phone Number must be number";
		}
		
		// Address
		if(address.isEmpty()) {
			return "Address must not be empty";
		} 
		
		// Role
		if(role.isEmpty()) {
			return "Role must be choosen";
		}
		
		return "Validation Success";
	}
	
	public void logout(SceneManager sceneManager) {
		this.authUser = null;
		sceneManager.switchPage("login");
	}

	public User getAuthUser() {
		return this.authUser;
	}
}
