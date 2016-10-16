package liuwei.ch.app.view;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Slider;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.ImageView;
import liuwei.ch.app.model.Detect;
import liuwei.ch.app.model.HSVData;
import liuwei.ch.app.model.HSVDataWrapper;
import liuwei.ch.app.model.HandDetect;

public class UIControl {
	
	@FXML
	private ImageView ImageView;
	@FXML
	private Button play;
	@FXML
	private ChoiceBox<String> sceneChoose;
	@FXML
	private Slider H_MIN1;
	@FXML
	private Slider H_MAX1;
	@FXML
	private Slider S_MIN1;
	@FXML
	private Slider S_MAX1;
	@FXML
	private Slider V_MIN1;
	@FXML
	private Slider V_MAX1;
	@FXML
	private Slider H_MIN2;
	@FXML
	private Slider H_MAX2;
	@FXML
	private Slider S_MIN2;
	@FXML
	private Slider S_MAX2;
	@FXML
	private Slider V_MIN2;
	@FXML
	private Slider V_MAX2;


	File colorRangeFile;
	boolean isPlay;
	Detect detect;
	HSVDataWrapper hsvDataWrapper;
	
	public UIControl() {
		isPlay = false;
		detect = new Detect();

		colorRangeFile = new File("D:/Coding/Java/java/HandGesture/resources/XML/colorRange.xml");
		loadHSVData(colorRangeFile);
	}

	private void init() {
H_MIN1.setValue((double) HandDetect.getH_MIN1());
		H_MIN2.setValue((double) HandDetect.getH_MIN2());
		H_MAX1.setValue((double) HandDetect.getH_MAX1());
		H_MAX2.setValue((double) HandDetect.getH_MAX2());
		S_MIN1.setValue((double) HandDetect.getS_MIN1());
		S_MIN2.setValue((double) HandDetect.getS_MIN2());
		S_MAX1.setValue((double) HandDetect.getS_MAX1());
		S_MAX2.setValue((double) HandDetect.getS_MAX2());
		V_MIN1.setValue((double) HandDetect.getV_MIN1());
		V_MIN2.setValue((double) HandDetect.getV_MIN2());
		V_MAX1.setValue((double) HandDetect.getV_MAX1());
		V_MAX2.setValue((double) HandDetect.getV_MAX2());
	}

	@FXML
	protected void startOrStopCamera() {
		if (isPlay) {
			detect.stopCamera();
			play.setText("START");
			isPlay = false;
		}
		else {
			detect.start(ImageView);
			play.setText("STOP");
			isPlay = true;
		}
		init();
	}
	
	@FXML
	protected void saveScene() throws IOException {
		TextInputDialog inputDialog = new TextInputDialog("«Î ‰»Î≥°æ∞√˚");
		inputDialog.setTitle("save scene");
		
		Optional<String> result = inputDialog.showAndWait();
		
		byte[] data = {(byte)H_MIN1.getValue(), (byte)H_MAX1.getValue(),
				(byte)S_MIN1.getValue(), (byte)S_MAX1.getValue(),
				(byte)V_MIN1.getValue(), (byte)V_MAX1.getValue()};
		
		if (result.isPresent()) {
			HSVData hsvData = new HSVData();
			hsvData.setHSV(data);
			String sceneName = result.get();
			
			hsvDataWrapper.add(sceneName, hsvData);

			File file = new File("D:/Coding/Java/java/HandGesture/resources/XML/colorRange.xml");
			saveHSVData(file);
		}
	}
	
	@FXML
	protected void loadScene() {
		sceneChoose.getItems().clear();
		List<String> scenes = hsvDataWrapper.getScenes();
		for (int i = 0; i < scenes.size(); i++) {
			sceneChoose.getItems().add(scenes.get(i));
		}
	}
	
