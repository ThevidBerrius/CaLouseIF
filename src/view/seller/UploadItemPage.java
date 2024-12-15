package view.seller;

import controller.ItemController;
import controller.UserController;
import javafx.event.ActionEvent;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import main.SceneManager;
import model.Page;

public class UploadItemPage extends Page{
	private SceneManager sceneManager;
	private ItemController itemController;
	private UserController userController;
	
	private BorderPane layoutBP, navBP, uploadBP;
	
	private GridPane gp;
	private ScrollPane sp;
	
	private MenuBar navbar;
	private Menu menu;
	private MenuItem homeNav, uploadNav, itemNav, offerNav, logoutNav;
	
	private Label nameLbl, categoryLbl, sizeLbl, priceLbl, titleLbl, messageLbl;
	private TextField nameField, categoryField, sizeField, priceField;
	private Button uploadBtn;
	
	public UploadItemPage(Stage primaryStage) {
		this.sceneManager = new SceneManager(primaryStage);
		this.itemController = new ItemController();
		this.userController = UserController.getInstance();
		initPage();
		setAlignment();
		setHandler();
	}
	
	@Override
	public void initPage() {
		layoutBP = new BorderPane();
		navBP = new BorderPane();
		uploadBP = new BorderPane();
		
		gp = new GridPane();
		sp = new ScrollPane();
		
		navbar = new MenuBar();
		menu = new Menu("Menu");
		homeNav = new MenuItem("Home");
		uploadNav = new MenuItem("Upload Item");
		itemNav = new MenuItem("Item");
		offerNav = new MenuItem("Offer");
		logoutNav = new MenuItem("Logout");
		navbar.getMenus().add(menu);
		menu.getItems().addAll(homeNav, uploadNav, itemNav, offerNav, logoutNav);
		
		titleLbl = new Label("Seller Upload Item");
	    titleLbl.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #333;");
	    GridPane.setHalignment(titleLbl, HPos.CENTER);

		nameLbl = new Label("Item Name");
		categoryLbl = new Label("Item Category");
		sizeLbl = new Label("Item Size");
		priceLbl = new Label("Item Price");
		
		messageLbl = new Label("");
	    messageLbl.setStyle("-fx-text-fill: red;");

		nameField = new TextField();
		categoryField = new TextField();
		sizeField = new TextField();
		priceField = new TextField();
		
		uploadBtn = new Button("Upload");
	    uploadBtn.setStyle("-fx-background-color: #28a745; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 8px 16px;");
	    GridPane.setHalignment(uploadBtn, HPos.CENTER);
	}

	@Override
	public void setAlignment() {
		navBP.setTop(navbar);
		navBP.setCenter(uploadBP);
		
		layoutBP.setTop(navBP);
		layoutBP.setCenter(sp);
		
		sp.setContent(gp);
		sp.setFitToWidth(true);
	    sp.setPadding(new Insets(20, 50, 20, 50)); 
	    gp.setVgap(15);
	    gp.setHgap(10);
	    gp.setPadding(new Insets(30, 50, 30, 50));
		
	    gp.add(titleLbl, 0, 0, 2, 1);

	    gp.add(nameLbl, 0, 1);
	    gp.add(nameField, 1, 1);
	    nameField.setPrefWidth(300);

	    gp.add(categoryLbl, 0, 2);
	    gp.add(categoryField, 1, 2);
	    categoryField.setPrefWidth(300);

	    gp.add(sizeLbl, 0, 3);
	    gp.add(sizeField, 1, 3);
	    sizeField.setPrefWidth(300);

		gp.add(priceLbl, 0, 4);
		gp.add(priceField, 1, 4);
	    priceField.setPrefWidth(300);

		gp.add(messageLbl, 1, 5);
		
		gp.add(uploadBtn, 0, 6, 2, 1);
	}

	@Override
	public void setHandler() {
		homeNav.setOnAction(e -> sceneManager.switchSellerPage("sellerhome"));
		uploadNav.setOnAction(e -> sceneManager.switchSellerPage("upload"));
		itemNav.setOnAction(e -> sceneManager.switchSellerPage("selleritem"));
		offerNav.setOnAction(e -> sceneManager.switchSellerPage("selleroffer"));
		logoutNav.setOnAction(e -> userController.logout(sceneManager));
		uploadBtn.setOnAction(e -> {
			String itemName = nameField.getText();
			String itemCategory = categoryField.getText();
			String itemSize = sizeField.getText();
			String itemPrice = priceField.getText();
			
			String message = itemController.uploadItem(itemName, userController.getAuthUser().getUserId(), itemCategory, itemSize, itemPrice);
			
			if (message.equals("Success")) sceneManager.switchSellerPage("selleritem");
			else messageLbl.setText(message);
		});
	}

	@Override
	public Scene createScene() {
		return new Scene(layoutBP);
	}
}
