package view.guest;

import javafx.event.ActionEvent;
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
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import main.SceneManager;
import model.Page;

public class RegisterPage extends Page{
	private SceneManager sceneManager;
	
	private BorderPane layoutBP, navBP;
	private GridPane gp;
	private ScrollPane sp;
	
	private MenuBar navbar;
	private Menu menu;
	private MenuItem loginNav, registerNav;
	
	private Label usernameLbl, passwordLbl, phoneNumberLbl, addressLbl, roleLbl, titleLbl;
	private TextField usernameField, phoneNumberField, addressField;
	private PasswordField passwordField;
	private ToggleGroup role;
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
		
		gp =  new GridPane();
		sp = new ScrollPane();
		
		navbar = new MenuBar();
		menu = new Menu("Tes");
		loginNav = new MenuItem("Login");
		registerNav = new MenuItem("Register");
		navbar.getMenus().add(menu);
		menu.getItems().addAll(loginNav, registerNav);
	}

	@Override
	public void setAlignment() {
		// TODO Auto-generated method stub
		
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
		// TODO Auto-generated method stub
		return null;
	}
}
