package view.admin;

import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.SceneManager;
import model.Page;

public class AdminHomePage extends Page {

	private SceneManager sceneManager;

	public AdminHomePage(Stage primaryStage) {
		sceneManager = new SceneManager(primaryStage);
		initPage();
		setAlignment();
		setHandler();
	}

	@Override
	public void initPage() {
		// TODO Auto-generated method stub

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
