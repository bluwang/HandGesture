package liuwei.ch.app;

import java.io.File;
import java.io.IOException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.opencv.core.Core;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import liuwei.ch.app.model.HSVData;
import liuwei.ch.app.view.InitialControl;
import liuwei.ch.app.view.RootLayoutControl;

public class MainApp extends Application {
	
	private Stage stage;
	private BorderPane rootLayout;

	public static void main(String[] args) {
		//加载openCV中的这个文件（后面的很多都要用的，先加载）
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
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
//		this.stage = primaryStage;
//		this.stage.setTitle("Hand Gesture Control");
//		
//		initRootLayout();
//		
//		showInitialUI();
	}

	private void initRootLayout() {
		try {
			//load root layout from fxml file
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/RootLayoutUI.fxml"));
			rootLayout = (BorderPane) loader.load();
			
			//Show the scene containing the root layout
			Scene scene = new Scene(rootLayout);
			this.stage.setScene(scene);
			
			// Give the controller access to the main app
			RootLayoutControl rootLayoutControl = new RootLayoutControl();
			rootLayoutControl.setMainApp(this);
			
			this.stage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void showInitialUI() {
		// TODO Auto-generated method stub
		try {
			// Load person overview.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/InitialUI.fxml"));
			BorderPane initialUI = (BorderPane) loader.load();
			
			// Set person overview into the center of root layout.
			rootLayout.setCenter(initialUI);
			
			// Give the controller access to the main app.
			InitialControl controller = loader.getController();
			controller.setMainApp(this);
			
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
	
}
