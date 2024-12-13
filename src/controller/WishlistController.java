package controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import model.Wishlist;
import model.WishlistItem;

public class WishlistController {
	public Vector<WishlistItem> viewWishlist(String wishlist_id, String user_id) {
		Vector<WishlistItem> wishlistItems = new Vector<>();
		ResultSet rs = Wishlist.viewWishlist(wishlist_id, user_id);
		
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

	public String addWishlist(String item_id, String user_id) {
		if (Wishlist.addWishlist(item_id, user_id)) return "Success";
		
		return "Error Insert to Database";
	}	
	
	public boolean removeWishlist(String wishlist_id) {
		if (Wishlist.removeWishlist(wishlist_id)) return true;
		
		return false;
	}
}
