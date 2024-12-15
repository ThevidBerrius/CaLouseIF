package view.buyer;

import java.util.Vector;

import controller.UserController;
import controller.WishlistController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import main.SceneManager;
import model.Page;
import model.WishlistItem;

public class WishlistPage extends Page {
	private SceneManager sceneManager;
	private UserController userController;
	private WishlistController wishlistController;
	private Vector<WishlistItem> wishlistItems;

	private BorderPane layoutBP, navBP, wishlistBP;
	private GridPane gp;
	private ScrollPane sp;

	private MenuBar navbar;
	private Menu menu;
	private MenuItem homeNav, wishlistNav, historyNav, logoutNav;

	private TableView<WishlistItem> wishlistTable;

	private Label titleLbl;

	public WishlistPage(Stage primaryStage) {
		this.sceneManager = new SceneManager(primaryStage);
		this.userController = UserController.getInstance();
		this.wishlistController = new WishlistController();
		this.wishlistItems = new Vector<>();
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
		wishlistBP = new BorderPane();

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

		titleLbl = new Label("Buyer Wishlist");

		wishlistTable = new TableView<WishlistItem>();
	}

	private void refreshTable() {
		this.wishlistItems.clear();
		this.wishlistItems = wishlistController.viewWishlist("", userController.getAuthUser().getUserId());
		ObservableList<WishlistItem> wishlistItemList = FXCollections.observableArrayList(this.wishlistItems);
		this.wishlistTable.setItems(wishlistItemList);
	}

	public void initializeTable() {
		TableColumn<WishlistItem, String> nameCol = new TableColumn<WishlistItem, String>("Name");
		nameCol.setCellValueFactory(new PropertyValueFactory<WishlistItem, String>("itemName"));
		nameCol.setPrefWidth(200);

		TableColumn<WishlistItem, String> categoryCol = new TableColumn<WishlistItem, String>("Category");
		categoryCol.setCellValueFactory(new PropertyValueFactory<WishlistItem, String>("itemCategory"));
		categoryCol.setPrefWidth(150);

		TableColumn<WishlistItem, String> sizeCol = new TableColumn<WishlistItem, String>("Size");
		sizeCol.setCellValueFactory(new PropertyValueFactory<WishlistItem, String>("itemSize"));
		sizeCol.setPrefWidth(150);

		TableColumn<WishlistItem, String> priceCol = new TableColumn<WishlistItem, String>("Price");
		priceCol.setCellValueFactory(new PropertyValueFactory<WishlistItem, String>("itemPrice"));
		priceCol.setPrefWidth(150);

		TableColumn<WishlistItem, Void> actionCol = new TableColumn<>("Action");
		actionCol.setPrefWidth(100);
		actionCol.setCellFactory(param -> new TableCell<>() {
		    private final Button removeBtn = new Button("Remove");
		    
		    {
		        removeBtn.setOnAction(e -> {
		        	WishlistItem wishlist = getTableRow().getItem();
		            wishlistController.removeWishlist(wishlist.getWishlistId());
		            refreshTable();
		        });
		    }

		    @Override
		    protected void updateItem(Void item, boolean empty) {
		        super.updateItem(item, empty);

		        if (empty || getTableRow() == null || getTableRow().getItem() == null) {
		            setGraphic(null);
		        } else {
		            HBox buttonBox = new HBox(removeBtn);
		            buttonBox.setStyle("-fx-alignment: center;");
		            setGraphic(buttonBox);
		        }
		    }
		});

		wishlistTable.getColumns().addAll(nameCol, categoryCol, sizeCol, priceCol, actionCol);
		
		double tableWidth = 200 + 150 + 150 + 150 + 100;
	    wishlistTable.setPrefWidth(tableWidth);
	    wishlistTable.setMaxWidth(tableWidth);
	}

	@Override
	public void setAlignment() {
		navBP.setTop(navbar);
		navBP.setCenter(wishlistBP);

		wishlistBP.setCenter(titleLbl);
	    titleLbl.setStyle("-fx-font-size: 16px; -fx-padding: 10;");

		layoutBP.setTop(navBP);
		layoutBP.setCenter(wishlistTable);
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
