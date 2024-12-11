package view.seller;

import controller.ItemController;
import controller.UserController;
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
import model.Item;
import model.Page;

public class EditItemPage extends Page{
	private SceneManager sceneManager;
	private ItemController itemController;
	private UserController userController;
	private Item selectedItem;
	
	private BorderPane layoutBP, navBP, editBP;
	
	private GridPane gp;
	private ScrollPane sp;
	
	private MenuBar navbar;
	private Menu menu;
	private MenuItem homeNav, uploadNav, itemNav, offerNav, logoutNav;
	
	private Label nameLbl, categoryLbl, sizeLbl, priceLbl, titleLbl, messageLbl;
	private TextField nameField, categoryField, sizeField, priceField;
	private Button updateBtn;
	
	public EditItemPage(Stage primaryStage, Item selectedItem) {
		this.sceneManager = new SceneManager(primaryStage);
		this.itemController = new ItemController();
		this.userController = new UserController();
		this.selectedItem = selectedItem;
		initPage();
		setAlignment();
		setHandler();
	}
	
	@Override
	public void initPage() {
		layoutBP = new BorderPane();
		navBP = new BorderPane();
		editBP = new BorderPane();
		
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
		
		titleLbl = new Label("Seller Edit Item");
		nameLbl = new Label("Item Name");
		categoryLbl = new Label("Item Category");
		sizeLbl = new Label("Item Size");
		priceLbl = new Label("Item Price");
		messageLbl = new Label("");
		
		nameField = new TextField(selectedItem.getItemName());
		categoryField = new TextField(selectedItem.getItemCategory());
		sizeField = new TextField(selectedItem.getItemSize());
		priceField = new TextField(selectedItem.getItemPrice());
		
		updateBtn = new Button("Save Changes");
	}

	@Override
	public void setAlignment() {
		navBP.setTop(navbar);
		navBP.setCenter(editBP);
		
		editBP.setCenter(titleLbl);
		
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
		
		gp.add(updateBtn, 0, 5, 2, 1);
	}

	@Override
	public void setHandler() {
		homeNav.setOnAction(e -> sceneManager.switchSellerPage("sellerhome"));
		uploadNav.setOnAction(e -> sceneManager.switchSellerPage("upload"));
		itemNav.setOnAction(e -> sceneManager.switchSellerPage("selleritem"));
		offerNav.setOnAction(e -> sceneManager.switchSellerPage("selleroffer"));
		logoutNav.setOnAction(e -> userController.logout(sceneManager));
		updateBtn.setOnAction(e -> handlePage(e));
	}

	@Override
	public void handlePage(ActionEvent e) {
		String itemName = nameField.getText();
		String itemCategory = categoryField.getText();
		String itemSize = sizeField.getText();
		String itemPrice = priceField.getText();
		
		String message = itemController.editItem(this.selectedItem.getItemId(), itemName, itemCategory, itemSize, itemPrice);
				
		if (message.equals("Success")) sceneManager.switchSellerPage("selleritem");
		else messageLbl.setText(message);
	}

	@Override
	public Scene createScene() {
		return new Scene(layoutBP);
	}

}
