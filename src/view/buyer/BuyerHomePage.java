package view.buyer;

import java.util.Vector;

import controller.ItemController;
import controller.TransactionController;
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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.SceneManager;
import model.Item;
import model.Page;
import model.User;

public class BuyerHomePage extends Page {
    private SceneManager sceneManager;
    private ItemController itemController;
    private TransactionController transactionController;
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
    private TextField searchField;
    private Button searchBtn;

    private TableView<Item> itemTable;

    public BuyerHomePage(Stage primaryStage) {
        this.sceneManager = new SceneManager(primaryStage);
        this.itemController = new ItemController();
        this.transactionController = new TransactionController();
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

        itemTable = new TableView<Item>();
        
        searchField = new TextField();
        searchField.setPromptText("Search item");
        searchBtn = new Button("Search");
    }
    
    private void refreshTable() {
    	this.items.clear();
    	this.items = itemController.viewItemNotInWishlist(userController.getAuthUser().getUserId());
    	ObservableList<Item> itemList = FXCollections.observableArrayList(this.items);
    	this.itemTable.setItems(itemList);
    }

    public void initializeTable() {
        TableColumn<Item, String> nameCol = new TableColumn<Item, String>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<Item, String>("itemName"));
        nameCol.setPrefWidth(200);

        TableColumn<Item, String> categoryCol = new TableColumn<Item, String>("Category");
        categoryCol.setCellValueFactory(new PropertyValueFactory<Item, String>("itemCategory"));
        categoryCol.setPrefWidth(150);

        TableColumn<Item, String> sizeCol = new TableColumn<Item, String>("Size");
        sizeCol.setCellValueFactory(new PropertyValueFactory<Item, String>("itemSize"));
        sizeCol.setPrefWidth(150);

        TableColumn<Item, String> priceCol = new TableColumn<Item, String>("Price");
        priceCol.setCellValueFactory(new PropertyValueFactory<Item, String>("itemPrice"));
        priceCol.setPrefWidth(150);
        