	@FXML
	protected void setScene() {
		System.out.println(sceneChoose.getValue());
		
		String sceneName = sceneChoose.getValue().toString();
		HSVData sceneData = hsvDataWrapper.getHSVData(sceneName);
		HandDetect.setH_MIN1(sceneData.getHSV()[0]);
		HandDetect.setH_MAX1(sceneData.getHSV()[1]);
		HandDetect.setS_MIN1(sceneData.getHSV()[2]);
		HandDetect.setS_MAX1(sceneData.getHSV()[3]);
		HandDetect.setV_MIN1(sceneData.getHSV()[4]);
		HandDetect.setV_MAX1(sceneData.getHSV()[5]);
		H_MIN1.setValue((double) sceneData.getHSV()[0]);
		H_MAX1.setValue((double) sceneData.getHSV()[1]);
		S_MIN1.setValue((double) sceneData.getHSV()[2]);
		S_MAX1.setValue((double) sceneData.getHSV()[3]);
		V_MIN1.setValue((double) sceneData.getHSV()[4]);
		V_MAX1.setValue((double) sceneData.getHSV()[5]);
	}
	
	@FXML
	protected void deleteScene() {
		
	}
	
	@FXML
	protected void clearScenes() {
		hsvDataWrapper = new HSVDataWrapper();
		saveHSVData(colorRangeFile);
	}

	@FXML
	protected void setH_MIN1() {
		System.out.println("H_MIN");
		HandDetect.setH_MIN1((int) H_MIN1.getValue());
	}

	@FXML
	protected void setH_MAX1() {
		System.out.println("H_MAX");
		HandDetect.setH_MAX1((int) H_MAX1.getValue());
	}

	@FXML
	protected void setS_MIN1() {
		System.out.println("S_MIN");
		HandDetect.setS_MIN1((int) S_MIN1.getValue());
	}

	@FXML
	protected void setS_MAX1() {
		System.out.println("S_MAX");
		HandDetect.setS_MAX1((int) S_MAX1.getValue());
	}

	@FXML
	protected void setV_MIN1() {
		System.out.println("V_MIN");
		HandDetect.setV_MIN1((int) V_MIN1.getValue());
	}

	@FXML
	protected void setV_MAX1() {
		System.out.println("V_MAX");
		HandDetect.setV_MAX1((int) V_MAX1.getValue());
	}
	
	@FXML
	protected void setH_MIN2() {
		System.out.println("H_MIN");
		HandDetect.setH_MIN2((int) H_MIN2.getValue());
	}

	@FXML
	protected void setH_MAX2() {
		System.out.println("H_MAX");
		HandDetect.setH_MAX2((int) H_MAX2.getValue());
	}

	@FXML
	protected void setS_MIN2() {
		System.out.println("S_MIN");
		HandDetect.setS_MIN2((int) S_MIN2.getValue());
	}

	@FXML
	protected void setS_MAX2() {
		System.out.println("S_MAX");
		HandDetect.setS_MAX2((int) S_MAX2.getValue());
	}

	@FXML
	protected void setV_MIN2() {
		System.out.println("V_MIN");
		HandDetect.setV_MIN2((int) V_MIN2.getValue());
	}

	@FXML
	protected void setV_MAX2() {
		System.out.println("V_MAX");
		HandDetect.setV_MAX2((int) V_MAX2.getValue());
	}
	
	public void saveHSVData(File file) {
		try {
			JAXBContext context = JAXBContext.newInstance(HSVDataWrapper.class);
			Marshaller marshaller = context.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			
			// Marshalling and saving XML to the file.
			marshaller.marshal(hsvDataWrapper, file);
			
			System.out.println("sava data");
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		}
	}
	
	public void loadHSVData(File file) {
		try {
			JAXBContext context = JAXBContext.newInstance(HSVDataWrapper.class);
			Unmarshaller um = context.createUnmarshaller();
			
			// Reading XML from the file and unmarshalling.
			hsvDataWrapper = (HSVDataWrapper) um.unmarshal(file);
			
			System.out.println("load successful");
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		}
	}
	
}
