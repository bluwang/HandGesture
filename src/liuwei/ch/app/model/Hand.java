
package liuwei.ch.app.model;

import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.opencv.video.BackgroundSubtractor;
import org.opencv.video.BackgroundSubtractorKNN;
import org.opencv.video.BackgroundSubtractorMOG2;
import org.opencv.video.Video;
import org.opencv.videoio.VideoCapture;

import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import liuwei.ch.app.util.MyTool;

public class Hand {
	
	private HandDetect handDetect;
	
	private VideoCapture capture;
	private Mat backgroundFrame;
	private Mat currentFrame;
	private Mat bSMat;
	private BackgroundSubtractor subtractor;
	private BackgroundSubtractorKNN KNN;
	private BackgroundSubtractorMOG2 MOG2;
	private boolean isFirstFrame;
	private boolean cameraActive;
	private Image currentImage;
	private int width = 400;
	private int height = 400;
	// Set video device
	private static int videodevice = 0;
	
	public Hand() {
		handDetect = new HandDetect();
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
					KNN = Video.createBackgroundSubtractorKNN();
					KNN.setDetectShadows(true);
					Thread processFrame = new Thread(new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							while (cameraActive) {
								capture.read(currentFrame);
								Core.flip(currentFrame, currentFrame, 1);
								Imgproc.resize(currentFrame, currentFrame, new Size(imageView.getFitWidth(), imageView.getFitHeight()));
								Imgproc.blur(currentFrame, currentFrame, new Size(2, 2));
								bSMat = handDetect.detect(currentFrame);
								
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

}