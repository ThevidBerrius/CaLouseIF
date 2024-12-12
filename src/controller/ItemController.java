package controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import main.SceneManager;
import model.Item;
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
		Item newItem = new Item(itemId, itemName, itemSize, itemPrice, itemCategory, null, null, null);
		
		String query = "INSERT INTO items (itemId, itemName, itemCategory, itemSize, itemPrice) VALUES (?, ?, ?, ?, ?)";
        Object[] params = { newItem.getItemId(), newItem.getItemName(), newItem.getItemCategory(), newItem.getItemSize(), newItem.getItemPrice() };
        
        if(Connect.getInstance().execUpdate(query, params)) {
        	return "Success";
        }
        
        return "Error Insert to Database";
	}
	
	public String editItem(String itemId, String itemName, String itemCategory, String itemSize, String itemPrice) {
		String message = checkItemValidation(itemName, itemCategory, itemSize, itemPrice);
		
		if (!message.equals("Validation Success")) return message;
		
		Item newItem = new Item(itemId, itemName, itemSize, itemPrice, itemCategory, null, null, null);
		
		String query = "UPDATE items SET itemName = ?, itemCategory = ?, itemSize = ?, itemPrice = ? WHERE itemId = ?";
        Object[] params = { newItem.getItemName(), newItem.getItemCategory(), newItem.getItemSize(), newItem.getItemPrice(), newItem.getItemId(),  };
        
        if(Connect.getInstance().execUpdate(query, params)) {
        	return "Success";
        }
		
		return "Error Insert to Database";
	}
	
	public void deleteItem(String itemId) {
		
	}
	
	public void browseItem(String itemName) {
		
	}
	
	public Vector<Item> viewItem() {
		Vector<Item> items = new Vector<Item>();
		String query = "SELECT * FROM items WHERE itemStatus LIKE 'Approved'";
		
		ResultSet rs = Connect.getInstance().execQuery(query, new Object[] {});
		
		try {
            while (rs.next()) {
            	String itemId = rs.getString("itemId");
                String itemName = rs.getString("itemName");
                String itemSize = rs.getString("itemSize");
                String itemPrice = rs.getString("itemPrice");
                String itemCategory = rs.getString("itemCategory");
                String itemStatus = rs.getString("itemStatus");
                String itemWishlist = rs.getString("itemWishlist");
                String itemOfferStatus = rs.getString("itemOfferStatus");
                items.add(new Item(itemId, itemName, itemSize, itemPrice, itemCategory, itemStatus, itemWishlist, itemOfferStatus));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } 
		
		return items;
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
	
	// Disini saya asumsikan bahwa viewRequestedItem tidak membutuhkan parameter
	public Vector<Item> viewRequestedItem() {
		Vector<Item> items = new Vector<Item>();
		String query = "SELECT * FROM items WHERE itemStatus LIKE 'Pending'";
		
		ResultSet rs = Connect.getInstance().execQuery(query, new Object[] {});
		
		try {
            while (rs.next()) {
            	String itemId = rs.getString("itemId");
                String itemName = rs.getString("itemName");
                String itemSize = rs.getString("itemSize");
                String itemPrice = rs.getString("itemPrice");
                String itemCategory = rs.getString("itemCategory");
                String itemStatus = rs.getString("itemStatus");
                String itemWishlist = rs.getString("itemWishlist");
                String itemOfferStatus = rs.getString("itemOfferStatus");
                items.add(new Item(itemId, itemName, itemSize, itemPrice, itemCategory, itemStatus, itemWishlist, itemOfferStatus));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } 
		
		return items;
	}
	
	public boolean approveItem(String itemId, SceneManager sceneManager) {
		String query = "UPDATE items SET itemStatus = 'Approved' WHERE itemId = ?";
        Object[] params = { itemId };
        
        if(Connect.getInstance().execUpdate(query, params)) {
        	sceneManager.switchPage("adminrequested");
        }
        
        return false;
	}
	
	public boolean declineItem(String itemId, SceneManager sceneManager) {
		String query = "UPDATE items SET itemStatus = 'Declined' WHERE itemId = ?";
        Object[] params = { itemId };
        
        if(Connect.getInstance().execUpdate(query, params)) {
        	sceneManager.switchPage("adminrequested");
        }
        
        return false;
	}
	
	public Vector<Item> viewAcceptedItem() {
		Vector<Item> items = new Vector<Item>();
		String query = "SELECT * FROM items WHERE itemStatus LIKE 'Approved'";
		
		ResultSet rs = Connect.getInstance().execQuery(query, new Object[] {});
		
		try {
            while (rs.next()) {
            	String itemId = rs.getString("itemId");
                String itemName = rs.getString("itemName");
                String itemSize = rs.getString("itemSize");
                String itemPrice = rs.getString("itemPrice");
                String itemCategory = rs.getString("itemCategory");
                String itemStatus = rs.getString("itemStatus");
                String itemWishlist = rs.getString("itemWishlist");
                String itemOfferStatus = rs.getString("itemOfferStatus");
                items.add(new Item(itemId, itemName, itemSize, itemPrice, itemCategory, itemStatus, itemWishlist, itemOfferStatus));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } 
		
		return items;
	}
}