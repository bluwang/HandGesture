package liuwei.ch.app.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.opencv.core.Rect;

public class MotionRects {
	private List<Rect> motionRects;
	private Vector<Integer> motionIndex;
	
	public MotionRects() {
		motionRects = new ArrayList<>();
		motionIndex = new Vector<Integer>();
	}
	
	public void clear() {
		motionRects.clear();
		motionIndex.clear();
	}
	
	public void add(Rect rect, int index) {
		motionRects.add(rect);
		motionIndex.add(index);
	}
	
	public Rect getRect(int i) {
		return motionRects.get(i);
	}

	public int getIndex(int i) {
		return motionIndex.get(i);
	}
	
	public int size() {
		return motionRects.size();
	}

}
