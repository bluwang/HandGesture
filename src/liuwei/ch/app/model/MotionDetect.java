package liuwei.ch.app.model;

import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.opencv.video.BackgroundSubtractorMOG;
import org.opencv.video.BackgroundSubtractorMOG2;

import liuwei.ch.app.util.MyTool;

/**
 * 运动检测类：
 * 获取图片中的运动目标
 * @author Administrator
 *
 */
public class MotionDetect {

	private BackgroundSubtractorMOG MOG;
	private BackgroundSubtractorMOG2 MOG2;
	private Mat current;
	private Mat previous;
	private Mat foreground;
	private List<MatOfPoint> contours;
	
	private String detectMethod;
	
	public MotionDetect() {
		MOG = new BackgroundSubtractorMOG();
		MOG2 = new BackgroundSubtractorMOG2(1, 0);
		current = new Mat();
		previous = null;
		foreground = new Mat();
		contours = new ArrayList<MatOfPoint>();
		
		detectMethod = "MyBS";
	}
	
	public void setDetectMethod(String method) {
		detectMethod = method;
	}
	
	/**
	 * 对图像中的运动目标进行检测，获得其轮廓的矩形框
	 * @param image 输入图像
	 * @return 返回运动目标轮廓的矩形框
	 */
	public List<MatOfPoint> detect(Mat image) {
		switch (detectMethod) {
		case "MOG":
			detect_MOG(image);
			break;

		case "MOG2":
			detect_MOG2(image);
			break;

		default:
			detect_myBS(image);
			break;
		}

		return contours;
//		return null;
	}
	
	private void detect_MOG(Mat image) {
		contours.clear();
		MOG.apply(image, image);
		Mat element = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(3, 3));
		Imgproc.dilate(image, image, element, new Point(-1, -1), 5);
		Imgproc.erode(image, image, element, new Point(-1, -1), 5);
		contours = MyTool.getContours(image);
	}

	private void detect_MOG2(Mat image) {
		contours.clear();
		MOG2.apply(image, image);
		Mat element = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(2, 2));
		Imgproc.dilate(image, image, element, new Point(-1, -1), 5);
		Imgproc.erode(image, image, element, new Point(-1, -1), 5);
		contours = MyTool.getContours(image);
	}
	
	private void detect_myBS(Mat image) {
		if (previous == null) {
			previous = new Mat();
			image.copyTo(previous);
			Imgproc.cvtColor(previous, previous, Imgproc.COLOR_BGR2GRAY);
			Imgproc.GaussianBlur(previous, previous, new Size(21,21), 0);
		}
		else {
			image.copyTo(current);

			Imgproc.cvtColor(current, current, Imgproc.COLOR_BGR2GRAY);
			Imgproc.GaussianBlur(current, current, new Size(21,21), 0);
			
			Core.absdiff(current, previous, foreground);
			Imgproc.threshold(foreground, foreground, 25, 255, Imgproc.THRESH_BINARY);
			
			Mat element = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(2, 2));
			Imgproc.dilate(foreground, foreground, element, new Point(-1, -1), 5);
			Imgproc.erode(foreground, foreground, element, new Point(-1, -1), 5);
			
			contours = MyTool.getContours(foreground);
			
			image.copyTo(previous);
			Imgproc.cvtColor(previous, previous, Imgproc.COLOR_BGR2GRAY);
			Imgproc.GaussianBlur(previous, previous, new Size(21,21), 0);
		}

	
	}
	
}
