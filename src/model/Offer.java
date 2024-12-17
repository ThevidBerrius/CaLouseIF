package model;

import java.sql.ResultSet;

import util.Connect;

public class Offer {
	private String offerId, itemId, userId, offerPrice;

	public Offer(String offerId, String itemId, String userId, String offerPrice) {
		super();
		this.offerId = offerId;
		this.itemId = itemId;
		this.userId = userId;
		this.offerPrice = offerPrice;
	}
	
	// Kalau offer sudah diapprove maka status offer akan menjadi accepted
	public static boolean updateOfferToAccepted(String item_id) {
		String query = "UPDATE offers SET status = 'Accepted' WHERE itemId LIKE ?";
        Object[] params = { item_id };
	
        if (Connect.getInstance().execUpdate(query, params)) return true;
        
        return false;
	}
	
	// Function untuk mendapatkan user id dari table offer
	public static ResultSet getOfferUserId(String item_id) {
		String query = "SELECT userId FROM offers WHERE itemId LIKE ? LIMIT 1";
		Object[] params = { item_id };
		
		ResultSet rs = Connect.getInstance().execQuery(query, params);
		
		return rs;
	}

	public String getOfferId() {
		return offerId;
	}

	public void setOfferId(String offerId) {
		this.offerId = offerId;
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

	public String getOfferPrice() {
		return offerPrice;
	}

	public void setOfferPrice(String offerPrice) {
		this.offerPrice = offerPrice;
	}
}
