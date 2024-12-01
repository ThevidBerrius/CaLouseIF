package main;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
	private static SceneManager sceneManager;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		sceneManager = new SceneManager(primaryStage);
		sceneManager.switchPage("login");
		
		primaryStage.setTitle("CaLouseIF");
		primaryStage.show();
	}

}