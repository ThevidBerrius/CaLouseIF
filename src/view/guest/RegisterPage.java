package view.guest;

import controller.UserController;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import main.SceneManager;
import model.Page;

public class RegisterPage extends Page {
    private SceneManager sceneManager;
    private UserController userController;

    private BorderPane layoutBP, navBP, registerBP;
    private GridPane gp;
    private ScrollPane sp;

    private MenuBar navbar;
    private Menu menu;
    private MenuItem loginNav, registerNav;

    private Label usernameLbl, passwordLbl, phoneNumberLbl, addressLbl, roleLbl, titleLbl, messageLbl;
    private TextField usernameField, phoneNumberField, addressField;
    private PasswordField passwordField;
    private ToggleGroup roleToggle;
    private RadioButton sellerRadio, buyerRadio;
    private Button registerBtn;

    public RegisterPage(Stage primaryStage) {
        this.sceneManager = new SceneManager(primaryStage);
        this.userController = UserController.getInstance();
        initPage();
        setAlignment();
        setHandler();
    }

    @Override
    public void initPage() {
        layoutBP = new BorderPane();
        navBP = new BorderPane();
        registerBP = new BorderPane();

        gp = new GridPane();
        sp = new ScrollPane();

        navbar = new MenuBar();
        menu = new Menu("Menu");
        loginNav = new MenuItem("Login");
        registerNav = new MenuItem("Register");
        navbar.getMenus().add(menu);
        menu.getItems().addAll(loginNav, registerNav);

        titleLbl = new Label("Register");
        GridPane.setHalignment(titleLbl, HPos.CENTER);
        titleLbl.setFont(javafx.scene.text.Font.font("Arial",FontWeight.BOLD, 24));
        titleLbl.setStyle("-fx-text-fill: #333;");

        usernameLbl = new Label("Username");
        usernameLbl.setFont(javafx.scene.text.Font.font("Arial", 14));
        usernameLbl.setStyle("-fx-text-fill: #333;");

        passwordLbl = new Label("Password");
        passwordLbl.setFont(javafx.scene.text.Font.font("Arial", 14));
        passwordLbl.setStyle("-fx-text-fill: #333;");

        phoneNumberLbl = new Label("Phone Number");
        phoneNumberLbl.setFont(javafx.scene.text.Font.font("Arial", 14));
        phoneNumberLbl.setStyle("-fx-text-fill: #333;");

        addressLbl = new Label("Address");
        addressLbl.setFont(javafx.scene.text.Font.font("Arial", 14));
        addressLbl.setStyle("-fx-text-fill: #333;");

        roleLbl = new Label("Role");
        roleLbl.setFont(javafx.scene.text.Font.font("Arial", 14));
        roleLbl.setStyle("-fx-text-fill: #333;");

        messageLbl = new Label("");
        GridPane.setHalignment(messageLbl, HPos.CENTER);
        messageLbl.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");

        usernameField = new TextField();
        usernameField.setPrefWidth(300);
        usernameField.setPromptText("Enter username");
        usernameField.setStyle("-fx-border-color: #ccc; -fx-padding: 5px;");

        passwordField = new PasswordField();
        passwordField.setPrefWidth(300);
        passwordField.setPromptText("Enter password");
        passwordField.setStyle("-fx-border-color: #ccc; -fx-padding: 5px;");

        phoneNumberField = new TextField();
        phoneNumberField.setPrefWidth(300);
        phoneNumberField.setPromptText("Enter phone number");
        phoneNumberField.setStyle("-fx-border-color: #ccc; -fx-padding: 5px;");

        addressField = new TextField();
        addressField.setPrefWidth(300);
        addressField.setPromptText("Enter address");
        addressField.setStyle("-fx-border-color: #ccc; -fx-padding: 5px;");

        roleToggle = new ToggleGroup();
        sellerRadio = new RadioButton("Seller");
        sellerRadio.setToggleGroup(roleToggle);
        buyerRadio = new RadioButton("Buyer");
        buyerRadio.setToggleGroup(roleToggle);

        registerBtn = new Button("Register");
        GridPane.setHalignment(registerBtn, HPos.CENTER);
        registerBtn.setPrefWidth(150); 
        registerBtn.setStyle("-fx-background-color: #007BFF; -fx-text-fill: white; -fx-padding: 8px 16px; -fx-font-size: 14px; -fx-cursor: hand;");
        registerBtn.setOnMouseEntered(e -> registerBtn.setStyle("-fx-background-color: #0056b3; -fx-text-fill: white; -fx-padding: 8px 16px; -fx-font-size: 14px; -fx-cursor: hand;"));
        registerBtn.setOnMouseExited(e -> registerBtn.setStyle("-fx-background-color: #007BFF; -fx-text-fill: white; -fx-padding: 8px 16px; -fx-font-size: 14px; -fx-cursor: hand;"));
    }

    @Override
    public void setAlignment() {
        navBP.setTop(navbar);

        layoutBP.setTop(navBP);
        layoutBP.setCenter(sp);

        sp.setContent(gp);
        sp.setFitToWidth(true); 

        gp.setPadding(new Insets(30, 50, 30, 50)); 
        gp.setHgap(15);
        gp.setVgap(20);

        gp.add(titleLbl, 0, 0, 2, 1);

        gp.add(usernameLbl, 0, 1);
        gp.add(usernameField, 1, 1);

        gp.add(passwordLbl, 0, 2);
        gp.add(passwordField, 1, 2);

        gp.add(phoneNumberLbl, 0, 3);
        gp.add(phoneNumberField, 1, 3);

        gp.add(addressLbl, 0, 4);
        gp.add(addressField, 1, 4);

        gp.add(roleLbl, 0, 5);
        gp.add(buyerRadio, 1, 5);
        gp.add(sellerRadio, 1, 6);

        gp.add(messageLbl, 0, 7, 2, 1);

        gp.add(registerBtn, 0, 8, 2, 1);
        
    }


    @Override
    public void setHandler() {
        loginNav.setOnAction(e -> sceneManager.switchPage("login"));
        registerNav.setOnAction(e -> sceneManager.switchPage("register"));
        registerBtn.setOnAction(e -> {
        	String username = usernameField.getText();
            String password = passwordField.getText();
            String phone_number = phoneNumberField.getText();
            String address = addressField.getText();
            String role = "";
            Toggle selectedToggle = roleToggle.getSelectedToggle();
            if (selectedToggle != null) role = ((RadioButton) selectedToggle).getText();

            String message = userController.register(username, password, phone_number, address, role);

            if (message.equals("Success") && role.equals("Buyer")) sceneManager.switchBuyerPage("buyerhome");
            else if (message.equals("Success") && role.equals("Seller")) sceneManager.switchSellerPage("sellerhome");
            else messageLbl.setText(message);
        });
    }

    @Override
    public Scene createScene() {
        return new Scene(layoutBP);
    }
}
