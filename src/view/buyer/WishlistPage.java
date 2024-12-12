package view.buyer;

import controller.UserController;
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
import model.Page;
import model.WishlistItem;

public class WishlistPage extends Page {
    private SceneManager sceneManager;
    private UserController userController;

    private BorderPane layoutBP, navBP, wishlistBP;
    private GridPane gp;
    private ScrollPane sp;

    private MenuBar navbar;
    private Menu menu;
    private MenuItem homeNav, wishlistNav, historyNav, logoutNav;

    private TableView<WishlistItem> wishlistTable;

    private Label titleLbl;
    private Button removeBtn;

    public WishlistPage(Stage primaryStage) {
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
        menu.getItems().addAll(homeNav, wishlistNav, historyNav);

        titleLbl = new Label("Buyer Wishlist");
        removeBtn = new Button("Remove Item");

        wishlistTable = new TableView<WishlistItem>();
    }

    public void initializeTable() {
        TableColumn<WishlistItem, String> nameCol = new TableColumn<WishlistItem, String>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<WishlistItem, String>("itemName"));

        TableColumn<WishlistItem, String> categoryCol = new TableColumn<WishlistItem, String>("Category");
        categoryCol.setCellValueFactory(new PropertyValueFactory<WishlistItem, String>("itemCategory"));

        TableColumn<WishlistItem, String> sizeCol = new TableColumn<WishlistItem, String>("Size");
        sizeCol.setCellValueFactory(new PropertyValueFactory<WishlistItem, String>("itemSize"));

        TableColumn<WishlistItem, String> priceCol = new TableColumn<WishlistItem, String>("Price");
        priceCol.setCellValueFactory(new PropertyValueFactory<WishlistItem, String>("itemPrice"));

        wishlistTable.getColumns().addAll(nameCol, categoryCol, sizeCol, priceCol);
    }

    @Override
    public void setAlignment() {
        navBP.setTop(navbar);
        navBP.setCenter(wishlistBP);

        wishlistBP.setCenter(titleLbl);

        layoutBP.setTop(navBP);
        layoutBP.setCenter(wishlistTable);

        GridPane buttonPane = new GridPane();
        buttonPane.setHgap(10); 
        buttonPane.add(removeBtn, 0, 0); 

        layoutBP.setBottom(buttonPane);

        buttonPane.setStyle("-fx-alignment: center; -fx-padding: 10;");
    }

    @Override
    public void setHandler() {
    	homeNav.setOnAction(e->sceneManager.switchBuyerPage("buyerhome"));
    	historyNav.setOnAction(e->sceneManager.switchBuyerPage("history"));
    	wishlistNav.setOnAction(e->sceneManager.switchBuyerPage("wishlist"));
		logoutNav.setOnAction(e -> userController.logout(sceneManager));
    }

    @Override
    public void handlePage(ActionEvent e) {
        // TODO: Handle page events
    }

    @Override
    public Scene createScene() {
        // Create and return the Scene
        return new Scene(layoutBP); 
    }
}
