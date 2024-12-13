package view.seller;

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
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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

public class ItemPage extends Page{
	private SceneManager sceneManager;
	private ItemController itemController;
	private UserController userController;
	private Vector<Item> items;
	
	private BorderPane layoutBP, navBP, itemBP;
	private GridPane gp;
	private ScrollPane sp;
	
	private MenuBar navbar;
	private Menu menu;
	private MenuItem homeNav, uploadNav, itemNav, offerNav, logoutNav;
	
	private Label tibleLbl;
	
	private TableView<Item> itemTable;
	

	public ItemPage(Stage primaryStage) {
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
		itemBP = new BorderPane();
		
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
		
		tibleLbl = new Label("Seller Item");
		
		itemTable = new TableView<Item>();
	}

	private void refreshTable() {
    	this.items.clear();
    	this.items = itemController.viewSellerItem(userController.getAuthUser().getUserId());
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
		
		TableColumn<Item, String> statusCol = new TableColumn<Item, String>("Status");
		statusCol.setCellValueFactory(new PropertyValueFactory<Item, String>("itemStatus"));
		
		TableColumn<Item, Void> actionCol = new TableColumn<Item, Void>("Action");
		actionCol.setCellFactory(param -> new TableCell<Item, Void>() {
            private final Button editBtn = new Button("Edit");
            private final Button deleteBtn = new Button("Delete");
            
            {
                editBtn.setOnAction(e -> {
                	Item selectedItem = getTableRow().getItem();
        			if (selectedItem != null) sceneManager.sellerEditItem(selectedItem);
                });
                
                deleteBtn.setOnAction(e -> {
                	Item selectedItem = getTableRow().getItem();
        			if (selectedItem != null) showDeleteConfirmationPopUp(selectedItem);;
        			refreshTable();
                });
            }
            
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                
                if (empty) {
                    setGraphic(null);
                } else {
                	HBox buttonBox = new HBox(10);
	                buttonBox.getChildren().addAll(editBtn, deleteBtn);
                	buttonBox.setStyle("-fx-alignment: center-left;");  
	                buttonBox.setSpacing(20);
                    setGraphic(buttonBox);
                }
            }
        });
		
		itemTable.getColumns().addAll(nameCol, categoryCol, sizeCol, priceCol, statusCol, actionCol);
	}

	@Override
	public void setAlignment() {
		navBP.setTop(navbar);
		navBP.setCenter(itemBP);
		
		itemBP.setCenter(tibleLbl);
		
		layoutBP.setTop(navBP);
		layoutBP.setCenter(itemTable);
	}

	@Override
	public void setHandler() {
		homeNav.setOnAction(e -> sceneManager.switchSellerPage("sellerhome"));
		uploadNav.setOnAction(e -> sceneManager.switchSellerPage("upload"));
		itemNav.setOnAction(e -> sceneManager.switchSellerPage("selleritem"));
		offerNav.setOnAction(e -> sceneManager.switchSellerPage("selleroffer"));
		logoutNav.setOnAction(e -> userController.logout(sceneManager));
	}
	
	public void showDeleteConfirmationPopUp(Item item) {
    	Stage popUpStage = new Stage();
    	popUpStage.setTitle("Confirm Delete Item");
    	popUpStage.initModality(Modality.APPLICATION_MODAL);
    	
    	Label messageLbl = new Label("Are you sure you want to delete " + item.getItemName() + "?");
    	
    	Button confirmBtn = new Button("Confirm");
    	Button cancelBtn = new Button("Cancel");
    	
    	confirmBtn.setOnAction(e -> {
    		// Masukin controller delete 
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

	@Override
	public Scene createScene() {
		return new Scene(layoutBP);
	}
}
