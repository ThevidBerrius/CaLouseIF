package view.buyer;

import javafx.event.ActionEvent;
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

public class HistoryPage extends Page {
	private SceneManager sceneManager;

	private BorderPane layoutBP, navBP, historyBP;
	private GridPane gp;
	private ScrollPane sp;

	private MenuBar navbar;
	private Menu menu;
	private MenuItem homeNav, wishlistNav, historyNav;

	private Label titleLbl;

	private TableView<TransactionHistory> historyTable;

	public HistoryPage(Stage primaryStage) {
    	sceneManager = new SceneManager(primaryStage);
    	initPage();
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
		navbar.getMenus().add(menu);
		menu.getItems().addAll(homeNav, wishlistNav, historyNav);

		titleLbl = new Label("Buyer Transaction History");

		historyTable = new TableView<TransactionHistory>();
	}

	public void initalizeTable() {
		TableColumn<TransactionHistory, String> idCol = new TableColumn<TransactionHistory, String>("Transaction ID");
		idCol.setCellValueFactory(new PropertyValueFactory<>("transactionID"));

		TableColumn<TransactionHistory, String> nameCol = new TableColumn<TransactionHistory, String>("Name");
		nameCol.setCellValueFactory(new PropertyValueFactory<>("itemName"));

		TableColumn<TransactionHistory, String> categoryCol = new TableColumn<TransactionHistory, String>("Category");
		categoryCol.setCellValueFactory(new PropertyValueFactory<>("itemCategory"));

		TableColumn<TransactionHistory, String> sizeCol = new TableColumn<TransactionHistory, String>("Size");
		sizeCol.setCellValueFactory(new PropertyValueFactory<>("itemSize"));

		TableColumn<TransactionHistory, String> priceCol = new TableColumn<TransactionHistory, String>("Price");
		priceCol.setCellValueFactory(new PropertyValueFactory<>("itemPrice"));

		historyTable.getColumns().addAll(idCol, nameCol, categoryCol, sizeCol, priceCol);
	}

	@Override
	public void setAlignment() {
		navBP.setTop(navbar);
		navBP.setCenter(historyBP);
		
		historyBP.setCenter(titleLbl);
		
		layoutBP.setTop(navBP);
		layoutBP.setCenter(historyTable);
		
	}

	@Override
	public void setHandler() {
		homeNav.setOnAction(e->sceneManager.switchBuyerPage("buyerhome"));
    	historyNav.setOnAction(e->sceneManager.switchBuyerPage("history"));
    	wishlistNav.setOnAction(e->sceneManager.switchBuyerPage("wishlist"));
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
