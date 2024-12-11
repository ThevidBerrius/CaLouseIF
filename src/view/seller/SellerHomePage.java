package view.seller;

import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import main.SceneManager;
import model.Item;
import model.Page;

public class SellerHomePage extends Page{
	
	private SceneManager sceneManager;
	
	private BorderPane layoutBP, navBP, sellerBP;
	private GridPane gp;
	private ScrollPane sp;
	
	private MenuBar navbar;
	private Menu menu;
	private MenuItem homeNav, uploadNav, itemNav, offerNav;
	
	private Label titleLbl;
	private TableView<Item> itemTable;
	
	public SellerHomePage(Stage primaryStage) {
		sceneManager = new SceneManager(primaryStage);
		initPage();
		initializeTable();
		setAlignment();
		setHandler();
	}
	
	@Override
	public void initPage() {
		layoutBP = new BorderPane();
		navBP = new BorderPane();
		sellerBP = new BorderPane();
		
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
		
		titleLbl = new Label("Seller Home");
		
		itemTable = new TableView<Item>();
		
	}
	
	public void initializeTable() {
		TableColumn<Item, String> nameCol = new TableColumn<Item, String>("Name");
		nameCol.setCellValueFactory(new PropertyValueFactory<Item, String>("itemName"));
		
		TableColumn<Item, String> categoryCol = new TableColumn<Item, String>("Category");
		categoryCol.setCellValueFactory(new PropertyValueFactory<Item, String>("itemCategory"));
		
		TableColumn<Item, String> sizeCol = new TableColumn<Item, String>("Size");
		sizeCol.setCellValueFactory(new PropertyValueFactory<Item, String>("itemSize"));
		
		TableColumn<Item, String> priceCol = new TableColumn<Item, String>("Price");
		priceCol.setCellValueFactory(new PropertyValueFactory<Item, String>("itemPrice"));
		
		itemTable.getColumns().addAll(nameCol, categoryCol, sizeCol, priceCol);
		
	}

	@Override
	public void setAlignment() {
		navBP.setTop(navbar);
		navBP.setCenter(sellerBP);
		
		sellerBP.setCenter(titleLbl);
		
		layoutBP.setTop(navBP);
		layoutBP.setCenter(itemTable);
		
		
	}

	@Override
	public void setHandler() {
		homeNav.setOnAction(e->sceneManager.switchSellerPage("sellerhome"));
		uploadNav.setOnAction(e->sceneManager.switchSellerPage("upload"));
		itemNav.setOnAction(e->sceneManager.switchSellerPage("selleritem"));
		offerNav.setOnAction(e->sceneManager.switchSellerPage(""));
	}

	@Override
	public void handlePage(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Scene createScene() {
		// TODO Auto-generated method stub
		return new Scene(layoutBP);
	}

}
