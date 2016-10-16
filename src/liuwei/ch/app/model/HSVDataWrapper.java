package liuwei.ch.app.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

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
	
	public HSVData getHSVData(String sceneName) {
		HSVData sceneData = null;

		for (int i = 0; i < this.HSVMap.size() ; i++) {
			if (this.sceneNames.get(i).equals(sceneName)) {
				sceneData = HSVMap.get(sceneName);
			}
		}

		return sceneData;
	}
	
	public List<String> getScenes() {
		return sceneNames;
	}
	
	public void add(String sceneName, HSVData data) {
		boolean isAdd = false;
		for (int i = 0; i < this.sceneNames.size() ; i++) {
			if (this.sceneNames.get(i).equals(sceneName)) {
				this.HSVMap.remove(sceneName);
				this.HSVMap.put(sceneName, data);
				this.sceneNames.add(sceneName);
				isAdd = true;
				break;
			}
		}
		if (!isAdd) {
			this.HSVMap.put(sceneName, data);
			this.sceneNames.add(sceneName);
		}
	}
	
	public void remove(String sceneName) {
		for (int i = 0; i < this.sceneNames.size() ; i++) {
			if (this.sceneNames.equals(sceneName)) {
				this.HSVMap.remove(sceneName);
			}
		}
	}
	
}
