package liuwei.ch.app.model;

import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.objdetect.Objdetect;

public class FaceDetect {
	
	private CascadeClassifier faceDetector;	
	private MatOfRect faceDetections;

	private List<Rect> rects;
	
	public FaceDetect() {
		rects = new ArrayList<Rect>();
		faceDetector = new CascadeClassifier("C:/Program Files/openCV/openCV-2.4.13/opencv/sources/data/lbpcascades/lbpcascade_frontalface.xml");
	}
	
	public List<Rect> detect(Mat image){
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
