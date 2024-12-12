package view.buyer;

import java.util.Vector;

import controller.ItemController;
import controller.UserController;
import controller.WishlistController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
import model.Item;
import model.Page;
import model.User;

public class BuyerHomePage extends Page {
    private SceneManager sceneManager;
    private ItemController itemController;
    private UserController userController;
    private WishlistController wishlistController;
    private Vector<Item> items;

    private BorderPane layoutBP, navBP, buyerBP;

    private GridPane gp;
    private ScrollPane sp;

    private MenuBar navbar;
    private Menu menu;
    private MenuItem homeNav, wishlistNav, historyNav, logoutNav;

    private Label titleLbl;

    private Button offerBtn, wishlistBtn, buyBtn;

    private TableView<Item> itemTable;

    public BuyerHomePage(Stage primaryStage) {
        this.sceneManager = new SceneManager(primaryStage);
        this.itemController = new ItemController();
        this.userController = UserController.getInstance();
        this.wishlistController = new WishlistController();
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
        buyerBP = new BorderPane();

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

        titleLbl = new Label("Buyer Home");

//        offerBtn = new Button("Offer Item");
//        wishlistBtn = new Button("Add Wishlist");
//        buyBtn = new Button("Buy");

        itemTable = new TableView<Item>();
    }
    
    private void refreshTable() {
    	this.items.clear();
    	this.items = itemController.viewItem();
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
        
        TableColumn<Item, Void> actionCol = new TableColumn<>("Action");
        actionCol.setCellFactory(param -> new TableCell<Item, Void>() {
            private final Button buyBtn = new Button("Buy");
            private final Button offerBtn = new Button("Offer");
            private final Button wishlistBtn = new Button("Add Wishlist");
            
            {
                buyBtn.setOnAction(e -> {
                    System.out.println("Buy Item");
                });
                
                offerBtn.setOnAction(e -> {
                    System.out.println("Offer Item");
                });
                
                wishlistBtn.setOnAction(e -> {
                	Item item = getTableRow().getItem();
                	wishlistController.addWishlist(item.getItemId(), userController.getAuthUser().getUserId());
                });
            }
            
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                
                if (empty) {
                    setGraphic(null);
                } else {
                	HBox buttonBox = new HBox(10);
	                buttonBox.getChildren().addAll(buyBtn, offerBtn, wishlistBtn);
                	buttonBox.setStyle("-fx-alignment: center-left;");  
	                buttonBox.setSpacing(20);
                    setGraphic(buttonBox);
                }
            }
        });
        
        itemTable.getColumns().addAll(nameCol, categoryCol, sizeCol, priceCol, actionCol);
    }

    @Override
    public void setAlignment() {
        navBP.setTop(navbar);
        navBP.setCenter(buyerBP);

        buyerBP.setCenter(titleLbl);

        layoutBP.setTop(navBP);
        layoutBP.setCenter(itemTable);

//        GridPane buttonPane = new GridPane();
//        buttonPane.setHgap(10); 
//        buttonPane.setVgap(10);
//        buttonPane.add(offerBtn, 0, 0);
//        buttonPane.add(wishlistBtn, 1, 0);
//        buttonPane.add(buyBtn, 2, 0);

//        layoutBP.setBottom(buttonPane);

//        buttonPane.setStyle("-fx-alignment: center; -fx-padding: 10;");
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
        // TODO Auto-generated method stub
    }

    @Override
    public Scene createScene() {
        return new Scene(layoutBP); 
    }
}
