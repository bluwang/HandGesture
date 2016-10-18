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
import org.opencv.highgui.VideoCapture;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.video.BackgroundSubtractor;
import org.opencv.video.BackgroundSubtractorMOG;
import org.opencv.video.BackgroundSubtractorMOG2;
import org.opencv.video.Video;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import liuwei.ch.app.util.MyTool;

public class Detect {
	
	private FaceDetect faceDetect;
	private MotionDetect motionDetect;
	private ColorDetect colorDetect;
	
	private Mat currentFrame;
	private VideoCapture capture;
	private MatOfRect faceDetections;
	private CascadeClassifier faceDetector;

	private Image currentImage;

	private boolean isFirstFrame;
	private boolean cameraActive;
	private int width = 400;
	private int height = 400;
	private List<Rect> faceRects;
	private List<Rect> motionRects;
	private List<Rect> colorRects;
	// Set video device
	private static int videodevice = 0;
	
	public Detect() {
		//加载openCV中的这个文件（后面的很多都要用的，先加载）
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

		faceDetect = new FaceDetect();
		motionDetect = new MotionDetect();
		colorDetect = new ColorDetect();
		faceRects = new ArrayList<Rect>();
		motionRects = new ArrayList<Rect>();
		colorRects = new ArrayList<Rect>();
		isFirstFrame = true;
		cameraActive = false;
	}
	
	
	public void start(ImageView imageView) {
		try {
			if (!cameraActive) {
				System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
				capture = new VideoCapture(videodevice);
				System.out.println("open the camera");
				if (this.capture.isOpened()) {
					cameraActive = true;
					currentFrame = new Mat();
					Thread processFrame = new Thread(new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							while (cameraActive) {
								capture.read(currentFrame);
								Mat frame = new Mat();
								currentFrame.copyTo(frame);
								Imgproc.blur(frame, frame, new Size(3, 3));
								
								//人脸检测
								faceRects = faceDetect.detect(frame);

								//运动检测
								motionRects = motionDetect.detect(frame);
//								MOG.apply(currentFrame, bSMat);
//								MOG2.apply(currentFrame, bSMat);
//								bSMat = handDetect.sub(currentFrame);

								//颜色匹配检测
								colorRects = colorDetect.detect(frame);
							
								showResult(frame);
								Core.flip(frame, frame, 1);
								Imgproc.resize(frame, frame, new Size(350, 300));
								
								currentImage = MyTool.MatToImage(frame);
								Platform.runLater(() -> {
									imageView.setImage(currentImage);
									imageView.setFitWidth(width);
									imageView.setFitHeight(height);
								});
							}
						}
					});
					
					processFrame.setDaemon(true);
					processFrame.setName("processFrame");
					processFrame.start();
				}
				else {
					System.out.println("capture can't open");
				}
			}
			else {
				this.capture.release();
			}
		} 
		catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public void stopCamera() {
		if (cameraActive) {
			if (capture.isOpened()) {
				capture.release();
			}
		}
	}
	
	private void showResult(Mat frame) {
		MyTool.drawContours(frame, faceRects, "red");
		MyTool.drawContours(frame, motionRects, "green");
		MyTool.drawContours(frame, colorRects, "blue");
		
		List<Rect> mergeRects = new ArrayList<Rect>();
		Rect motionRect, colorRect;
		for (int i = 0; i < motionRects.size(); i++) {
			for (int j = 0; j < colorRects.size(); j++) {
				motionRect = motionRects.get(i);
				colorRect = colorRects.get(j);
				if (motionRect.area() >= colorRect.area()) {
					if (motionRect.x <= colorRect.x && motionRect.y <= colorRect.y) {
						mergeRects.add(motionRect);
					}
				}
				else {
					if (motionRect.x > colorRect.x && motionRect.y > colorRect.y) {
						mergeRects.add(colorRect);
					}
				}
			}
		}
		
		MyTool.drawContours(frame, mergeRects, "white");
	}
	
	public void faceDetect(Mat frame) {
		faceDetections = new MatOfRect();
		faceDetector.detectMultiScale(frame, faceDetections);
		
		System.out.println(String.format("Detected %s faces",
				faceDetections.toArray().length));
		for (Rect rect : faceDetections.toArray()) {
			Core.rectangle(frame, new Point(rect.x, rect.y), new Point(rect.x
					+ rect.width, rect.y + rect.height), new Scalar(0, 255, 0));
		}
		
		String filename = "./Result/FaceDetect.png";
		System.out.println(String.format("Writing %s", filename));
	}
	
}
