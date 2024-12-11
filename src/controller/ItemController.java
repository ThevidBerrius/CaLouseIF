package controller;

import model.User;
import util.Connect;

public class ItemController {
	private IdGenerator idGenerator;
	
	public ItemController() {
		this.idGenerator = new IdGenerator();
	}

	public String uploadItem(String itemName, String itemCategory, String itemSize, String itemPrice) {
		String message = checkItemValidation(itemName, itemCategory, itemSize, itemPrice);
		
		if (!message.equals("Validation Success")) return message;
		
		String itemId = this.idGenerator.generateId("items", "IT");
		
		String query = "INSERT INTO items (id, itemName, itemCategory, itemSize, itemPrice) VALUES (?, ?, ?, ?, ?)";
        Object[] params = { itemId, itemName, itemCategory, itemSize, itemPrice };
        
        if(Connect.getInstance().execUpdate(query, params)) {
        	return "Success";
        }
        
        return "Error Insert to Database";
	}
	
	private String checkItemValidation(String itemName, String itemCategory, String itemSize, String itemPrice) {
		// Item Name
		if (itemName.isEmpty() || itemName.length() < 3) {
			return "Item Name must not be empty and length below 3";
		} 
		
		// Item Category
		if (itemCategory.isEmpty() || itemCategory.length() < 3) {
			return "Item Category must not be empty and length below 3";
		}
		
		// Item Size
		if (itemSize.isEmpty()) {
			return "Item Size must not be empty";
		}
		
		// Item Price
		if (itemPrice.isEmpty() || itemPrice.equals("0")) {
			return "Item Price must not be empty and cannot be 0";
		}
		
		for (char c : itemPrice.toCharArray()) {
			if(!Character.isDigit(c)) return "Item Price must be number";
		}
		
		return "Validation Success";
	}
}
