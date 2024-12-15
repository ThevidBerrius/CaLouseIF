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
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.SceneManager;
import model.Item;
import model.OfferItem;
import model.Page;

public class SellerOffer extends Page {
	private SceneManager sceneManager;
	private ItemController itemController;
	private UserController userController;
	private Vector<OfferItem> offers;

	private BorderPane layoutBP, navBP, offerBP, actionBP;

	private GridPane gp;
	private ScrollPane sp;

	private MenuBar navbar;
	private Menu menu;
	private MenuItem homeNav, uploadNav, itemNav, offerNav, logoutNav;

	private TableView<OfferItem> offerTable;

	private Button approveBtn, declineBtn;
	private TextArea reasonArea;
	private Label reasonLbl, titleLbl;
	private VBox actionBox;

	public SellerOffer(Stage primaryStage) {
		this.sceneManager = new SceneManager(primaryStage);
		this.itemController = new ItemController();
		this.userController = UserController.getInstance();
		this.offers = new Vector<>();
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
		offerBP = new BorderPane();
		actionBP = new BorderPane();

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

		offerTable = new TableView<>();

		reasonArea = new TextArea();
		reasonLbl = new Label("Reason for Declining:");
		titleLbl = new Label("Seller Offers");
	    titleLbl.setStyle("-fx-font-size: 16px; -fx-padding: 10;");

		actionBox = new VBox(10);
	}
	
	private void refreshTable() {
    	this.offers.clear();
    	this.offers = itemController.viewOfferItem(userController.getAuthUser().getUserId());
    	ObservableList<OfferItem> offerList = FXCollections.observableArrayList(this.offers);
    	this.offerTable.setItems(offerList);
    }

	private void initializeTable() {
	    TableColumn<OfferItem, String> itemNameCol = new TableColumn<>("Name");
	    itemNameCol.setCellValueFactory(new PropertyValueFactory<>("itemName"));
		itemNameCol.setPrefWidth(200);

	    TableColumn<OfferItem, String> categoryCol = new TableColumn<>("Category");
	    categoryCol.setCellValueFactory(new PropertyValueFactory<>("itemCategory"));
		categoryCol.setPrefWidth(150);
	    
	    TableColumn<OfferItem, String> sizeCol = new TableColumn<>("Size");
	    sizeCol.setCellValueFactory(new PropertyValueFactory<>("itemSize"));
		sizeCol.setPrefWidth(150);

	    TableColumn<OfferItem, String> priceCol = new TableColumn<>("Original Price");
	    priceCol.setCellValueFactory(new PropertyValueFactory<>("itemPrice"));
		priceCol.setPrefWidth(150);

	    TableColumn<OfferItem, String> offerPriceCol = new TableColumn<>("Offer Price");
	    offerPriceCol.setCellValueFactory(new PropertyValueFactory<>("offerPrice"));
		offerPriceCol.setPrefWidth(150);

//	    TableColumn<OfferItem, String> statusCol = new TableColumn<>("Offer Status");
//	    statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
//		statusCol.setPrefWidth(150);

	    TableColumn<OfferItem, Void> actionCol = new TableColumn<>("Action");
		actionCol.setPrefWidth(150);
	    actionCol.setCellFactory(param -> new TableCell<OfferItem, Void>() {
	        private final Button approveBtn = new Button("Approve");
	        private final Button declineBtn = new Button("Decline");

	        {
	            approveBtn.setOnAction(e -> {
	            	OfferItem offerItem = getTableRow().getItem();
	                itemController.acceptOffer(offerItem.getItemId());
	            });

	            declineBtn.setOnAction(e -> {
	            	OfferItem offerItem = getTableRow().getItem();
	                showReasonPopUp(offerItem);
	            });
	        }

	        @Override
	        protected void updateItem(Void item, boolean empty) {
	            super.updateItem(item, empty);
	            if (empty) {
	                setGraphic(null);  
	            } else {
	                HBox buttonBox = new HBox(10);
	                buttonBox.getChildren().addAll(approveBtn, declineBtn);
	                buttonBox.setStyle("-fx-alignment: center-left;");  
	                buttonBox.setSpacing(20); 
	                setGraphic(buttonBox);
	            }
	        }
	    });

	    offerTable.getColumns().addAll(itemNameCol, categoryCol, sizeCol, priceCol, offerPriceCol, actionCol);
	    
	    double tableWidth = 200 + 150 + 150 + 150 + 150 + 150;
	    offerTable.setPrefWidth(tableWidth);
	    offerTable.setMaxWidth(tableWidth);
	}

	@Override
	public void setAlignment() {
		navBP.setTop(navbar);
		navBP.setCenter(offerBP);

		offerBP.setCenter(titleLbl);
		actionBP.setCenter(actionBox);

		layoutBP.setTop(navBP);
		layoutBP.setCenter(offerTable);
		layoutBP.setBottom(actionBP);

		reasonArea.setPromptText("Enter reason for declining...");
		reasonArea.setWrapText(true);

	}

	@Override
	public void setHandler() {
		homeNav.setOnAction(e -> sceneManager.switchSellerPage("sellerhome"));
		uploadNav.setOnAction(e -> sceneManager.switchSellerPage("upload"));
		itemNav.setOnAction(e -> sceneManager.switchSellerPage("selleritem"));
		offerNav.setOnAction(e -> sceneManager.switchSellerPage("selleroffer"));
		logoutNav.setOnAction(e -> userController.logout(sceneManager));
	}

	@Override
	public Scene createScene() {
		return new Scene(layoutBP);
	}

	private void showReasonPopUp(OfferItem offerItem) {
		Stage popUpStage = new Stage();
		popUpStage.setTitle("Enter Decline Reason");
		popUpStage.initModality(Modality.APPLICATION_MODAL);

		Label reasonLabel = new Label("Please provide a reason for declining:");
		TextArea reasonInput = new TextArea();
		reasonInput.setPromptText("Enter reason...");
		reasonInput.setWrapText(true);

		Button submitBtn = new Button("Submit");
		Button cancelBtn = new Button("Cancel");

		submitBtn.setOnAction(e -> {
			String reason = reasonInput.getText().trim();
			String message = itemController.declineOffer(reason, reason);
			
			if (message.equals("Sucess")) popUpStage.close();
			else {
				reasonLabel.setText(message);
				reasonLabel.setStyle("-fx-text-fill: red;");
			}
		});

		cancelBtn.setOnAction(e -> popUpStage.close());

		VBox popUpLayout = new VBox(10, reasonLabel, reasonInput, submitBtn, cancelBtn);
		popUpLayout.setStyle("-fx-padding: 10;");
		popUpLayout.setSpacing(10);
		popUpLayout.setStyle("-fx-alignment: center;");

		Scene popUpScene = new Scene(popUpLayout, 300, 200);
		popUpStage.setScene(popUpScene);

		popUpStage.showAndWait();
	}
}
