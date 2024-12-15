package model;

import java.sql.ResultSet;

import controller.IdGenerator;
import util.Connect;

public class Item {
	private String itemId, userId, itemName, itemSize, itemPrice, itemCategory, itemStatus, itemWishlist, itemOfferStatus;

	public Item(String itemId, String userId, String itemName, String itemSize, String itemPrice, String itemCategory,
			String itemStatus, String itemWishlist, String itemOfferStatus) {
		super();
		this.itemId = itemId;
		this.userId = userId;
		this.itemName = itemName;
		this.itemSize = itemSize;
		this.itemPrice = itemPrice;
		this.itemCategory = itemCategory;
		this.itemStatus = itemStatus;
		this.itemWishlist = itemWishlist;
		this.itemOfferStatus = itemOfferStatus; // Available, Offering, Sold
	}
	
	public static boolean uploadItem(String item_name, String user_id, String item_category, String item_size, String item_price) {
		String itemId = IdGenerator.generateId("items", "IT");
		Item newItem = new Item(itemId, user_id, item_name, item_size, item_price, item_category, null, null, null);
		
		String query = "INSERT INTO items (itemId, userId, itemName, itemCategory, itemSize, itemPrice) VALUES (?, ?, ?, ?, ?, ?)";
        Object[] params = { newItem.getItemId(), newItem.getUserId(), newItem.getItemName(), newItem.getItemCategory(), newItem.getItemSize(), newItem.getItemPrice() };
	
        if (Connect.getInstance().execUpdate(query, params)) return true;
        
        return false;
	}
	
	// Logic untuk mendapatkan model item 
	public static ResultSet getItemById(String item_id) {
		String query = "SELECT * FROM items WHERE itemId LIKE ? LIMIT 1";
        Object[] params = { item_id };
        
        ResultSet rs = Connect.getInstance().execQuery(query, params);
        
        return rs;
	}
	
	public static boolean editItem(String item_id, String item_name, String item_category, String item_size, String item_price) {
		System.out.println(item_id);
		String query = "UPDATE items SET itemName = ?, itemCategory = ?, itemSize = ?, itemPrice = ? WHERE itemId = ?";
        Object[] params = { item_name, item_category, item_size, item_price, item_id };
	
        if (Connect.getInstance().execUpdate(query, params)) return true;
        
        return false;
	}
	
	public static boolean deleteItem(String item_id) {
		String query = "DELETE FROM items WHERE itemId LIKE ?";
        Object[] params = { item_id };
        
        if (Connect.getInstance().execUpdate(query, params)) return true;
        
        return false;
	}

	public static ResultSet browseItem(String item_name) {
		String query = "SELECT * FROM items WHERE itemStatus LIKE 'Approved' && itemOfferStatus NOT LIKE 'Sold' && itemName LIKE ?";
        Object[] params = { "%" + item_name + "%" };
        
        ResultSet rs = Connect.getInstance().execQuery(query, params);
		
		return rs;
	}
	
	public static ResultSet viewItem() {
		String query = "SELECT * FROM items WHERE itemStatus LIKE 'Approved' && itemOfferStatus NOT LIKE 'Sold'";

		ResultSet rs = Connect.getInstance().execQuery(query, new Object[] {});
		
		return rs;
	}
	
	// Untuk mengembalikan item yang tidak ada di wishlist
	public static ResultSet viewItemNotInWishlist(String user_id) {
		String query = "SELECT items.itemId, items.userId, items.itemName, items.itemSize, items.itemPrice, items.itemCategory, items.itemStatus, items.itemWishlist, items.itemOfferStatus FROM items WHERE items.itemStatus LIKE 'Approved' AND items.itemOfferStatus NOT LIKE 'Sold' AND items.itemId NOT IN (SELECT wishlists.itemId FROM wishlists WHERE wishlists.userId LIKE ?)";
        Object[] params = { user_id };
        
        ResultSet rs = Connect.getInstance().execQuery(query, params);
        
        return rs;
	}
	
	public static ResultSet viewRequestedItem(String item_id, String item_status) {
		String query = "SELECT * FROM items WHERE itemStatus LIKE 'Pending'";
		
		ResultSet rs = Connect.getInstance().execQuery(query, new Object[] {});
		
		return rs;
	}
	
