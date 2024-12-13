package controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import main.SceneManager;
import model.Item;
import model.Offer;
import util.Connect;

public class ItemController {
	private IdGenerator idGenerator;
	
	public ItemController() {
		this.idGenerator = new IdGenerator();
	}

	public String uploadItem(String itemName, String userId, String itemCategory, String itemSize, String itemPrice) {
		String message = checkItemValidation(itemName, itemCategory, itemSize, itemPrice);
		
		if (!message.equals("Validation Success")) return message;
		
		String itemId = this.idGenerator.generateId("items", "IT");
		Item newItem = new Item(itemId, userId, itemName, itemSize, itemPrice, itemCategory, null, null, null);
		
		String query = "INSERT INTO items (itemId, userId, itemName, itemCategory, itemSize, itemPrice) VALUES (?, ?, ?, ?, ?, ?)";
        Object[] params = { newItem.getItemId(), newItem.getUserId(), newItem.getItemName(), newItem.getItemCategory(), newItem.getItemSize(), newItem.getItemPrice() };
        
        if(Connect.getInstance().execUpdate(query, params)) {
        	return "Success";
        }
        
        return "Error Insert to Database";
	}
	
	public String editItem(String itemId, String userId, String itemName, String itemCategory, String itemSize, String itemPrice) {
		String message = checkItemValidation(itemName, itemCategory, itemSize, itemPrice);
		
		if (!message.equals("Validation Success")) return message;
		
		Item newItem = new Item(itemId, userId, itemName, itemSize, itemPrice, itemCategory, null, null, null);
		
		String query = "UPDATE items SET itemName = ?, itemCategory = ?, itemSize = ?, itemPrice = ? WHERE itemId = ?";
        Object[] params = { newItem.getItemName(), newItem.getItemCategory(), newItem.getItemSize(), newItem.getItemPrice(), newItem.getItemId() };
        
        if(Connect.getInstance().execUpdate(query, params)) {
        	return "Success";
        }
		
		return "Error Update to Database";
	}
	
	public void deleteItem(String itemId) {
		String query = "DELETE FROM items WHERE itemId LIKE ?";
        Object[] params = { itemId };
        
        if(Connect.getInstance().execUpdate(query, params)) {
        	return;
        }
	}
	
	public void browseItem(String itemName) {
		
	}
	
