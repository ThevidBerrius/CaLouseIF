package model;

import java.sql.ResultSet;

import controller.IdGenerator;
import util.Connect;

public class Transaction {
	private String transactionId, userId, itemId;

	public Transaction(String transactionId, String userId, String itemId) {
		super();
		this.transactionId = transactionId;
		this.userId = userId;
		this.itemId = itemId;
	}
	
	public static boolean updateItemToSold(String item_id) {
		String query = "UPDATE items SET itemOfferStatus = 'Sold' WHERE itemId LIKE ?";
        Object[] params = { item_id };
        
        if (Connect.getInstance().execUpdate(query, params)) {
        	return true;
        }
        
        return false;
	}
	
	// Disini saya mengasumsikan untuk menghapus semua data yang ada di wishlist berdasarkan itemId
	// Karena item yang sudah dibeli tidak boleh ada di wishlist orang lain lagi
	public static boolean purchaseItems(String user_id, String item_id) {
		String query = "DELETE FROM wishlists WHERE itemId LIKE ?";
        Object[] params = { item_id };
        
        Connect.getInstance().execUpdate(query, params);
        
        if (!updateItemToSold(item_id)) return false;
        
        createTransaction(IdGenerator.generateId("transactions", "TR"), user_id, item_id);
        
        return true;
	}
	
	public static ResultSet viewHistory(String user_id) {
		String query = "SELECT transactions.transactionId, items.itemName, items.itemCategory, items.itemSize, items.itemPrice FROM transactions JOIN items ON transactions.itemId = items.itemId WHERE transactions.userId LIKE ?";
        Object[] params = { user_id };
		
		ResultSet rs = Connect.getInstance().execQuery(query, params);
		
		return rs;
	}
	
	// Saya mengasumsikan untuk membuat transaction harus membutuhkan userId dan itemId juga
	public static boolean createTransaction(String transaction_id, String user_id, String item_id) {
		Transaction newTransaction = new Transaction(transaction_id, user_id, item_id);
		String query = "INSERT INTO transactions (transactionId, userId, itemId) VALUES (?, ?, ?)";
        Object[] params = { newTransaction.getTransactionId(), newTransaction.getUserId(), newTransaction.getItemId() };
        
        if(Connect.getInstance().execUpdate(query, params)) {
        	return true;
        }
        
        return false;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

}
