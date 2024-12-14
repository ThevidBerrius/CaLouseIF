package view.admin;

import java.util.Vector;

import controller.ItemController;
import controller.UserController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.SceneManager;
import model.Item;
import model.Page;

public class AdminHomePage extends Page {
	private SceneManager sceneManager;
	private ItemController itemController;
	private UserController userController;
    private Vector<Item> items;
	
	private BorderPane layoutBP, navBP, adminBP;
	private GridPane gp;
	private ScrollPane sp;
	
	private MenuBar navbar;
	private Menu menu;
	private MenuItem homeNav, requestedNav, logoutNav;
	
	private Label titleLbl;
	
	private TableView<Item> itemTable;
	private TextField searchField;
    private Button searchBtn;

	public AdminHomePage(Stage primaryStage) {
		this.sceneManager = new SceneManager(primaryStage);
		this.itemController = new ItemController();
		this.userController = UserController.getInstance();
		this.items = new Vector<>();
		initPage();
		refreshTable();
		initializeTable();
		setAlignment();
		setHandler();
	}

	@Override
	public void initPage() {
		layoutBP = new BorderPane();
		navBP = new BorderPane();
		adminBP = new BorderPane();
		
		gp = new GridPane();
		sp = new ScrollPane();
		
		navbar = new MenuBar();
		menu  = new Menu("Menu");
		homeNav = new MenuItem("Home");
		requestedNav = new MenuItem("Requested Item");
		logoutNav = new MenuItem("Logout");
		
		navbar.getMenus().add(menu);
		menu.getItems().addAll(homeNav, requestedNav, logoutNav);
		
		titleLbl = new Label("Admin Home");
		
		searchField = new TextField();
        searchField.setPromptText("Search item");
        searchBtn = new Button("Search");
		
		itemTable = new TableView<Item>();
	}
	
	private void refreshTable() {
    	this.items.clear();
    	this.items = itemController.viewAcceptedItem(null);
    	ObservableList<Item> itemList = FXCollections.observableArrayList(this.items);
    	this.itemTable.setItems(itemList);
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
		navBP.setCenter(adminBP);
		
		adminBP.setCenter(titleLbl);
		
		HBox searchBox = new HBox(10, searchField, searchBtn);
        searchBox.setStyle("-fx-padding: 10; -fx-alignment: center;");

        VBox mainLayout = new VBox(10, searchBox, itemTable);
        mainLayout.setStyle("-fx-padding: 10;");
		
		layoutBP.setTop(navBP);
		layoutBP.setCenter(mainLayout);
	}

	@Override
	public void setHandler() {
		homeNav.setOnAction(e -> sceneManager.switchPage("adminhome"));
		requestedNav.setOnAction(e -> sceneManager.switchPage("adminrequested"));
		logoutNav.setOnAction(e -> userController.logout(sceneManager));
	}

	@Override
	public Scene createScene() {
		return new Scene(layoutBP);
	}
}
