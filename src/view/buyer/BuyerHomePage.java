package view.buyer;

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
import model.Item;
import model.Page;

public class BuyerHomePage extends Page {
    private SceneManager sceneManager;

    private BorderPane layoutBP, navBP, buyerBP;

    private GridPane gp;
    private ScrollPane sp;

    private MenuBar navbar;
    private Menu menu;
    private MenuItem homeNav, wishlistNav, historyNav;

    private Label titleLbl;

    private Button offerBtn, wishlistBtn, buyBtn;

    private TableView<Item> itemTable;

    public BuyerHomePage(Stage primaryStage) {
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
        buyerBP = new BorderPane();

        gp = new GridPane();
        sp = new ScrollPane();

        navbar = new MenuBar();
        menu = new Menu("Menu");
        homeNav = new MenuItem("Home");
        wishlistNav = new MenuItem("Wishlist");
        historyNav = new MenuItem("History");
        navbar.getMenus().add(menu);
        menu.getItems().addAll(homeNav, wishlistNav, historyNav);

        titleLbl = new Label("Home");

        offerBtn = new Button("Offer Item");
        wishlistBtn = new Button("Add Wishlist");
        buyBtn = new Button("Buy");

        itemTable = new TableView<Item>();
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

        itemTable.getColumns().addAll(nameCol, categoryCol, sizeCol, priceCol);
    }

    @Override
    public void setAlignment() {
        navBP.setTop(navbar);

        buyerBP.setTop(titleLbl);

        layoutBP.setTop(navBP);
        layoutBP.setCenter(itemTable);

        GridPane buttonPane = new GridPane();
        buttonPane.setHgap(10); 
        buttonPane.setVgap(10);
        buttonPane.add(offerBtn, 0, 0);
        buttonPane.add(wishlistBtn, 1, 0);
        buttonPane.add(buyBtn, 2, 0);

        layoutBP.setBottom(buttonPane);

        buttonPane.setStyle("-fx-alignment: center; -fx-padding: 10;");
    }

    @Override
    public void setHandler() {
        // TODO Auto-generated method stub
    }

    @Override
    public void handlePage(ActionEvent e) {
        // TODO Auto-generated method stub
    }

    @Override
    public Scene createScene() {
        // Create and return the Scene with the layout
        return new Scene(layoutBP, 800, 600); // Adjust width and height as necessary
    }
}
