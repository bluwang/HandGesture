package liuwei.ch.app.model;

import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

public class HandDetect {
	
	private static int H_MIN1 = 0; 
	private static int H_MAX1 = 40; 
	private static int S_MIN1 = 30; 
	private static int S_MAX1 = 170; 
	private static int V_MIN1 = 30; 
	private static int V_MAX1 = 256; 
	private static int H_MIN2 = 156; 
	private static int H_MAX2 = 180; 
	private static int S_MIN2 = 30; 
	private static int S_MAX2 = 170; 
	private static int V_MIN2 = 30; 
	private static int V_MAX2 = 256; 
	
	private boolean isFirstMat;
	private Mat backgroundMat;
	private Mat resultMat;
	private int no;

	public HandDetect() {
		isFirstMat = true;
		backgroundMat = new Mat();
		resultMat = new Mat();
		no = 1;
	}
	
	public Mat detect(Mat inputMat) {
		Mat mat1 = new Mat();
		Mat mat2 = new Mat();
		Mat resultMat = new Mat();
//		Imgproc.medianBlur(inputMat, inputMat, 5);
		Imgproc.cvtColor(inputMat, resultMat, Imgproc.COLOR_BGR2HSV);
//		Core.inRange(resultMat, new Scalar(H_MIN, S_MIN, V_MIN, 255), new Scalar(H_MAX, S_MAX, V_MAX, 255), resultMat);
		Core.inRange(resultMat, new Scalar(H_MIN1, S_MIN1, V_MIN1), new Scalar(H_MAX1, S_MAX1, V_MAX1), mat1);
		Core.inRange(resultMat, new Scalar(H_MIN2, S_MIN2, V_MIN2), new Scalar(H_MAX2, S_MAX2, V_MAX2), mat2);
		Core.bitwise_or(mat1, mat2, resultMat);
		
		return resultMat;
	}
	
	public Mat sub(Mat inputMat) {
		System.out.println(no);
		++no;

		if (isFirstMat) {
			inputMat.copyTo(backgroundMat);
			resultMat = backgroundMat;
			isFirstMat = false;
		}
		else {
			Core.subtract(inputMat, backgroundMat, resultMat);
			Core.bitwise_not(resultMat, resultMat);
		}
		return resultMat;
	}

	public static int getH_MIN1() {
		return H_MIN1;
	}

	public static void setH_MIN1(int h_MIN1) {
		H_MIN1 = h_MIN1;
	}

	public static int getH_MAX1() {
		return H_MAX1;
	}

	public static void setH_MAX1(int h_MAX1) {
		H_MAX1 = h_MAX1;
	}

	public static int getS_MIN1() {
		return S_MIN1;
	}

	public static void setS_MIN1(int s_MIN1) {
		S_MIN1 = s_MIN1;
	}

	public static int getS_MAX1() {
		return S_MAX1;
	}

	public static void setS_MAX1(int s_MAX1) {
		S_MAX1 = s_MAX1;
	}

	public static int getV_MIN1() {
		return V_MIN1;
	}

	public static void setV_MIN1(int v_MIN1) {
		V_MIN1 = v_MIN1;
	}

	public static int getV_MAX1() {
		return V_MAX1;
	}

	public static void setV_MAX1(int v_MAX1) {
		V_MAX1 = v_MAX1;
	}

	public static int getH_MIN2() {
		return H_MIN2;
	}

	public static void setH_MIN2(int h_MIN2) {
		H_MIN2 = h_MIN2;
	}

	public static int getH_MAX2() {
		return H_MAX2;
	}

	public static void setH_MAX2(int h_MAX2) {
		H_MAX2 = h_MAX2;
	}

	public static int getS_MIN2() {
		return S_MIN2;
	}

	public static void setS_MIN2(int s_MIN2) {
		S_MIN2 = s_MIN2;
	}

	public static int getS_MAX2() {
		return S_MAX2;
	}

	public static void setS_MAX2(int s_MAX2) {
		S_MAX2 = s_MAX2;
	}

	public static int getV_MIN2() {
		return V_MIN2;
	}

	public static void setV_MIN2(int v_MIN2) {
		V_MIN2 = v_MIN2;
	}

	public static int getV_MAX2() {
		return V_MAX2;
	}

	public static void setV_MAX2(int v_MAX2) {
		V_MAX2 = v_MAX2;
	}

}
