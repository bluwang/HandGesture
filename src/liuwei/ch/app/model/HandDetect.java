package liuwei.ch.app.model;

import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

public class HandDetect {
	
	private static byte H_MIN = 0; 
	private static byte H_MAX = 0; 
	private static byte S_MIN = 0; 
	private static byte S_MAX = 0; 
	private static byte V_MIN = 0; 
	private static byte V_MAX = 0; 

	public HandDetect() {
	}
	
	public Mat detect(Mat inputMat) {
		Mat resultMat = new Mat();
		Imgproc.cvtColor(inputMat, resultMat, Imgproc.COLOR_BGR2HSV);
		Core.inRange(resultMat, new Scalar(H_MIN, S_MIN, V_MIN, 255), new Scalar(H_MAX, S_MAX, V_MAX, 255), resultMat);
		
		return resultMat;
	}
	
	public static byte getH_MIN() {
		return H_MIN;
	}

	public static void setH_MIN(byte h_MIN) {
		H_MIN = h_MIN;
	}

	public static byte getH_MAX() {
		return H_MAX;
	}

	public static void setH_MAX(byte h_MAX) {
		H_MAX = h_MAX;
	}

	public static byte getS_MIN() {
		return S_MIN;
	}

	public static void setS_MIN(byte s_MIN) {
		S_MIN = s_MIN;
	}

	public static byte getS_MAX() {
		return S_MAX;
	}

	public static void setS_MAX(byte s_MAX) {
		S_MAX = s_MAX;
	}

	public static byte getV_MIN() {
		return V_MIN;
	}

	public static void setV_MIN(byte v_MIN) {
		V_MIN = v_MIN;
	}

	public static byte getV_MAX() {
		return V_MAX;
	}

	public static void setV_MAX(byte v_MAX) {
		V_MAX = v_MAX;
	}

}
