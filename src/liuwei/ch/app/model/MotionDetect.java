package liuwei.ch.app.model;

import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.video.BackgroundSubtractorMOG;
import org.opencv.video.BackgroundSubtractorMOG2;

import liuwei.ch.app.util.MyTool;

public class MotionDetect {

	private BackgroundSubtractorMOG2 MOG2;
	private BackgroundSubtractorMOG MOG;
	private Mat bsMat;
	private List<Rect> rects;
	
	public MotionDetect() {
		MOG = new BackgroundSubtractorMOG();
		MOG2 = new BackgroundSubtractorMOG2();
		bsMat = new Mat();
		rects = new ArrayList<Rect>();
	}
	
	public List<Rect> detect(Mat image) {
		rects.clear();
		MOG.apply(image, bsMat);
		rects = MyTool.getContours(bsMat);

		return rects;
	}
}
