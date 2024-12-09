package view.admin;

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
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.Modality;
import main.SceneManager;
import model.Item;
import model.Page;

public class RequestedPage extends Page {
    private SceneManager sceneManager;

    private GridPane gp;
    private ScrollPane sp;

    private MenuBar navbar;
    private Menu menu;
    private MenuItem homeNav, requestedNav;

    private BorderPane layoutBP, navBP, requestedBP, reasonBP;

    private TableView<Item> requestedTable;

    private Button approveBtn, declineBtn;
    private TextArea reasonArea;
    private Label reasonLbl, titleLbl;
    private VBox actionBox;

    public RequestedPage(Stage primaryStage) {
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
        requestedBP = new BorderPane();
        reasonBP = new BorderPane();

        gp = new GridPane();
        sp = new ScrollPane();

        navbar = new MenuBar();
        menu = new Menu("Menu");
        homeNav = new MenuItem("Home");
        requestedNav = new MenuItem("Requested Item");
        navbar.getMenus().add(menu);
        menu.getItems().addAll(homeNav, requestedNav);

        requestedTable = new TableView<>();
        approveBtn = new Button("Approve");
        declineBtn = new Button("Decline");
        reasonArea = new TextArea();
        reasonLbl = new Label("Reason for Declining:");
        titleLbl = new Label("Requested Item");

        actionBox = new VBox(10);
    }

    private void initializeTable() {
        TableColumn<Item, String> nameCol = new TableColumn<Item, String>("itemName");
        nameCol.setCellValueFactory(new PropertyValueFactory<Item, String>("itemName"));

        TableColumn<Item, String> categoryCol = new TableColumn<Item, String>("itemCategory");
        categoryCol.setCellValueFactory(new PropertyValueFactory<Item, String>("itemCategory"));

        TableColumn<Item, String> sizeCol = new TableColumn<Item, String>("itemSize");
        sizeCol.setCellValueFactory(new PropertyValueFactory<Item, String>("itemSize"));

        TableColumn<Item, String> priceCol = new TableColumn<Item, String>("itemPrice");
        priceCol.setCellValueFactory(new PropertyValueFactory<Item, String>("itemPrice"));

        requestedTable.getColumns().addAll(nameCol, categoryCol, sizeCol, priceCol);
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

        actionBox.getChildren().addAll(approveBtn, declineBtn);
        actionBox.setSpacing(10);
        actionBox.setStyle("-fx-padding: 10; -fx-alignment: top-center;");
    }

    @Override
    public void setHandler() {
    	homeNav.setOnAction(e->sceneManager.switchPage("adminhome"));
		requestedNav.setOnAction(e->sceneManager.switchPage("adminrequested"));
        declineBtn.setOnAction(e -> showReasonPopUp());
        approveBtn.setOnAction(e -> {
            System.out.println("Item approved!");
        });
    }

    @Override
    public void handlePage(ActionEvent e) {
    	
    }

    @Override
    public Scene createScene() {
        return new Scene(layoutBP);
    }

    private void showReasonPopUp() {
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
                System.out.println("Item declined with reason: " + reason);
                popUpStage.close(); 
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
