package main;

import javafx.scene.Scene;
import javafx.stage.Stage;
import view.guest.RegisterPage;

public class SceneManager {
    private Stage primaryStage;

    public SceneManager(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    private void setScene(Scene scene) {
        primaryStage.setScene(scene);
    }
    
    public void switchPage(String pageName) {
        switch (pageName.toLowerCase()) { 
            case "register":
                RegisterPage register = new RegisterPage(primaryStage);
                setScene(register.createScene()); 
                break;
            default:
                throw new IllegalArgumentException("Page not found: " + pageName);
        }
    }
}
