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
import liuwei.ch.app.model.ColorDetect;

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

	Detect detect;
	HSVDataWrapper hsvDataWrapper;

	File colorRangeFile;

	boolean isPlay;
	
	public UIControl() {
		isPlay = false;
		detect = new Detect();

		colorRangeFile = new File("D:/Coding/Java/java/HandGesture/resources/XML/colorRange.xml");
		loadHSVData(colorRangeFile);
	}

	/**
	 * 初始化Slider的值
	 */
	private void init() {
		H_MIN1.setValue((double) ColorDetect.getH_MIN1());
		H_MIN2.setValue((double) ColorDetect.getH_MIN2());
		H_MAX1.setValue((double) ColorDetect.getH_MAX1());
		H_MAX2.setValue((double) ColorDetect.getH_MAX2());
		S_MIN1.setValue((double) ColorDetect.getS_MIN1());
		S_MIN2.setValue((double) ColorDetect.getS_MIN2());
		S_MAX1.setValue((double) ColorDetect.getS_MAX1());
		S_MAX2.setValue((double) ColorDetect.getS_MAX2());
		V_MIN1.setValue((double) ColorDetect.getV_MIN1());
		V_MIN2.setValue((double) ColorDetect.getV_MIN2());
		V_MAX1.setValue((double) ColorDetect.getV_MAX1());
		V_MAX2.setValue((double) ColorDetect.getV_MAX2());
	}
	
	/**
	 * 设置slider的值
	 */
	private void setSlider(int[] data) {
		H_MIN1.setValue((double) data[0]);
		H_MIN2.setValue((double) data[1]);
		H_MAX1.setValue((double) data[2]);
		H_MAX2.setValue((double) data[3]);
		S_MIN1.setValue((double) data[4]);
		S_MIN2.setValue((double) data[5]);
		S_MAX1.setValue((double) data[6]);
		S_MAX2.setValue((double) data[7]);
		V_MIN1.setValue((double) data[8]);
		V_MIN2.setValue((double) data[9]);
		V_MAX1.setValue((double) data[10]);
		V_MAX2.setValue((double) data[11]);
	}

	/**
	 * 开启或关闭摄像头
	 */
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
	
	/**
	 * 保存当前ColorRange的值为相应场景
	 * @throws IOException
	 */
	@FXML
	protected void saveScene() throws IOException {
		TextInputDialog inputDialog = new TextInputDialog("请输入场景名");
		inputDialog.setTitle("save scene");
		
		Optional<String> result = inputDialog.showAndWait();
		
		int[] data = {
				(int)H_MIN1.getValue(), (int)H_MAX1.getValue(),
				(int)S_MIN1.getValue(), (int)S_MAX1.getValue(),
				(int)V_MIN1.getValue(), (int)V_MAX1.getValue(),
				(int)H_MIN2.getValue(), (int)H_MAX2.getValue(),
				(int)S_MIN2.getValue(), (int)S_MAX2.getValue(),
				(int)V_MIN2.getValue(), (int)V_MAX2.getValue()
				};
		
		if (result.isPresent()) {
			HSVData hsvData = new HSVData(data);
			String sceneName = result.get();
			hsvDataWrapper.add(sceneName, hsvData);

			File file = new File("D:/Coding/Java/java/HandGesture/resources/XML/colorRange.xml");
			saveHSVData(file);
		}
	}
	
	/**
	 * 加载相应的场景到选择按钮让用户进行选择
	 */
	@FXML
	protected void loadScene() {
		sceneChoose.getItems().clear();
		List<String> scenes = hsvDataWrapper.getScenes();
		for (int i = 0; i < scenes.size(); i++) {
			sceneChoose.getItems().add(scenes.get(i));
		}
	}
	
	/**
	 * 设置当前的ColorRange值为选择按钮中的场景值
	 */
	@FXML
	protected void setScene() {
		String sceneName = sceneChoose.getValue().toString();
		HSVData sceneData = hsvDataWrapper.getHSVData(sceneName);
		
		ColorDetect.setHSVData(sceneData.getHSV());
		this.setSlider(sceneData.getHSV());
	}
	
	/**
	 * 删除相应的场景
	 */
	@FXML
	protected void deleteScene() {
		
	}
	
	/**
	 * 清除所以场景
	 */
	@FXML
	protected void clearScenes() {
		hsvDataWrapper = new HSVDataWrapper();
		saveHSVData(colorRangeFile);
	}
	
	/**
	 * 将相应的场景数据储存到XML文件进行保存
	 * @param file 起储存作业的XML文件
	 */
	public void saveHSVData(File file) {
		try {
			JAXBContext context = JAXBContext.newInstance(HSVDataWrapper.class);
			Marshaller marshaller = context.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			
			// Marshalling and saving XML to the file.
			marshaller.marshal(hsvDataWrapper, file);
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	/**
	 * 加载所有的场景数据到内存中，供以后使用
	 * @param file 相应的XML文件
	 */
	public void loadHSVData(File file) {
		try {
			JAXBContext context = JAXBContext.newInstance(HSVDataWrapper.class);
			Unmarshaller um = context.createUnmarshaller();
			
			// Reading XML from the file and unmarshalling.
			hsvDataWrapper = (HSVDataWrapper) um.unmarshal(file);
			
			System.out.println("load successful");
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	@FXML
	protected void setH_MIN1() {
		System.out.println("H_MIN");
		ColorDetect.setH_MIN1((int) H_MIN1.getValue());
	}

	@FXML
	protected void setH_MAX1() {
		System.out.println("H_MAX");
		ColorDetect.setH_MAX1((int) H_MAX1.getValue());
	}

	@FXML
	protected void setS_MIN1() {
		System.out.println("S_MIN");
		ColorDetect.setS_MIN1((int) S_MIN1.getValue());
	}

	@FXML
	protected void setS_MAX1() {
		System.out.println("S_MAX");
		ColorDetect.setS_MAX1((int) S_MAX1.getValue());
	}

	@FXML
	protected void setV_MIN1() {
		System.out.println("V_MIN");
		ColorDetect.setV_MIN1((int) V_MIN1.getValue());
	}

	@FXML
	protected void setV_MAX1() {
		System.out.println("V_MAX");
		ColorDetect.setV_MAX1((int) V_MAX1.getValue());
	}
	
	@FXML
	protected void setH_MIN2() {
		System.out.println("H_MIN");
		ColorDetect.setH_MIN2((int) H_MIN2.getValue());
	}

	@FXML
	protected void setH_MAX2() {
		System.out.println("H_MAX");
		ColorDetect.setH_MAX2((int) H_MAX2.getValue());
	}

	@FXML
	protected void setS_MIN2() {
		System.out.println("S_MIN");
		ColorDetect.setS_MIN2((int) S_MIN2.getValue());
	}

	@FXML
	protected void setS_MAX2() {
		System.out.println("S_MAX");
		ColorDetect.setS_MAX2((int) S_MAX2.getValue());
	}

	@FXML
	protected void setV_MIN2() {
		System.out.println("V_MIN");
		ColorDetect.setV_MIN2((int) V_MIN2.getValue());
	}

	@FXML
	protected void setV_MAX2() {
		System.out.println("V_MAX");
		ColorDetect.setV_MAX2((int) V_MAX2.getValue());
	}
	
}
