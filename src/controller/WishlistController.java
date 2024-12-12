package controller;

import model.Item;
import model.Wishlist;
import util.Connect;

public class WishlistController {
	private IdGenerator idGenerator;

	public String addWishlist(String itemId, String userId) {
		String wishlistId = this.idGenerator.generateId("wishlists", "WI");
		Wishlist newWishlist = new Wishlist(wishlistId, itemId, userId);
		
		String query = "INSERT INTO wishlists (wishlistId, itemId, userId) VALUES (?, ?, ?)";
        Object[] params = { newWishlist.getWishlistId(), newWishlist.getItemId(), newWishlist.getUserId() };
        
        if(Connect.getInstance().execUpdate(query, params)) {
        	return "Success";
        }
        
        return "Error Insert to Database";
	}
}
