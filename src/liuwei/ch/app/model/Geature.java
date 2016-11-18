package liuwei.ch.app.model;

import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

public class Geature {
	private Point origin;
	private Point lastest;
	private Mat dstHist;
	
	private List<Point> track;
	
	private boolean isStart;
	private int[] channels;
	private float[] histRange;
	
	public Geature() {
		track = new ArrayList<Point>();
		isStart = false;
		channels = new int[]{0, 1};
		dstHist = new Mat();
		histRange = new float[]{0, 255};
	}
	
	public void detect(Point current) {
		if (track.size() < 10) {
			track.add(current);
		}
		else {
			if ((track.get(0).x - track.get(track.size()-1).x) > 0) {
				System.out.println("left");
			}
			else {
				System.out.println("right");
			}
			
			track.clear();
		}
	}
	
	public void track(Mat image, Rect rect) {
		Imgproc.cvtColor(image, image, Imgproc.COLOR_RGB2HSV);
	}

}
