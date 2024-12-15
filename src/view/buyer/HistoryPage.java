package view.buyer;

import java.util.Vector;

import controller.TransactionController;
import controller.UserController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
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
import model.Page;
import model.TransactionHistory;
import model.WishlistItem;

public class HistoryPage extends Page {
	private SceneManager sceneManager;
	private TransactionController transactionController;
	private UserController userController;
	private Vector<TransactionHistory> transactionHistory;

	private BorderPane layoutBP, navBP, historyBP;
	private GridPane gp;
	private ScrollPane sp;

	private MenuBar navbar;
	private Menu menu;
	private MenuItem homeNav, wishlistNav, historyNav, logoutNav;

	private Label titleLbl;

	private TableView<TransactionHistory> historyTable;

	public HistoryPage(Stage primaryStage) {
    	this.sceneManager = new SceneManager(primaryStage);
    	this.transactionController = new TransactionController();
    	this.userController = UserController.getInstance();
    	this.transactionHistory = new Vector<>();
    	initPage();
    	refreshTable();
    	initalizeTable();
    	setAlignment();
    	setHandler();
	}

	@Override
	public void initPage() {
		layoutBP = new BorderPane();
		navBP = new BorderPane();
		historyBP = new BorderPane();

		gp = new GridPane();
		sp = new ScrollPane();

		navbar = new MenuBar();
		menu = new Menu("Menu");
		homeNav = new MenuItem("Home");
		wishlistNav = new MenuItem("Wishlist");
		historyNav = new MenuItem("History");
		logoutNav = new MenuItem("Logout");
		navbar.getMenus().add(menu);
		menu.getItems().addAll(homeNav, wishlistNav, historyNav, logoutNav);

		titleLbl = new Label("Buyer Transaction History");

		historyTable = new TableView<TransactionHistory>();
	}
	
	private void refreshTable() {
		this.transactionHistory.clear();
		this.transactionHistory = transactionController.viewHistory(userController.getAuthUser().getUserId());
		ObservableList<TransactionHistory> transactionHistoryList = FXCollections.observableArrayList(this.transactionHistory);
		this.historyTable.setItems(transactionHistoryList);
	}

	public void initalizeTable() {
		TableColumn<TransactionHistory, String> idCol = new TableColumn<TransactionHistory, String>("Transaction ID");
		idCol.setCellValueFactory(new PropertyValueFactory<>("transactionId"));
		idCol.setPrefWidth(150);

		TableColumn<TransactionHistory, String> nameCol = new TableColumn<TransactionHistory, String>("Name");
		nameCol.setCellValueFactory(new PropertyValueFactory<>("itemName"));
		nameCol.setPrefWidth(200);

		TableColumn<TransactionHistory, String> categoryCol = new TableColumn<TransactionHistory, String>("Category");
		categoryCol.setCellValueFactory(new PropertyValueFactory<>("itemCategory"));
		categoryCol.setPrefWidth(150);


		TableColumn<TransactionHistory, String> sizeCol = new TableColumn<TransactionHistory, String>("Size");
		sizeCol.setCellValueFactory(new PropertyValueFactory<>("itemSize"));
		sizeCol.setPrefWidth(150);

		TableColumn<TransactionHistory, String> priceCol = new TableColumn<TransactionHistory, String>("Price");
		priceCol.setCellValueFactory(new PropertyValueFactory<>("itemPrice"));
		priceCol.setPrefWidth(150);

		historyTable.getColumns().addAll(idCol, nameCol, categoryCol, sizeCol, priceCol);
		
		double tableWidth = 150 + 200 + 150 + 150 + 150;
	    historyTable.setPrefWidth(tableWidth);
	    historyTable.setMaxWidth(tableWidth);
	}

	@Override
	public void setAlignment() {
		navBP.setTop(navbar);
		navBP.setCenter(historyBP);
		
		historyBP.setCenter(titleLbl);
	    titleLbl.setStyle("-fx-font-size: 16px; -fx-padding: 10;");
		
		layoutBP.setTop(navBP);
		layoutBP.setCenter(historyTable);
	}

	@Override
	public void setHandler() {
		homeNav.setOnAction(e -> sceneManager.switchBuyerPage("buyerhome"));
    	historyNav.setOnAction(e -> sceneManager.switchBuyerPage("history"));
    	wishlistNav.setOnAction(e -> sceneManager.switchBuyerPage("wishlist"));
		logoutNav.setOnAction(e -> userController.logout(sceneManager));
	}

	@Override
	public Scene createScene() {
		return new Scene(layoutBP);
	}
}
