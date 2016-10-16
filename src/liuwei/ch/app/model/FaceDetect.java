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
	
	public FaceDetect() {
		// TODO Auto-generated constructor stub
	}
	
	public static void run(){
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		System.loadLibrary("opencv_java249");
//		CascadeClassifier faceDetector = new CascadeClassifier("./lbpcascade_frontalcatface.xml");
		CascadeClassifier faceDetector = new CascadeClassifier("C:/Program Files/openCV/openCV2.4.9/opencv/sources/data/lbpcascades/lbpcascade_frontalface.xml");
//		CascadeClassifier faceDetector = new CascadeClassifier("./lbpcascade_profileface.xml");
//		CascadeClassifier faceDetector = new CascadeClassifier("./lbpcascade_silverware.xml");
		Mat image = Highgui.imread("D:/Coding/Java/java/HandGesture/src/liuwei/ch/app/model/test.jpg");
		MatOfRect faceDetections = new MatOfRect();
		faceDetector.detectMultiScale(image, faceDetections);
		
		System.out.println(String.format("Detected %s faces",
				faceDetections.toArray().length));
		for (Rect rect : faceDetections.toArray()) {
			Core.rectangle(image, new Point(rect.x, rect.y), new Point(rect.x
					+ rect.width, rect.y + rect.height), new Scalar(0, 255, 0));
		}
		
		String filename = "./FaceDetect.png";
		System.out.println(String.format("Writing %s", filename));
		Highgui.imwrite(filename, image);
	}
	
	public static void main(String[] args) {
		run();
	}

}
