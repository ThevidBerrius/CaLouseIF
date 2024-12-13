package controller;

import main.SceneManager;
import model.User;

public class UserController {
	private static UserController instance;
	private User authUser;
	
	public static UserController getInstance() {
		if (instance == null) instance = new UserController();
		return instance;
	}

	public String login(String username, String password) {
		if (username.equals("admin") && password.equals("admin")) return "Admin";
		else if (username.isEmpty() || password.isEmpty()) return "Username or Password cannot empty";
		
        authUser = User.login(username, password);
        if (this.authUser != null) return this.authUser.getRole();
        
        return "Invalid Credentials";
    }
	
	public String register(String username, String password, String phoneNumber, String address, String role) {
		String message = checkAccountValidation(username, password, phoneNumber, address, role);
		
		if (!message.equals("Validation Success")) return message;
		
		authUser = User.register(username, password, phoneNumber, address, role);
		if(this.authUser != null) return "Success";
		
        return "Error Insert to Database";
	}
	
	public String checkAccountValidation(String username, String password, String phoneNumber, String address, String role) {
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
		String remainingNumber = (phoneNumber.length() > 3) ? phoneNumber.substring(3) : "";
		if(phoneNumber.isEmpty() || !phoneNumber.startsWith("+62")) {
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
		return authUser;
	}

	public void setAuthUser(User user) {
		this.authUser = user;
	}
}