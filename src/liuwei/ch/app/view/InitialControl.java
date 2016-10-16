package liuwei.ch.app.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import liuwei.ch.app.MainApp;
import liuwei.ch.app.model.Detect;

public class InitialControl {
	
//	@FXML
//	private Button start_btn;
//	@FXML
//	private ImageView frame;

	// Reference to the main application.
    private MainApp mainApp;
    
    //手势识别控制
    Detect hand;
   
    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public InitialControl() {
    	hand = new Detect();
    }

    /**
     * Is called by the main application to give a reference back to itself.
     * 
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
    
    @FXML
    protected void startCamera(ActionEvent event) {
//    	hand.start(frame);
    }
    
}
