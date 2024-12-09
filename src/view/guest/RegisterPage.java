package view.guest;

import controller.UserController;
import javafx.event.ActionEvent;
import javafx.geometry.HPos;
import javafx.geometry.Rectangle2D;
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
import javafx.stage.Stage;
import main.SceneManager;
import model.Page;

public class RegisterPage extends Page{
	private SceneManager sceneManager;
	
	private BorderPane layoutBP, navBP, registerBP;
	private GridPane gp;
	private ScrollPane sp;
	
	private MenuBar navbar;
	private Menu menu;
	private MenuItem loginNav, registerNav;
	
	private Label usernameLbl, passwordLbl, phoneNumberLbl, addressLbl, roleLbl, titleLbl;
	private TextField usernameField, phoneNumberField, addressField;
	private PasswordField passwordField;
	private ToggleGroup roleToggle;
	private RadioButton sellerRadio, buyerRadio;
	private Button registerBtn;
	
	public RegisterPage(Stage primaryStage) {
		sceneManager = new SceneManager(primaryStage);
		initPage();
		setAlignment();
		setHandler();
	}

	@Override
	public void initPage() {
		layoutBP = new BorderPane();
		navBP = new BorderPane();
		registerBP = new BorderPane();
		
		gp =  new GridPane();
		sp = new ScrollPane();
		
		navbar = new MenuBar();
		menu = new Menu("Menu");
		loginNav = new MenuItem("Login");
		registerNav = new MenuItem("Register");
		navbar.getMenus().add(menu);
		menu.getItems().addAll(loginNav, registerNav);
		
		titleLbl = new Label("Register");
		usernameLbl = new Label("Username");
		passwordLbl = new Label("Password");
		phoneNumberLbl = new Label("Phone Number");
		addressLbl = new Label("Address");
		roleLbl = new Label("Role");
		
		usernameField = new TextField();
		passwordField = new PasswordField();
		phoneNumberField = new TextField();
		addressField = new TextField();
		
		roleToggle = new ToggleGroup();
		sellerRadio = new RadioButton("Seller");
		sellerRadio.setToggleGroup(roleToggle);
		
		buyerRadio = new RadioButton("Buyer");
		buyerRadio.setToggleGroup(roleToggle);
		
		registerBtn = new Button("Register");
	}

	@Override
	public void setAlignment() {
		navBP.setTop(navbar);
		navBP.setCenter(registerBP);
		
		registerBP.setCenter(titleLbl);
		
		layoutBP.setTop(navBP);
		layoutBP.setCenter(sp);
		
		sp.setContent(gp);
		
		gp.add(usernameLbl, 0, 0);
		gp.add(usernameField, 1, 0);
		gp.add(passwordLbl, 0, 1);
		gp.add(passwordField, 1, 1);
		gp.add(phoneNumberLbl, 0, 2);
		gp.add(phoneNumberField, 1, 2);
		gp.add(addressLbl, 0, 3);
		gp.add(addressField, 1, 3);
		gp.add(roleLbl, 0, 4);
		gp.add(buyerRadio, 1, 4);
		gp.add(sellerRadio, 1, 5);
		
		gp.add(registerBtn, 0, 7, 2, 1);
		GridPane.setHalignment(registerBtn, HPos.CENTER);
		
//		buyerRadio.setSelected(true);
	}

	@Override
	public void setHandler() {
		loginNav.setOnAction(e -> sceneManager.switchPage("login"));
		registerNav.setOnAction(e -> sceneManager.switchPage("register"));
		registerBtn.setOnAction(e -> handlePage(e));
	}

	@Override
	public void handlePage(ActionEvent e) {
		String username = usernameField.getText();
		String password = passwordField.getText();
		String phone_number = phoneNumberField.getText();
		String address = addressField.getText();
		String role = "";
		Toggle selectedToggle = roleToggle.getSelectedToggle();
		if(selectedToggle != null) role = ((RadioButton) selectedToggle).getText();
		UserController authController = new UserController();
		
		if(authController.register(username, password, phone_number, address, role)) {
			System.out.println("Berhasil Input database");
		}
	}

	@Override
	public Scene createScene() {
		return new Scene(layoutBP);
	}
}
