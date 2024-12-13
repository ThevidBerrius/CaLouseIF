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
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.Modality;
import main.SceneManager;
import model.Item;
import model.Page;

public class RequestedPage extends Page {
    private SceneManager sceneManager;
    private ItemController itemController;
    private UserController userController;
    private Vector<Item> items;

    private GridPane gp;
    private ScrollPane sp;

    private MenuBar navbar;
    private Menu menu;
    private MenuItem homeNav, requestedNav, logoutNav;

    private BorderPane layoutBP, navBP, requestedBP, reasonBP;

    private TableView<Item> requestedTable;

    private Button approveBtn, declineBtn;
    private TextArea reasonArea;
    private Label reasonLbl, titleLbl;
    private VBox actionBox;

    public RequestedPage(Stage primaryStage) {
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
        requestedBP = new BorderPane();
        reasonBP = new BorderPane();

        gp = new GridPane();
        sp = new ScrollPane();

        navbar = new MenuBar();
        menu = new Menu("Menu");
        homeNav = new MenuItem("Home");
        requestedNav = new MenuItem("Requested Item");
        logoutNav = new MenuItem("Logout");
        navbar.getMenus().add(menu);
        menu.getItems().addAll(homeNav, requestedNav, logoutNav);

        requestedTable = new TableView<>();
        approveBtn = new Button("Approve");
        declineBtn = new Button("Decline");
        reasonArea = new TextArea();
        reasonLbl = new Label("Reason for Declining:");
        titleLbl = new Label("Admin Requested Item");

        actionBox = new VBox(10);
    }
    
    private void refreshTable() {
    	this.items.clear();
    	this.items = itemController.viewRequestedItem();
    	ObservableList<Item> itemList = FXCollections.observableArrayList(this.items);
    	this.requestedTable.setItems(itemList);
    }

    private void initializeTable() {
        TableColumn<Item, String> nameCol = new TableColumn<Item, String>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<Item, String>("itemName"));

        TableColumn<Item, String> categoryCol = new TableColumn<Item, String>("Category");
        categoryCol.setCellValueFactory(new PropertyValueFactory<Item, String>("itemCategory"));

        TableColumn<Item, String> sizeCol = new TableColumn<Item, String>("Size");
        sizeCol.setCellValueFactory(new PropertyValueFactory<Item, String>("itemSize"));

        TableColumn<Item, String> priceCol = new TableColumn<Item, String>("Price");
        priceCol.setCellValueFactory(new PropertyValueFactory<Item, String>("itemPrice"));
        
        TableColumn<Item, Void> actionCol = new TableColumn<Item, Void>("Action");
        
        actionCol.setCellFactory(e -> new TableCell<Item, Void>() {
            private final Button approveBtn = new Button("Approve");
            private final Button declineBtn = new Button("Decline");

            {
                approveBtn.setOnAction(e -> {
                	Item item = getTableRow().getItem();
                	itemController.approveItem(item.getItemId(), sceneManager);
                });

                declineBtn.setOnAction(e -> {
                	Item item = getTableRow().getItem();
                    showReasonPopUp(item.getItemId());
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
                    setGraphic(buttonBox);
                }
            }
        });

        requestedTable.getColumns().addAll(nameCol, categoryCol, sizeCol, priceCol, actionCol);
    }

    @Override
    public void setAlignment() {
        navBP.setTop(navbar);
        navBP.setCenter(requestedBP);

        requestedBP.setCenter(titleLbl);
        reasonBP.setCenter(actionBox);

        layoutBP.setTop(navBP);
        layoutBP.setCenter(requestedTable);
        layoutBP.setBottom(reasonBP);

        reasonArea.setPromptText("Enter reason for declining...");
        reasonArea.setWrapText(true);
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

    private void showReasonPopUp(String itemId) {
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
            if (reason.isEmpty()) {
                reasonLabel.setText("Reason cannot be empty!");
                reasonLabel.setStyle("-fx-text-fill: red;");
            } else {
                itemController.declineItem(itemId, sceneManager);
                popUpStage.close();
            }
        });

        cancelBtn.setOnAction(e -> popUpStage.close());

        HBox buttonBox = new HBox(10, submitBtn, cancelBtn); 
        buttonBox.setStyle("-fx-alignment: center;"); 
        buttonBox.setSpacing(20); 

        VBox popUpLayout = new VBox(10, reasonLabel, reasonInput, buttonBox);
        popUpLayout.setStyle("-fx-padding: 10; -fx-alignment: center;");

        Scene popUpScene = new Scene(popUpLayout, 300, 200);
        popUpStage.setScene(popUpScene);

        popUpStage.showAndWait();
    }

}