        TableColumn<Item, Void> actionCol = new TableColumn<>("Action");
        actionCol.setPrefWidth(200);
        actionCol.setCellFactory(param -> new TableCell<Item, Void>() {
            private final Button buyBtn = new Button("Buy");
            private final Button offerBtn = new Button("Offer");
            private final Button wishlistBtn = new Button("Add Wishlist");
            
            {
                buyBtn.setOnAction(e -> {
                	Item item  = getTableRow().getItem();
                	if(item != null) showBuyConfirmationPopUp(item);
                });
                
                offerBtn.setOnAction(e -> {
                	Item item  = getTableRow().getItem();
                	if(item != null) showOfferPricePopUp(item);
                });
                
                wishlistBtn.setOnAction(e -> {
                	Item item = getTableRow().getItem();
                	wishlistController.addWishlist(item.getItemId(), userController.getAuthUser().getUserId());
                	refreshTable();
                });
            }
            
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                
                if (empty) {
                    setGraphic(null);
                } else {
                	HBox buttonBox = new HBox(20);
	                buttonBox.getChildren().addAll(buyBtn, offerBtn, wishlistBtn);
                	buttonBox.setStyle("-fx-alignment: center-left;");  
	                buttonBox.setSpacing(10);
                    setGraphic(buttonBox);
                }
            }
        });
        
        itemTable.getColumns().addAll(nameCol, categoryCol, sizeCol, priceCol, actionCol);
        
        double tableWidth = 200 + 150 + 150 + 150 + 200;
        itemTable.setPrefWidth(tableWidth);
        itemTable.setMaxWidth(tableWidth);
    }

    @Override
    public void setAlignment() {
        navBP.setTop(navbar);
        navBP.setCenter(buyerBP);

        buyerBP.setCenter(titleLbl);
        titleLbl.setStyle("-fx-font-size: 16px; -fx-padding: 10;");
        
        HBox searchBox = new HBox(10, searchField, searchBtn);
        searchBox.setStyle("-fx-padding: 10; -fx-alignment: center;");

        VBox mainLayout = new VBox(10, searchBox, itemTable);
        mainLayout.setStyle("-fx-padding: 10;");
        
        layoutBP.setTop(navBP);
        layoutBP.setCenter(mainLayout);
    }

    @Override
    public void setHandler() {
    	homeNav.setOnAction(e -> sceneManager.switchBuyerPage("buyerhome"));
    	historyNav.setOnAction(e -> sceneManager.switchBuyerPage("history"));
    	wishlistNav.setOnAction(e -> sceneManager.switchBuyerPage("wishlist"));
		logoutNav.setOnAction(e -> userController.logout(sceneManager));
		searchBtn.setOnAction(e -> {
			String search = searchField.getText();
			
			this.items.clear();
	    	this.items = itemController.browseItem(search, this.userController.getAuthUser().getUserId());
	    	ObservableList<Item> itemList = FXCollections.observableArrayList(this.items);
	    	this.itemTable.setItems(itemList);
		});
    }
    
    public void showBuyConfirmationPopUp(Item item) {
    	Stage popUpStage = new Stage();
    	popUpStage.setTitle("Confirm Purchase");
    	popUpStage.initModality(Modality.APPLICATION_MODAL);
    	
    	Label messageLbl = new Label("Are you sure you want to buy " + item.getItemName() + "?");
    	
    	Button confirmBtn = new Button("Confirm");
    	Button cancelBtn = new Button("Cancel");
    	
    	confirmBtn.setOnAction(e -> {
    		transactionController.purchaseItems(userController.getAuthUser().getUserId(), item.getItemId());
            popUpStage.close();
            refreshTable();
        });
    	
    	cancelBtn.setOnAction(e -> popUpStage.close());
    	
    	HBox buttonBox = new HBox(10, confirmBtn, cancelBtn);
        buttonBox.setStyle("-fx-alignment: center;");
        VBox popUpLayout = new VBox(10, messageLbl, buttonBox);
        popUpLayout.setStyle("-fx-padding: 15; -fx-alignment: center;");
        
        Scene popUpScene = new Scene(popUpLayout, 300, 150);
        popUpStage.setScene(popUpScene);
        popUpStage.showAndWait();
    }
    
    public void showOfferPricePopUp(Item item) {
        Stage popUpStage = new Stage();
        popUpStage.setTitle("Make Offer");
        popUpStage.initModality(Modality.APPLICATION_MODAL);
        
        int highest_offer = itemController.getHighestOffer(item.getItemId());

        Label messageLbl = new Label("Enter your offer price for " + item.getItemName() + ", current offer: " + highest_offer);
        TextField offerInput = new TextField();
        offerInput.setPromptText("Enter offer price...");

        Button submitBtn = new Button("Submit");
        Button cancelBtn = new Button("Cancel");

        submitBtn.setOnAction(e -> {
            String offerPrice = offerInput.getText().trim();
            String message = itemController.offerPrice(item.getItemId(), userController.getAuthUser().getUserId(), offerPrice, highest_offer);
            
            if (message.equals("Success")) popUpStage.close();
            else {
            	messageLbl.setText(message);
                messageLbl.setStyle("-fx-text-fill: red;");
            }
        });

        cancelBtn.setOnAction(e -> popUpStage.close());

        HBox buttonBox = new HBox(10, submitBtn, cancelBtn);
        buttonBox.setStyle("-fx-alignment: center;");
        VBox popUpLayout = new VBox(10, messageLbl, offerInput, buttonBox);
        popUpLayout.setStyle("-fx-padding: 15; -fx-alignment: center;");

        Scene popUpScene = new Scene(popUpLayout, 450, 200);
        popUpStage.setScene(popUpScene);
        popUpStage.showAndWait();
    }

    @Override
    public Scene createScene() {
        return new Scene(layoutBP); 
    }
}
