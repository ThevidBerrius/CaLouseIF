package controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import model.Item;
import model.Transaction;
import model.TransactionHistory;
import model.WishlistItem;
import util.Connect;

public class TransactionController {
	private IdGenerator idGenerator;
	
	public TransactionController() {
		this.idGenerator = new IdGenerator();
	}
	
	// Disini saya mengasumsikan untuk menghapus semua data yang ada di wishlist berdasarkan itemId
	// Karena item yang sudah dibeli tidak boleh ada di wishlist orang lain lagi
	public void purchaseItems(String userId, String itemId) {
		String query = "DELETE FROM wishlists WHERE itemId LIKE ?";
        Object[] params = { itemId };
        
        if(Connect.getInstance().execUpdate(query, params)) {        
        	createTransaction(this.idGenerator.generateId("transactions", "TR"), userId, itemId);
        }
	}
	
	public Vector<TransactionHistory> viewHistory(String userId) {
		Vector<TransactionHistory> transactionHistory = new Vector<>();
		String query = "SELECT transactions.transactionId, items.itemName, items.itemCategory, items.itemSize, items.itemPrice FROM transactions JOIN items ON transactions.itemId = items.itemId WHERE transactions.userId LIKE ?";
        Object[] params = { userId };
		
		ResultSet rs = Connect.getInstance().execQuery(query, params);
		
		try {
            while (rs.next()) {
            	String transactionId = rs.getString("transactionId");
            	String itemName = rs.getString("itemName");
            	String itemCategory = rs.getString("itemCategory");
            	String itemSize = rs.getString("itemSize");
            	String itemPrice = rs.getString("itemPrice");
                transactionHistory.add(new TransactionHistory(transactionId, itemName, itemCategory, itemSize, itemPrice));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } 
		
		return transactionHistory;
	}
	
	public String createTransaction(String transactionId, String userId, String itemId) {
		Transaction newTransaction = new Transaction(transactionId, userId, itemId);
		
		String query = "INSERT INTO transactions (transactionId, userId, itemId) VALUES (?, ?, ?)";
        Object[] params = { newTransaction.getTransactionId(), newTransaction.getUserId(), newTransaction.getItemId() };
        
        if(Connect.getInstance().execUpdate(query, params)) {
        	return "Success";
        }
        
        return "Error Insert to Database";
	}
}
