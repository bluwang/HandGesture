
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
	private HandDetect handDetect;
	
	private VideoCapture capture;
	private Mat backgroundFrame;
	private Mat currentFrame;
	private Mat bSMat;
	private BackgroundSubtractor subtractor;
	private BackgroundSubtractorMOG2 MOG2;
	private BackgroundSubtractorMOG MOG;
	private boolean isFirstFrame;
	private boolean cameraActive;
	private Image currentImage;
	private int width = 400;
	private int height = 400;
	private CascadeClassifier faceDetector;
	private MatOfRect faceDetections;
	// Set video device
	private static int videodevice = 0;
	
	public Detect() {
		faceDetect = new FaceDetect();
		handDetect = new HandDetect();
//		faceDetector = new CascadeClassifier("C:/Program Files/openCV/openCV2.4.9/opencv/sources/data/lbpcascades/lbpcascade_frontalface.xml");
//		faceDetector = new CascadeClassifier("C:/Program Files/openCV/openCV-2.4.13/opencv/sources/data/lbpcascades/lbpcascade_frontalcatface.xml");
		faceDetector = new CascadeClassifier("C:/Program Files/openCV/openCV-2.4.13/opencv/sources/data/lbpcascades/lbpcascade_frontalface.xml");
//		faceDetector = new CascadeClassifier("C:/Program Files/openCV/openCV-2.4.13/opencv/sources/data/lbpcascades/lbpcascade_profileface.xml");
//		faceDetector = new CascadeClassifier("C:/Program Files/openCV/openCV-2.4.13/opencv/sources/data/lbpcascades/lbpcascade_silverware.xml");
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
					backgroundFrame = new Mat();
					currentFrame = new Mat();
					bSMat = new Mat();
					MOG = new BackgroundSubtractorMOG();
					MOG2 = new BackgroundSubtractorMOG2();
					Thread processFrame = new Thread(new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							while (cameraActive) {
								capture.read(currentFrame);
//								Imgproc.blur(currentFrame, currentFrame, new Size(3, 3));

								//»À¡≥ºÏ≤‚
//								faceDetect(currentFrame);
								
								//—’…´∆•≈‰ºÏ≤‚
//								bSMat = handDetect.detect(currentFrame);
								
								//‘À∂ØºÏ≤‚
//								MOG.apply(currentFrame, bSMat);
//								MOG2.apply(currentFrame, bSMat);
								bSMat = handDetect.sub(currentFrame);

								Core.flip(currentFrame, currentFrame, 1);
								Core.flip(bSMat, bSMat, 1);
								Imgproc.resize(currentFrame, currentFrame, new Size(350, 300));
								Imgproc.resize(bSMat, bSMat, new Size(350, 300));
								
								//ª≠¬÷¿™
//								List<MatOfPoint> contours = new ArrayList<MatOfPoint>();   
//								Imgproc.threshold(bSMat, bSMat, 100, 255, Imgproc.THRESH_BINARY);
//								Imgproc.findContours(bSMat, contours, new Mat(), Imgproc.RETR_LIST,Imgproc.CHAIN_APPROX_SIMPLE);
//								if (contours.size() > 0) {
//									Rect r = Imgproc.boundingRect(contours.get(0));
//									for (int i = 1; i < contours.size(); i++) {
////										if (r.area() < Imgproc.boundingRect(contours.get(i)).area()) {
////											r = Imgproc.boundingRect(contours.get(i));
////										}
//										r = Imgproc.boundingRect(contours.get(i));
//										Core.rectangle(bSMat, r.tl(), r.br(), new Scalar(255, 0, 0, 255), 3);
//									}
////									Imgproc.rectangle(bSMat, r.tl(), r.br(), new Scalar(255, 0, 0, 255), 3);
//								}

//								if (isFirstFrame) {
//									currentFrame.copyTo(backgroundFrame);
//									isFirstFrame = false;
//								}
//								else {
//									KNN.apply(currentFrame, bSMat);
//									Imgproc.dilate(bSMat, bSMat, new Mat());
//									Imgproc.erode(bSMat, bSMat, new Mat());
									
//									Imgproc.cvtColor(currentFrame, bSMat, Imgproc.COLOR_BGR2HSV);
//									Core.inRange(bSMat, new Scalar(0, 76, 0, 255), new Scalar(15, 118, 255, 255), bSMat);
									
//									List<MatOfPoint> contours = new ArrayList<MatOfPoint>();   
//									Imgproc.threshold(bSMat, bSMat, 100, 255, Imgproc.THRESH_BINARY);
//									Imgproc.findContours(bSMat, contours, new Mat(), Imgproc.RETR_LIST,Imgproc.CHAIN_APPROX_SIMPLE);
//									Rect r = null;
//									Rect r = Imgproc.boundingRect(contours.get(0));
//									for (int i = 1; i < contours.size(); i++) {
//										if (r.area() < Imgproc.boundingRect(contours.get(i)).area()) {
//											r = Imgproc.boundingRect(contours.get(i));
//										}
//									}
//									Imgproc.rectangle(bSMat, r.tl(), r.br(), new Scalar(255, 0, 0, 255), 3);
//									for (int i = 0; i < contours.size(); i++) {
//										r = Imgproc.boundingRect(contours.get(i));
//										// if bounding rect larger than min area, draw rect on screen
////										if(r.area()>1200) {
//											// we have motion!
//											Imgproc.rectangle(bSMat, r.tl(), r.br(), new Scalar(255, 0, 0, 255), 3);
////										}
//									}
//									Imgproc.drawContours(bSMat, contours, 0, new Scalar(255,0,255));
//									Core.absdiff(backgroundFrame, currentFrame, bSMat);
//									Imgproc.cvtColor(bSMat, bSMat, Imgproc.COLOR_RGB2GRAY);
//									Imgproc.blur(bSMat, bSMat, new Size(3, 3));
//									Imgproc.dilate(bSMat, bSMat, new Mat());
//									Imgproc.erode(bSMat, bSMat, new Mat());
//									List<MatOfPoint> contours = new ArrayList<MatOfPoint>();   
//									Imgproc.adaptiveThreshold(bSMat, bSMat, 255,Imgproc.ADAPTIVE_THRESH_MEAN_C, Imgproc.THRESH_BINARY,7, 5);
//									Imgproc.findContours(bSMat, contours, new Mat(), Imgproc.RETR_LIST,Imgproc.CHAIN_APPROX_SIMPLE);
//									Imgproc.drawContours(bSMat, contours, 0, new Scalar(255,0,255));
//									for(int i=0; i< contours.size();i++){
//										System.out.println(Imgproc.contourArea(contours.get(i)));
//										if (Imgproc.contourArea(contours.get(i)) > 50 ){
//											Rect rect = Imgproc.boundingRect(contours.get(i));
//											System.out.println(rect.height);
//											if (rect.height > 28){
//												//System.out.println(rect.x +","+rect.y+","+rect.height+","+rect.width);
//												Imgproc.rectangle(bSMat, new Point(rect.x,rect.height), new Point(rect.y,rect.width),new Scalar(255,0,255));
//											}
//										}
//									}

									currentImage = MyTool.MatToImage(bSMat);
									Platform.runLater(() -> {
										imageView.setImage(currentImage);
										imageView.setFitWidth(width);
										imageView.setFitHeight(height);
									});
								}
//								bSMat = handDetect.detect(currentFrame);
//								KNN.apply(currentFrame, bSMat);
//								KNN.setDetectShadows(true);
//								KNN.setDist2Threshold(3000.0);
//    							KNN.setHistory(1);
//    							KNN.setkNNSamples(-1);
//    							KNN.setNSamples(1);
//								Imgproc.cvtColor(bSMat, bSMat, Imgproc.COLOR_GRAY2RGB);
//							}
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