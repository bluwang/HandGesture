package liuwei.ch.app.model;

import org.opencv.core.Core;
import org.opencv.core.Core.MinMaxLocResult;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;

public class Match {
	private Mat templ;
	
	private int width;
	private int height;
	
	public Match() {
		String file = "D:\\Coding\\Java\\java\\HandGesture\\resources\\picture\\hand.png";
		templ = new Mat();
		templ = Highgui.imread(file, Highgui.CV_LOAD_IMAGE_COLOR);
		width = templ.width();
		height = templ.height();
	}	

	public Rect pictureMatch(Mat image, int matchMethod) {
		Mat result = new Mat();

		switch (matchMethod) {
		case 1:
			Imgproc.matchTemplate(image, templ, result, Imgproc.TM_SQDIFF);
			break;

		case 2:
			Imgproc.matchTemplate(image, templ, result, Imgproc.TM_SQDIFF_NORMED);
			break;

		case 3:
			Imgproc.matchTemplate(image, templ, result, Imgproc.TM_CCORR);
			break;

		case 4:
			Imgproc.matchTemplate(image, templ, result, Imgproc.TM_CCORR_NORMED);
			break;

		case 5:
			Imgproc.matchTemplate(image, templ, result, Imgproc.TM_CCOEFF);
			break;

		default:
			Imgproc.matchTemplate(image, templ, result, Imgproc.TM_CCOEFF_NORMED);
			break;
		}

		Core.normalize(result, result, 0, 1, Core.NORM_MINMAX, -1, new Mat());
		
		MinMaxLocResult data = Core.minMaxLoc(result, new Mat());
		
		Point matchLoc;
		float value;
		if (matchMethod == 1 || matchMethod == 2) {
//			System.out.println(data.minVal + "--" + data.maxVal);
			matchLoc = data.minLoc;
			value = (float) data.minVal;
		}
		else {
//			System.out.println(data.maxVal);
			matchLoc = data.maxLoc;
			value = (float) data.maxVal;
		}
		
//		Core.rectangle(image, matchLoc, new Point(matchLoc.x+width, matchLoc.y+height), new Scalar(255, 255, 255), 3);
		
		return new Rect(matchLoc, new Point(matchLoc.x+width, matchLoc.y+height));
	}

}
