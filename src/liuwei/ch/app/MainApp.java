package liuwei.ch.app;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {
	
	public static void main(String[] args) {
		launch(args);
	}	

	@Override
	public void start(Stage primaryStage) throws IOException {
		primaryStage.setTitle("Hand Gesture Control");
		
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(MainApp.class.getResource("view/UI.fxml"));
		
		Scene scene = new Scene(loader.load());

		primaryStage.setScene(scene);
		primaryStage.show();
	}

}
