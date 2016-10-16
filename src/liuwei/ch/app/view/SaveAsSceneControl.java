package liuwei.ch.app.view;

import java.awt.TextField;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class SaveAsSceneControl {

	@FXML
	private Button add;
	@FXML
	private TextField sceneName;
	
	@FXML
	protected String save() {
		return sceneName.getText();
	}
	
}
