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

// userId pada table items, kami mengasumsikan items yang dibuat dari seller harus memiliki userId agar dapat mengetahui siapa yang membuat item.
// itemOfferStatus pada table items, kami mengasumsikan status offer dari sebuah item, yang berisi enum seperti Pending, Accepted, Declined.
// itemWishlist pada table items, kami mengasumsikan tidak akan berimpact kepada flow aplikasi.

// Pada aplikasi CalouseIF ini, kami membuat fitur offering dari buyer kepada seller dengan menggunakan satu table tambahan, yaitu offers.
// Jika buyer satu offer harga lebih dari buyer sebelumnya pada satu item, maka offer dari buyer sebelumnya akan dihapus dari table.
// Jika seller melakukan decline pada offer dari buyer, maka offer tersebut juga akan dihapus dari table.