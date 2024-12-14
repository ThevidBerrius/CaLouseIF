package model;

import java.sql.ResultSet;
import java.sql.SQLException;

import controller.IdGenerator;
import util.Connect;

public class User {
	private String userId, username, password, phoneNumber, address, role;

	public User(String userId, String username, String password, String phoneNumber, String address, String role) {
		super();
		this.userId = userId;
		this.username = username;
		this.password = password;
		this.phoneNumber = phoneNumber;
		this.address = address;
		this.role = role;
	}
	
	public static User login(String username, String password) {
		String query = "SELECT * FROM users WHERE username = ? AND password = ?";
        Object[] params = { username, password };
        ResultSet rs = Connect.getInstance().execQuery(query, params);
        
        try {
            if (rs != null && rs.next()) return new User(rs.getString("userId"), rs.getString("username"), rs.getString("password"), rs.getString("phoneNumber"), rs.getString("address"), rs.getString("role"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return null;
	}
	
	public static User register(String username, String password, String phoneNumber, String address, String role) {
		String userId = IdGenerator.generateId("users", "US");
		User newUser = new User(userId, username, password, phoneNumber, address, role);
		
		String query = "INSERT INTO users (userId, username, password, phoneNumber, address, role) VALUES (?, ?, ?, ?, ?, ?)";
        Object[] params = { newUser.getUserId(), newUser.getUsername(), newUser.getPassword(), newUser.getPhoneNumber(), newUser.getAddress(), newUser.getRole() };
        
        
        if(Connect.getInstance().execUpdate(query, params)) return newUser;
        
		return null;
	}
	
	// function checkAccountValidation tidak dipindahkan ke model karena merupakan logic yang berkaitan dengan controller saja

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
}
