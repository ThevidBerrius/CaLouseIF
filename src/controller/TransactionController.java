package controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import model.Transaction;
import model.TransactionHistory;

public class TransactionController {
	private IdGenerator idGenerator;
	
	public TransactionController() {
		this.idGenerator = new IdGenerator();
	}
	
	public void purchaseItems(String userId, String itemId) {
        if (Transaction.purchaseItems(userId, itemId)) createTransaction(idGenerator.generateId("transactions", "TR"), userId, itemId);
        else System.out.println("Error Delete Database");
	}
	
	public Vector<TransactionHistory> viewHistory(String userId) {
		Vector<TransactionHistory> transactionHistory = new Vector<>();
		ResultSet rs = Transaction.viewHistory(userId);
		
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
		if (Transaction.createTransaction(transactionId, userId, itemId)) {
			return "Success";
		}
		
		return "Error Insert to Database";
	}
}
