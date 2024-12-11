package main;

import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Item;
import view.admin.AdminHomePage;
import view.admin.RequestedPage;
import view.buyer.BuyerHomePage;
import view.buyer.HistoryPage;
import view.buyer.WishlistPage;
import view.guest.LoginPage;
import view.guest.RegisterPage;
import view.seller.EditItemPage;
import view.seller.ItemPage;
import view.seller.SellerHomePage;
import view.seller.SellerOffer;
import view.seller.UploadItemPage;

public class SceneManager {
	private Stage primaryStage;

	public SceneManager(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}

	private void setScene(Scene scene) {
		primaryStage.setScene(scene);
	}

	public void switchPage(String pageName) {
		switch (pageName.toLowerCase()) {
		case "register":
			RegisterPage register = new RegisterPage(primaryStage);
			setScene(register.createScene());
			break;
		case "login":
			LoginPage login = new LoginPage(primaryStage);
			setScene(login.createScene());
			break;
		case "adminhome":
			AdminHomePage adminHome = new AdminHomePage(primaryStage);
			setScene(adminHome.createScene());
			break;
		case "adminrequested":
			RequestedPage requestPage = new RequestedPage(primaryStage);
			setScene(requestPage.createScene());
			break;
		default:
			throw new IllegalArgumentException("Page not found: " + pageName);
		}
	}

	public void switchBuyerPage(String pageName) {
		switch (pageName.toLowerCase()) {
		case "buyerhome":
			BuyerHomePage buyerHome = new BuyerHomePage(primaryStage);
			setScene(buyerHome.createScene());
			break;
		case "history":
			HistoryPage history = new HistoryPage(primaryStage);
			setScene(history.createScene());
			break;
		case "wishlist":
			WishlistPage wishlist = new WishlistPage(primaryStage);
			setScene(wishlist.createScene());
			break;
		default:
			throw new IllegalArgumentException("Page not found: " + pageName);
		}
	}

	public void switchSellerPage(String pageName) {
		switch (pageName.toLowerCase()) {
		case "sellerhome":
			SellerHomePage sellerHome = new SellerHomePage(primaryStage);
			setScene(sellerHome.createScene());
			break;
		case "selleritem":
			ItemPage sellerItem = new ItemPage(primaryStage);
			setScene(sellerItem.createScene());
			break;
		case "upload":
			UploadItemPage uploadItem = new UploadItemPage(primaryStage);
			setScene(uploadItem.createScene());
			break;
		case "edititem":
			EditItemPage editItem = new EditItemPage(primaryStage);
			setScene(editItem.createScene());
			break;
		case "selleroffer":
			SellerOffer offer = new SellerOffer(primaryStage);
			setScene(offer.createScene());
			break;
		default:
			throw new IllegalArgumentException("Page not found: " + pageName);
		}
	}
}