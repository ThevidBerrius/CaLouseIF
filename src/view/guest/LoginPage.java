package view.guest;

import controller.AuthController;
import javafx.event.ActionEvent;
import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
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
	
	private Label usernameLbl, passwordLbl, titleLbl;
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
		usernameLbl = new Label("Username");
		passwordLbl = new Label("Password");
		
		usernameField = new TextField();
		passwordField = new PasswordField();
		
		loginBtn = new Button("Login");
	}

	@Override
	public void setAlignment() {
		navBP.setTop(navbar);
		navBP.setCenter(loginBP);
		
		loginBP.setCenter(titleLbl);
		
		layoutBP.setTop(navBP);
		layoutBP.setCenter(sp);
		
		sp.setContent(gp);
		
		gp.add(usernameLbl, 0, 0);
		gp.add(usernameField, 1, 0);
		gp.add(passwordLbl, 0, 1);
		gp.add(passwordField, 1, 1);
		
		gp.add(loginBtn, 0, 3, 2, 1);
		GridPane.setHalignment(loginBtn, HPos.CENTER);

	}

	@Override
	public void setHandler() {
		loginNav.setOnAction(e -> sceneManager.switchPage("login"));
		registerNav.setOnAction(e -> sceneManager.switchPage("register"));
		loginBtn.setOnAction(e -> login());
	}

	@Override
	public void handlePage(ActionEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public Scene createScene() {
		return new Scene(layoutBP);
	}

	private void login() {
		String username = usernameField.getText();
		String password = passwordField.getText();
		AuthController authController = new AuthController();
		
		if(authController.login(username, password)) {
			System.out.println("Direct Home Page");
		} else {
			System.out.println("Gagal");
		}
	}
}
