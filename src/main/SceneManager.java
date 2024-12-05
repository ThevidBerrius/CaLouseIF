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
            case "admin":
            	RequestedPage seller = new RequestedPage(primaryStage);
            	setScene(seller.createScene());
            	break;
            default:
                throw new IllegalArgumentException("Page not found: " + pageName);
        }
    }
}
