package liuwei.ch.app.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 对颜色检测中的HSV颜色值进行储存
 * @author Administrator
 *
 */
@XmlRootElement(name = "HSVDataWrapper")
public class HSVDataWrapper {
	
	@XmlElement(name = "HSVMap")
	private Map<String, HSVData> HSVMap;
	@XmlElement(name = "sceneName")
	private Vector<String> sceneNames;
	@XmlElement(name = "lastScene")
	private String lastScene;
	
	public HSVDataWrapper() {
		HSVMap = new HashMap<String, HSVData>();
		sceneNames = new Vector<String>();
	}
	
	/**
	 * 获取相应场景的HSV颜色数据
	 * @param sceneName 相应的场景名
	 * @return HSV数据
	 */
	public HSVData getHSVData(String sceneName) {
		HSVData sceneData = null;

		for (int i = 0; i < this.HSVMap.size() ; i++) {
			if (this.sceneNames.get(i).equals(sceneName)) {
				sceneData = HSVMap.get(sceneName);
			}
		}

		return sceneData;
	}
	
	/**
	 * 获取所有的场景名
	 * @return 场景名列表
	 */
	public List<String> getScenes() {
		return sceneNames;
	}
	
	/**
	 * 添加场景
	 * @param sceneName 场景名
	 * @param data 场景HSV数据
	 */
	public void add(String sceneName, HSVData data) {
		boolean isAdd = false;

		//检查场景名是否存在
		for (int i = 0; i < this.sceneNames.size() ; i++) {
			if (this.sceneNames.get(i).equals(sceneName)) {
				this.HSVMap.remove(sceneName);
				this.HSVMap.put(sceneName, data);
				this.sceneNames.add(sceneName);
				isAdd = true;
				break;
			}
		}
		
		//不存在（既从没添加过），便添加
		if (!isAdd) {
			this.HSVMap.put(sceneName, data);
			this.sceneNames.add(sceneName);
		}
		//存在则替换相应场景的数据
		else {
		}
	}
	
	/**
	 * 删除相应场景
	 * @param sceneName 场景名
	 */
	public void remove(String sceneName) {
		for (int i = 0; i < this.sceneNames.size() ; i++) {
			if (this.sceneNames.equals(sceneName)) {
				this.HSVMap.remove(sceneName);
			}
		}
	}
	
}