	// Function untuk update item yang sedang dioffering
	public static boolean updateItemToOffering(String item_id) {
		String query = "UPDATE items SET itemOfferStatus = 'Offering' WHERE itemId LIKE ?";
        Object[] params = { item_id };
	
        if (Connect.getInstance().execUpdate(query, params)) return true;
        
        return false;
	}
	
	// Function untuk mendelete last offer sehingga akan ditimpah dengan offer baru
	public static void deleteLastOffer(String item_id) {
		String query = "DELETE FROM offers WHERE itemId LIKE ?";
        Object[] params = { item_id };
        
        Connect.getInstance().execUpdate(query, params);
	}
	
	public static ResultSet getLastOffer(String item_id) {
		String query = "SELECT offerPrice FROM offers WHERE itemId LIKE ? LIMIT 1";
		Object[] params = { item_id };
		
		ResultSet rs = Connect.getInstance().execQuery(query, params);
		
		return rs;
	}
	
	public static boolean offerPrice(String item_id, String user_id, String offer_price) {
		String offerId = IdGenerator.generateId("offers", "OF");
		Offer offer = new Offer(offerId, item_id, user_id, offer_price);
		
		String query = "INSERT INTO offers (offerId, itemId, userId, offerPrice) VALUES (?, ?, ?, ?)";
		Object[] params = { offer.getOfferId(), offer.getItemId(), offer.getUserId(), offer.getOfferPrice() };
		
		if(Connect.getInstance().execUpdate(query, params)) return true;
        
		return false;
	}
	
	public static boolean acceptOffer(String item_id) {
		if (Transaction.updateItemToSold(item_id)) return true;
		
		return false;
	}
	
	public static boolean declineOffer(String item_id) {
		deleteLastOffer(item_id);
		
		return true;
	}
	
	public static boolean approveItem(String item_id) {
		String query = "UPDATE items SET itemStatus = 'Approved' WHERE itemId = ?";
        Object[] params = { item_id };
        
        if (Connect.getInstance().execUpdate(query, params)) return true;
        
        return false;
	}
	
	public static boolean declineItem(String item_id) {
		String query = "UPDATE items SET itemStatus = 'Declined' WHERE itemId = ?";
        Object[] params = { item_id };
        
        if (Connect.getInstance().execUpdate(query, params)) return true;
        
        return false;
	}
	
	public static ResultSet viewAcceptedItem(String item_id) {
		String query = "SELECT * FROM items WHERE itemStatus LIKE 'Approved'";
		
		ResultSet rs = Connect.getInstance().execQuery(query, new Object[] {});
		
		return rs;
	}
	
	public static ResultSet viewSellerItem(String user_id) {
		String query = "SELECT * FROM items WHERE userId LIKE ?";
		Object[] params = { user_id };
		
		ResultSet rs = Connect.getInstance().execQuery(query, params);
		
		return rs;
	}
	
	public static ResultSet viewOfferItem(String user_id) {
		String query = "SELECT offers.offerId, offers.itemId, offers.userId, items.itemName, items.itemCategory, items.itemSize, items.itemPrice, offers.offerPrice FROM items JOIN offers ON items.itemId = offers.itemId WHERE items.userId LIKE ?";
		Object[] params = { user_id };

		ResultSet rs = Connect.getInstance().execQuery(query, params);
		
		return rs;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getItemSize() {
		return itemSize;
	}

	public void setItemSize(String itemSize) {
		this.itemSize = itemSize;
	}

	public String getItemPrice() {
		return itemPrice;
	}

	public void setItemPrice(String itemPrice) {
		this.itemPrice = itemPrice;
	}

	public String getItemCategory() {
		return itemCategory;
	}

	public void setItemCategory(String itemCategory) {
		this.itemCategory = itemCategory;
	}

	public String getItemStatus() {
		return itemStatus;
	}

	public void setItemStatus(String itemStatus) {
		this.itemStatus = itemStatus;
	}

	public String getItemWhislist() {
		return itemWishlist;
	}

	public void setItemWhislist(String itemWishlist) {
		this.itemWishlist = itemWishlist;
	}

	public String getItemOfferStatus() {
		return itemOfferStatus;
	}

	public void setItemOfferStatus(String itemOfferStatus) {
		this.itemOfferStatus = itemOfferStatus;
	}

}
