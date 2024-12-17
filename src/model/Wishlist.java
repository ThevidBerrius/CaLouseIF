package model;

import java.sql.ResultSet;

import controller.IdGenerator;
import util.Connect;

public class Wishlist {
	private String wishlistId, itemId, userId;

	public Wishlist(String wishlistId, String itemId, String userId) {
		super();
		this.wishlistId = wishlistId;
		this.itemId = itemId;
		this.userId = userId;
	}
	
	// Disini saya mengasumsikan jika ingin melihat wishlist tidak perlu menggunakan wishlist_id kecuali searching
	public static ResultSet viewWishlist(String wishlist_id, String user_id) {
		String query = "SELECT wishlists.wishlistId, wishlists.userId, wishlists.itemId, items.itemName, items.itemSize, items.itemPrice, items.itemCategory, items.itemStatus, items.itemWishlist, items.itemOfferStatus FROM wishlists JOIN items ON wishlists.itemId = items.itemId WHERE wishlists.userId LIKE ? && items.itemOfferStatus NOT LIKE 'Sold'";
        Object[] params = { user_id };

        ResultSet rs = Connect.getInstance().execQuery(query, params);
		
		return rs;
	}
	
	public static boolean addWishlist(String item_id, String user_id) {
		String wishlistId = IdGenerator.generateId("wishlists", "WI");
		Wishlist newWishlist = new Wishlist(wishlistId, item_id, user_id);
		
		String query = "INSERT INTO wishlists (wishlistId, itemId, userId) VALUES (?, ?, ?)";
        Object[] params = { newWishlist.getWishlistId(), item_id, user_id };
        
        if(Connect.getInstance().execUpdate(query, params)) return true;
        
        return false;
	}
	
	public static boolean removeWishlist(String wishlist_id) {
		String query = "DELETE FROM wishlists WHERE wishlistId LIKE ?";
        Object[] params = { wishlist_id };
        
        if(Connect.getInstance().execUpdate(query, params)) return true;
		
		return false;
	}

	public String getWishlistId() {
		return wishlistId;
	}

	public void setWishlistId(String wishlistId) {
		this.wishlistId = wishlistId;
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
}
