package controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import model.Wishlist;
import model.WishlistItem;
import util.Connect;

public class WishlistController {
	private IdGenerator idGenerator;
	
	public WishlistController() {
		this.idGenerator = new IdGenerator();
	}
	
	public Vector<WishlistItem> viewWishlist(String wishlist_id, String user_id) {
		Vector<WishlistItem> wishlistItems = new Vector<>();
		String query = "SELECT wishlists.wishlistId, wishlists.userId, wishlists.itemId, items.itemName, items.itemSize, items.itemPrice, items.itemCategory, items.itemStatus, items.itemWishlist, items.itemOfferStatus FROM wishlists JOIN items ON wishlists.itemId = items.itemId WHERE wishlists.userId LIKE ? && items.itemOfferStatus LIKE 'Available'";
        Object[] params = { user_id };
		
		ResultSet rs = Connect.getInstance().execQuery(query, params);
		
		try {
            while (rs.next()) {
            	String wishlistId = rs.getString("wishlistId");
                String itemId = rs.getString("itemId");
                String userId = rs.getString("userId");
                String itemName = rs.getString("itemName");
                String itemSize = rs.getString("itemSize");
                String itemPrice = rs.getString("itemPrice");
                String itemCategory = rs.getString("itemCategory");
                String itemStatus = rs.getString("itemStatus");
                String itemWishlist = rs.getString("itemWishlist");
                String itemOfferStatus = rs.getString("itemOfferStatus");
                wishlistItems.add(new WishlistItem(wishlistId, userId, itemId, itemName, itemPrice, itemSize, itemCategory, itemStatus, itemWishlist, itemOfferStatus));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } 
		
		return wishlistItems;
	}

	public String addWishlist(String itemId, String userId) {
		String wishlistId = this.idGenerator.generateId("wishlists", "WI");
		Wishlist newWishlist = new Wishlist(wishlistId, itemId, userId);
		
		String query = "INSERT INTO wishlists (wishlistId, itemId, userId) VALUES (?, ?, ?)";
        Object[] params = { newWishlist.getWishlistId(), itemId, userId };
        
        if(Connect.getInstance().execUpdate(query, params)) {
        	return "Success";
        }
        
        return "Error Insert to Database";
	}
}
