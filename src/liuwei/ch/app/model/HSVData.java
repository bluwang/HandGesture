package liuwei.ch.app.model;

/**
 * HSVÊý¾Ý
 * @author Administrator
 *
 */
public class HSVData {
	
	private int[] data;
	
	public HSVData() {
		data = new int[12];
	}
	
	public HSVData(int[] data) {
		this.data = data;
	}
	
	public int[] getHSV() {
		return data;
	}
	
	public void setHSV(int[] data) {
		this.data = data;
	}
}
