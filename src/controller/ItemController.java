package controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import main.SceneManager;
import model.Item;
import model.Offer;
import model.OfferItem;
import util.Connect;

public class ItemController {
	public String uploadItem(String item_name, String user_id, String item_category, String item_size, String item_price) {
		String message = checkItemValidation(item_name, item_category, item_size, item_price);
		
		if (!message.equals("Validation Success")) return message;
		
        if (Item.uploadItem(item_name, user_id, item_category, item_size, item_price)) return "Success";
        
        return "Error Insert to Database";
	}
	
	public String editItem(String item_id, String item_name, String item_category, String item_size, String item_price) {
		String message = checkItemValidation(item_name, item_category, item_size, item_price);
		
		if (!message.equals("Validation Success")) return message;
		
		Item currentItem = null;
		ResultSet rs = Item.getItemById(item_id);
		
		try {
			if (rs.next()) {
				String itemId = rs.getString("itemId");
            	String userId = rs.getString("userId");
                String itemName = rs.getString("itemName");
                String itemSize = rs.getString("itemSize");
                String itemPrice = rs.getString("itemPrice");
                String itemCategory = rs.getString("itemCategory");
                String itemStatus = rs.getString("itemStatus");
                String itemWishlist = rs.getString("itemWishlist");
                String itemOfferStatus = rs.getString("itemOfferStatus");
                currentItem = new Item(itemId, userId, itemName, itemSize, itemPrice, itemCategory, itemStatus, itemWishlist, itemOfferStatus);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		currentItem.setItemName(item_name);
		currentItem.setItemCategory(item_category);
		currentItem.setItemSize(item_size);
		currentItem.setItemPrice(item_price);
		
        if (Item.editItem(currentItem.getItemId(), currentItem.getItemName(), currentItem.getItemCategory(), currentItem.getItemSize(), currentItem.getItemPrice())) return "Success";
		
		return "Error Update to Database";
	}
	
	public boolean deleteItem(String itemId) {
        if (Item.deleteItem(itemId)) return true;
        
        return false;
	}
	
	public Vector<Item> browseItem(String item_name) {
		Vector<Item> items = new Vector<>();
		ResultSet rs = Item.browseItem(item_name);
		
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
	
	public Vector<Item> viewItem() {
		Vector<Item> items = new Vector<>();
		ResultSet rs = Item.viewItem();
		
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
		ResultSet rs = Item.viewItemNotInWishlist(user_id);
		
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
	
	// Disini saya asumsikan bahwa viewRequestedItem tidak membutuhkan parameter, sehingga dipassing dengan null
	public Vector<Item> viewRequestedItem(String item_id, String item_status) {
		Vector<Item> items = new Vector<>();
		ResultSet rs = Item.viewRequestedItem(item_id, item_status);
		
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
	
	// Function untuk mendapatkan harga tertinggi dari offer sebelumnya
	public int getHighestOffer(String item_id) {
		int highest_offer = 0;
		ResultSet rs = Item.getLastOffer(item_id);
		
		try {
            if (rs.next()) {
            	highest_offer = Integer.parseInt(rs.getString("offerPrice"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } 
		
		return highest_offer;
	}
	
	// Validasi untuk offer price dari user
	private String offerPriceValidation(String item_price, int highest_offer) {
		if (item_price.isEmpty()) return "Offer must not be empty";
		
		for (char c : item_price.toCharArray()) {
			if(!Character.isDigit(c)) return "Offer must be number";
		}
		
		if (Integer.parseInt(item_price) <= 0) return "Offer must more than 0";
		
		if (highest_offer >= Integer.parseInt(item_price)) return "Offer must more than latest offer " + highest_offer;
		
		return "Validation Success";
	}
	
	// Disini saya mengasumsikan item_price sebagai harga yang dioffer dari buyer
	public String offerPrice(String item_id, String user_id, String item_price, int highest_offer) {
		String message = offerPriceValidation(item_price, highest_offer);
		
		if (!message.equals("Validation Success")) return message;
		
		if (!Item.updateItemToOffering(item_id)) return "Error Update to Database";
		
		Item.deleteLastOffer(item_id);
		
		if (Item.offerPrice(item_id, user_id, item_price)) return "Success";
		
		return "Error Insert to Database";
	}
	
	public boolean acceptOffer(String item_id) {
		if (!Item.acceptOffer(item_id)) return false;
		
		if (!Offer.updateOfferToAccepted(item_id)) return false;
		
		return true;
	}
	
	private String declineOfferValidation(String reason) {
		if (reason.isEmpty()) return "Reason must not be empty";
		
		return "Validation Success";
	}
	
	public String declineOffer(String item_id, String reason) {
		String message = declineOfferValidation(reason);
		
		if (!message.equals("Validation Success")) return message;
		
		if (Item.declineOffer(item_id)) return "Sucess";
		
		return "Error Delete to Database";
	}
	
	public boolean approveItem(String item_id, SceneManager sceneManager) {
        if (Item.approveItem(item_id)) sceneManager.switchPage("adminrequested");
        
        return false;
	}
	
	public boolean declineItem(String item_id, SceneManager sceneManager) {
        if (Item.declineItem(item_id)) sceneManager.switchPage("adminrequested");
        
        return false;
	}
	
	// Function viewAcceptedItem hanya mengembalikan semua item yang telah diaccept oleh admin dan tidak membutuhkan item_id
	public Vector<Item> viewAcceptedItem(String item_id) {
		Vector<Item> items = new Vector<>();
		ResultSet rs = Item.viewAcceptedItem(item_id);
		
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
		ResultSet rs = Item.viewSellerItem(user_id);
		
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
	
	public Vector<OfferItem> viewOfferItem(String user_id) {
		Vector<OfferItem> offers = new Vector<>();		
		ResultSet rs = Item.viewOfferItem(user_id);
		
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
                offers.add(new OfferItem(offerId, itemId, userId, itemName, itemCategory, itemSize, itemPrice, offerPrice));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } 
		
		return offers;
	}
}