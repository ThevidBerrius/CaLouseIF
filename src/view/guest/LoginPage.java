package view.guest;

import controller.UserController;
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
    private UserController userController;

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
        GridPane.setHalignment(titleLbl, HPos.CENTER);
        titleLbl.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        titleLbl.setStyle("-fx-text-fill: #333;");

        usernameLbl = new Label("Username:");
        usernameLbl.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
        usernameLbl.setStyle("-fx-text-fill: #333;");

        passwordLbl = new Label("Password:");
        passwordLbl.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
        passwordLbl.setStyle("-fx-text-fill: #333;");

        messageLbl = new Label("");
        GridPane.setHalignment(messageLbl, HPos.CENTER);
        messageLbl.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");

        usernameField = new TextField();
        usernameField.setPrefWidth(300); 
        usernameField.setPromptText("Enter your username");
        usernameField.setStyle("-fx-border-color: #ccc; -fx-padding: 5px;");

        passwordField = new PasswordField();
        passwordField.setPrefWidth(300);
        passwordField.setPromptText("Enter your password");
        passwordField.setStyle("-fx-border-color: #ccc; -fx-padding: 5px;");

        loginBtn = new Button("Login");
        GridPane.setHalignment(loginBtn, HPos.CENTER);
        loginBtn.setPrefWidth(150); 
        loginBtn.setStyle("-fx-background-color: #007BFF; -fx-text-fill: white; -fx-padding: 8px 16px; -fx-font-size: 14px; -fx-cursor: hand;");
        loginBtn.setOnMouseEntered(e -> loginBtn.setStyle("-fx-background-color: #0056b3; -fx-text-fill: white; -fx-padding: 8px 16px; -fx-font-size: 14px; -fx-cursor: hand;"));
        loginBtn.setOnMouseExited(e -> loginBtn.setStyle("-fx-background-color: #007BFF; -fx-text-fill: white; -fx-padding: 8px 16px; -fx-font-size: 14px; -fx-cursor: hand;"));
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

        gp.add(messageLbl, 0, 3, 2, 1);

        gp.add(loginBtn, 0, 4, 2, 1);
        
    }


    @Override
    public void setHandler() {
        loginNav.setOnAction(e -> sceneManager.switchPage("login"));
        registerNav.setOnAction(e -> sceneManager.switchPage("register"));
        loginBtn.setOnAction(e -> {
        	String username = usernameField.getText();
            String password = passwordField.getText();

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
        });
    }
    
    @Override
    public Scene createScene() {
        return new Scene(layoutBP);
    }
}
