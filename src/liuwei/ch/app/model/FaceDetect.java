package liuwei.ch.app.model;

import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Rect;
import org.opencv.objdetect.CascadeClassifier;

/**
 * 对人的脸部进行检测
 * @author Administrator
 *
 */
public class FaceDetect {
	
	private CascadeClassifier faceDetector;	
	private MatOfRect faceDetections;

	private List<Rect> rects;
	
	public FaceDetect() {
		rects = new ArrayList<Rect>();
		faceDetector = new CascadeClassifier("C:/Program Files/openCV/openCV-2.4.13/opencv/sources/data/lbpcascades/lbpcascade_frontalface.xml");
	}
	
	/**
	 * 对图像中的人脸进行检测
	 * @param image 输入的图像
	 * @return 返回检测到的人脸的矩形框
	 */
	public List<Rect> detect(Mat image){
		//清除之前的人脸矩形框
		rects.clear();

		faceDetections = new MatOfRect();
		faceDetector.detectMultiScale(image, faceDetections);
		
		for (Rect rect : faceDetections.toArray()) {
			if (rect.area() > 1000) {
				rects.add(rect);
			}
		}
		
		return rects;
	}
	
}
