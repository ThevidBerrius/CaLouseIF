package view.seller;

import controller.ItemController;
import javafx.event.ActionEvent;
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
	
	private BorderPane layoutBP, navBP, uploadBP;
	
	private GridPane gp;
	private ScrollPane sp;
	
	private MenuBar navbar;
	private Menu menu;
	private MenuItem homeNav, uploadNav, itemNav, offerNav;
	
	private Label nameLbl, categoryLbl, sizeLbl, priceLbl, titleLbl, messageLbl;
	private TextField nameField, categoryField, sizeField, priceField;
	private Button uploadBtn;
	
	public UploadItemPage(Stage primaryStage) {
		sceneManager = new SceneManager(primaryStage);
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
		navbar.getMenus().add(menu);
		menu.getItems().addAll(homeNav, uploadNav, itemNav, offerNav);
		
		titleLbl = new Label("Seller Upload Item");
		nameLbl = new Label("Item Name");
		categoryLbl = new Label("Item Category");
		sizeLbl = new Label("Item Size");
		priceLbl = new Label("Item Price");
		messageLbl = new Label("");
		
		nameField = new TextField();
		categoryField = new TextField();
		sizeField = new TextField();
		priceField = new TextField();
		
		uploadBtn = new Button("Upload");
	}

	@Override
	public void setAlignment() {
		navBP.setTop(navbar);
		navBP.setCenter(uploadBP);
		
		uploadBP.setCenter(titleLbl);
		
		layoutBP.setTop(navBP);
		layoutBP.setCenter(sp);
		
		sp.setContent(gp);
		
		gp.add(nameLbl, 0, 0);
		gp.add(nameField, 1, 0);
		gp.add(categoryLbl, 0, 1);
		gp.add(categoryField, 1, 1);
		gp.add(sizeLbl, 0, 2);
		gp.add(sizeField, 1, 2);
		gp.add(priceLbl, 0, 3);
		gp.add(priceField, 1, 3);
		gp.add(messageLbl, 1, 4);
		
		gp.add(uploadBtn, 0, 5, 2, 1);
	}

	@Override
	public void setHandler() {
		homeNav.setOnAction(e -> sceneManager.switchSellerPage("sellerhome"));
		uploadNav.setOnAction(e -> sceneManager.switchSellerPage("upload"));
		itemNav.setOnAction(e -> sceneManager.switchSellerPage("selleritem"));
		offerNav.setOnAction(e -> sceneManager.switchSellerPage("selleroffer"));
		uploadBtn.setOnAction(e -> handlePage(e));
	}

	@Override
	public void handlePage(ActionEvent e) {
		String itemName = nameField.getText();
		String itemCategory = categoryField.getText();
		String itemSize = sizeField.getText();
		String itemPrice = priceField.getText();
		ItemController itemController = new ItemController();
		
		String message = itemController.uploadItem(itemName, itemCategory, itemSize, itemPrice);
		
		if (message.equals("Success")) sceneManager.switchSellerPage("sellerhome");
		else messageLbl.setText(message);
	}

	@Override
	public Scene createScene() {
		// TODO Auto-generated method stub
		return new Scene(layoutBP);
	}

}
