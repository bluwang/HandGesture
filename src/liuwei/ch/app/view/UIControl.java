package liuwei.ch.app.view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import liuwei.ch.app.model.Hand;
import liuwei.ch.app.model.HandDetect;

public class UIControl {
	
	@FXML
	private ImageView ImageView;
	@FXML
	private Button play;
	@FXML
	private Slider H_MIN;
	@FXML
	private Slider H_MAX;
	@FXML
	private Slider S_MIN;
	@FXML
	private Slider S_MAX;
	@FXML
	private Slider V_MIN;
	@FXML
	private Slider V_MAX;


	boolean isPlay;
	Hand hand;
	
	public UIControl() {
		isPlay = false;
		hand = new Hand();
	}

	@FXML
	protected void startOrStopCamera() {
		if (isPlay) {
			hand.start(ImageView);
			play.setText("START");
			isPlay = false;
		}
		else {
			hand.stopCamera();
			play.setText("STOP");
			isPlay = true;
		}
	}
	
	@FXML
	protected void setH_MIN() {
		System.out.println("H_MIN");
		HandDetect.setH_MIN((byte) H_MIN.getValue());
	}

	@FXML
	protected void setH_MAX() {
		System.out.println("H_MAX");
		HandDetect.setH_MAX((byte) H_MAX.getValue());
	}

	@FXML
	protected void setS_MIN() {
		System.out.println("S_MIN");
		HandDetect.setS_MIN((byte) S_MIN.getValue());
	}

	@FXML
	protected void setS_MAX() {
		System.out.println("S_MAX");
		HandDetect.setS_MAX((byte) S_MAX.getValue());
	}

	@FXML
	protected void setV_MIN() {
		System.out.println("V_MIN");
		HandDetect.setV_MIN((byte) V_MIN.getValue());
	}

	@FXML
	protected void setV_MAX() {
		System.out.println("V_MAX");
		HandDetect.setV_MAX((byte) V_MAX.getValue());
	}
}
