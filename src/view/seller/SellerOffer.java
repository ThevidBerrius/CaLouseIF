package view.seller;

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
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.SceneManager;
import model.Offer;
import model.Page;

public class SellerOffer extends Page {
    private SceneManager sceneManager;

    private BorderPane layoutBP, navBP, offerBP, actionBP;

    private GridPane gp;
    private ScrollPane sp;

    private MenuBar navbar;
    private Menu menu;
    private MenuItem homeNav, uploadNav, itemNav, offerNav;

    private TableView<Offer> offerTable;

    private Button approveBtn, declineBtn;
    private TextArea reasonArea;
    private Label reasonLbl, titleLbl;
    private VBox actionBox;

    public SellerOffer(Stage primaryStage) {
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

        navbar.getMenus().add(menu);
        menu.getItems().addAll(homeNav, uploadNav, itemNav, offerNav);

        offerTable = new TableView<>();
        approveBtn = new Button("Approve");
        declineBtn = new Button("Decline");
        reasonArea = new TextArea();
        reasonLbl = new Label("Reason for Declining:");
        titleLbl = new Label("Seller Offers");

        actionBox = new VBox(10);
    }

    private void initializeTable() {
        TableColumn<Offer, String> itemNameCol = new TableColumn<>("Name");
        itemNameCol.setCellValueFactory(new PropertyValueFactory<>("itemName"));

        TableColumn<Offer, String> categoryCol = new TableColumn<>("Category");
        categoryCol.setCellValueFactory(new PropertyValueFactory<>("itemCategory"));

        TableColumn<Offer, String> sizeCol = new TableColumn<>("Size");
        sizeCol.setCellValueFactory(new PropertyValueFactory<>("itemSize"));

        TableColumn<Offer, String> priceCol = new TableColumn<>("Original Price");
        priceCol.setCellValueFactory(new PropertyValueFactory<>("itemPrice"));

        TableColumn<Offer, String> offerPriceCol = new TableColumn<>("Offer Price");
        offerPriceCol.setCellValueFactory(new PropertyValueFactory<>("offerPrice"));

        TableColumn<Offer, String> statusCol = new TableColumn<>("Offer Status");
        statusCol.setCellValueFactory(new PropertyValueFactory<>("itemOfferStatus"));

        offerTable.getColumns().addAll(itemNameCol, categoryCol, sizeCol, priceCol, offerPriceCol, statusCol);
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

        actionBox.getChildren().addAll(approveBtn, declineBtn);
        actionBox.setStyle("-fx-padding: 10; -fx-alignment: top-center;");
    }

    @Override
    public void setHandler() {
    	homeNav.setOnAction(e->sceneManager.switchSellerPage("sellerhome"));
		uploadNav.setOnAction(e->sceneManager.switchSellerPage("upload"));
		itemNav.setOnAction(e->sceneManager.switchSellerPage("selleritem"));
        offerNav.setOnAction(e -> sceneManager.switchSellerPage("selleroffer"));

        declineBtn.setOnAction(e -> showReasonPopUp());
        approveBtn.setOnAction(e -> {
            System.out.println("Offer approved!");
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
                System.out.println("Offer declined with reason: " + reason);
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