	public Vector<Item> viewItem() {
		Vector<Item> items = new Vector<>();
		String query = "SELECT * FROM items WHERE itemStatus LIKE 'Approved' && itemOfferStatus LIKE 'Available'";
		
		ResultSet rs = Connect.getInstance().execQuery(query, new Object[] {});
		
		try {
            while (rs.next()) {
            	String itemId = rs.getString("itemId");
            	String userId = rs.getString("userId");
                String itemName = rs.getString("itemName");
                String itemSize = rs.getString("itemSize");
                String itemPrice = rs.getString("itemPrice");
                String itemCategory = rs.getString("itemCategory");
                String itemStatus = rs.getString("itemStatus");
                String itemWishlist = rs.getString("itemWishlist");
                String itemOfferStatus = rs.getString("itemOfferStatus");
                items.add(new Item(itemId, userId, itemName, itemSize, itemPrice, itemCategory, itemStatus, itemWishlist, itemOfferStatus));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } 
		
		return items;
	}
	
	// Menampilkan semua data item yang tidak berada di wishlist
	public Vector<Item> viewItemNotInWishlist(String user_id){
		Vector<Item> items = new Vector<>();
		String query = "SELECT items.itemId, items.userId, items.itemName, items.itemSize, items.itemPrice, items.itemCategory, items.itemStatus, items.itemWishlist, items.itemOfferStatus FROM items WHERE items.itemStatus LIKE 'Approved' AND items.itemOfferStatus LIKE 'Available' AND items.itemId NOT IN (SELECT wishlists.itemId FROM wishlists WHERE wishlists.userId = ?)";
        Object[] params = { user_id };
		
		ResultSet rs = Connect.getInstance().execQuery(query, params);
		
		try {
            while (rs.next()) {
            	String itemId = rs.getString("itemId");
            	String userId = rs.getString("userId");
                String itemName = rs.getString("itemName");
                String itemSize = rs.getString("itemSize");
                String itemPrice = rs.getString("itemPrice");
                String itemCategory = rs.getString("itemCategory");
                String itemStatus = rs.getString("itemStatus");
                String itemWishlist = rs.getString("itemWishlist");
                String itemOfferStatus = rs.getString("itemOfferStatus");
                items.add(new Item(itemId, userId, itemName, itemSize, itemPrice, itemCategory, itemStatus, itemWishlist, itemOfferStatus));
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
		Vector<Item> items = new Vector<>();
		String query = "SELECT * FROM items WHERE itemStatus LIKE 'Pending'";
		
		ResultSet rs = Connect.getInstance().execQuery(query, new Object[] {});
		
		try {
            while (rs.next()) {
            	String itemId = rs.getString("itemId");
            	String userId = rs.getString("userId");
                String itemName = rs.getString("itemName");
                String itemSize = rs.getString("itemSize");
                String itemPrice = rs.getString("itemPrice");
                String itemCategory = rs.getString("itemCategory");
                String itemStatus = rs.getString("itemStatus");
                String itemWishlist = rs.getString("itemWishlist");
                String itemOfferStatus = rs.getString("itemOfferStatus");
                items.add(new Item(itemId, userId, itemName, itemSize, itemPrice, itemCategory, itemStatus, itemWishlist, itemOfferStatus));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } 
		
		return items;
	}
	
	public void offerPrice(String itemId, String itemPrice) {
		
	}
	
	public void acceptOffer(String itemId) {
		
	}
	
	public void declineOffer(String itemId) {
		
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
		Vector<Item> items = new Vector<>();
		String query = "SELECT * FROM items WHERE itemStatus LIKE 'Approved'";
		
		ResultSet rs = Connect.getInstance().execQuery(query, new Object[] {});
		
		try {
            while (rs.next()) {
            	String itemId = rs.getString("itemId");
            	String userId = rs.getString("userId");
                String itemName = rs.getString("itemName");
                String itemSize = rs.getString("itemSize");
                String itemPrice = rs.getString("itemPrice");
                String itemCategory = rs.getString("itemCategory");
                String itemStatus = rs.getString("itemStatus");
                String itemWishlist = rs.getString("itemWishlist");
                String itemOfferStatus = rs.getString("itemOfferStatus");
                items.add(new Item(itemId, userId, itemName, itemSize, itemPrice, itemCategory, itemStatus, itemWishlist, itemOfferStatus));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } 
		
		return items;
	}
	
	// Function untuk mengembalikan data itemm yang telah dibuat oleh seller
	public Vector<Item> viewSellerItem(String user_id){
		Vector<Item> items = new Vector<>();
		String query = "SELECT * FROM items WHERE userId LIKE ?";
		Object[] params = { user_id };
		
		ResultSet rs = Connect.getInstance().execQuery(query, params);
		
		try {
            while (rs.next()) {
            	String itemId = rs.getString("itemId");
            	String userId = rs.getString("userId");
                String itemName = rs.getString("itemName");
                String itemSize = rs.getString("itemSize");
                String itemPrice = rs.getString("itemPrice");
                String itemCategory = rs.getString("itemCategory");
                String itemStatus = rs.getString("itemStatus");
                String itemWishlist = rs.getString("itemWishlist");
                String itemOfferStatus = rs.getString("itemOfferStatus");
                items.add(new Item(itemId, userId, itemName, itemSize, itemPrice, itemCategory, itemStatus, itemWishlist, itemOfferStatus));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } 
		
		return items;
	}
	
	public Vector<Offer> viewOfferItem(String user_id) {
		Vector<Offer> offers = new Vector<>();
		String query = "SELECT offers.offerId, offers.itemId, offers.userId, items.itemName, items.itemCategory, items.itemSize, items.itemPrice, offers.offerPrice, offers.status FROM items JOIN offers ON items.itemId = offers.itemId WHERE items.userId LIKE ?";
		Object[] params = { user_id };
		
		ResultSet rs = Connect.getInstance().execQuery(query, params);
		
		try {
            while (rs.next()) {
            	String offerId = rs.getString("offerId");
            	String itemId = rs.getString("itemId");
            	String userId = rs.getString("userId");
                String itemName = rs.getString("itemName");
                String itemCategory = rs.getString("itemCategory");
                String itemSize = rs.getString("itemSize");
                String itemPrice = rs.getString("itemPrice");
                String offerPrice = rs.getString("offerPrice");
                String status = rs.getString("status");
                offers.add(new Offer(offerId, itemId, userId, itemName, itemCategory, itemSize, itemPrice, offerPrice, status));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } 
		
		return offers;
	}
}