package core.json;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class JsonMapWrapper {

	private Map<String, Object> map;
	
	public JsonMapWrapper() {
		this.map = new HashMap<>();
	}
	
	public JsonMapWrapper(Map<String, Object> map) {
		this.map = map;
	}
	
	public void put(String key, Object value) {
		this.map.put(key, value);
	}
	
	public void remove(String key) {
		this.map.remove(key);
	}
	
	public Boolean getAsBoolean(String key, Boolean defaultValue) {
		Boolean ret = (Boolean) this.map.get(key);
		return ret != null ? ret : defaultValue; 
	}
	
	public Integer getAsInteger(String key, Integer defaultValue) {
		Integer ret = ((Double) this.map.get(key)).intValue();
		return ret != null ? ret : defaultValue; 
	}
	
	public Double getAsDouble(String key, Double defaultValue) {
		Double ret = (Double) this.map.get(key);
		return ret != null ? ret : defaultValue; 
	}
	
	public String getAsString(String key, String defaultValue) {
		String ret = (String) this.map.get(key);
		return ret != null ? ret : defaultValue; 
	}
	
	public List<?> getAsArray(String key, List<?> defaultValue) {
		List<?> ret = (List<?>) this.map.get(key);
		return ret != null ? ret : defaultValue; 
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> getAsObject(String key, Map<String, Object> defaultValue) {
		Map<String, Object> ret = (Map<String, Object>) this.map.get(key);
		return ret != null ? ret : defaultValue; 
	}
	
	protected Map<String, Object> getMap() {
		return this.map;
	}
	
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		
		if (o instanceof JsonMapWrapper) {
			JsonMapWrapper jmw = (JsonMapWrapper) o;
			return this.map.equals(jmw.map);
		}
		return false;
	}

	protected void fillIfNotExists(Map<String, Object> jsonMap) {
		for (Entry<String, Object> e : jsonMap.entrySet()) {
			if (!this.map.containsKey(e.getKey())) {
				this.put(e.getKey(), e.getValue());
			}
		}
	}

	public boolean isEmpty() {
		return this.map.isEmpty();
	}

}
