package view.guest;

import controller.UserController;
import javafx.event.ActionEvent;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import main.SceneManager;
import model.Page;

public class LoginPage extends Page {

    private SceneManager sceneManager;

    private BorderPane layoutBP, navBP, loginBP;
    private GridPane gp;
    private ScrollPane sp;

    private MenuBar navbar;
    private Menu menu;
    private MenuItem loginNav, registerNav;

    private Label usernameLbl, passwordLbl, titleLbl, messageLbl;
    private TextField usernameField;
    private PasswordField passwordField;
    private Button loginBtn;

    public LoginPage(Stage primaryStage) {
        sceneManager = new SceneManager(primaryStage);
        initPage();
        setAlignment();
        setHandler();
    }

    @Override
    public void initPage() {
        layoutBP = new BorderPane();
        navBP = new BorderPane();
        loginBP = new BorderPane();

        gp = new GridPane();
        sp = new ScrollPane();

        navbar = new MenuBar();
        menu = new Menu("Menu");
        loginNav = new MenuItem("Login");
        registerNav = new MenuItem("Register");
        navbar.getMenus().add(menu);
        menu.getItems().addAll(loginNav, registerNav);

        titleLbl = new Label("Login");
        titleLbl.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        titleLbl.setStyle("-fx-text-fill: #333;");

        usernameLbl = new Label("Username:");
        usernameLbl.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
        usernameLbl.setStyle("-fx-text-fill: #333;");

        passwordLbl = new Label("Password:");
        passwordLbl.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
        passwordLbl.setStyle("-fx-text-fill: #333;");

        messageLbl = new Label("");
        messageLbl.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");

        usernameField = new TextField();
        usernameField.setPromptText("Enter your username");
        usernameField.setStyle("-fx-border-color: #ccc; -fx-padding: 5px;");

        passwordField = new PasswordField();
        passwordField.setPromptText("Enter your password");
        passwordField.setStyle("-fx-border-color: #ccc; -fx-padding: 5px;");

        loginBtn = new Button("Login");
        loginBtn.setStyle("-fx-background-color: #007BFF; -fx-text-fill: white; -fx-padding: 8px 16px; -fx-font-size: 14px; -fx-cursor: hand;");
        loginBtn.setOnMouseEntered(e -> loginBtn.setStyle("-fx-background-color: #0056b3; -fx-text-fill: white; -fx-padding: 8px 16px; -fx-font-size: 14px; -fx-cursor: hand;"));
        loginBtn.setOnMouseExited(e -> loginBtn.setStyle("-fx-background-color: #007BFF; -fx-text-fill: white; -fx-padding: 8px 16px; -fx-font-size: 14px; -fx-cursor: hand;"));
    }

    @Override
    public void setAlignment() {
        navBP.setTop(navbar);
        navBP.setCenter(loginBP);

        loginBP.setCenter(titleLbl);

        layoutBP.setTop(navBP);
        layoutBP.setCenter(sp);

        sp.setContent(gp);
        sp.setFitToWidth(true);

        gp.setPadding(new Insets(20));
        gp.setHgap(10);
        gp.setVgap(15);

        gp.add(titleLbl, 0, 0, 2, 1);
        GridPane.setHalignment(titleLbl, HPos.CENTER);

        gp.add(usernameLbl, 0, 1);
        gp.add(usernameField, 1, 1);

        gp.add(passwordLbl, 0, 2);
        gp.add(passwordField, 1, 2);

        gp.add(messageLbl, 0, 3, 2, 1);
        GridPane.setHalignment(messageLbl, HPos.CENTER);

        gp.add(loginBtn, 0, 4, 2, 1);
        GridPane.setHalignment(loginBtn, HPos.CENTER);
    }

    @Override
    public void setHandler() {
        loginNav.setOnAction(e -> sceneManager.switchPage("login"));
        registerNav.setOnAction(e -> sceneManager.switchPage("register"));
        loginBtn.setOnAction(e -> handlePage(e));
    }

    @Override
    public void handlePage(ActionEvent e) {
        String username = usernameField.getText();
        String password = passwordField.getText();
        UserController userController = new UserController();

        if (username.equals("admin") && password.equals("admin")) {
            sceneManager.switchPage("adminhome");
        }

        String message = userController.login(username, password);

        if (message.equals("Admin")) {
            sceneManager.switchPage("adminhome");
        } else if (message.equals("Buyer")) {
            sceneManager.switchBuyerPage("buyerhome");
        } else if (message.equals("Seller")) {
            sceneManager.switchSellerPage("sellerhome");
        } else {
            messageLbl.setText(message);
        }
    }

    @Override
    public Scene createScene() {
        return new Scene(layoutBP);
    }
}
