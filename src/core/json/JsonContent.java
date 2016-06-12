package core.json;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonContent {

	private Map<String, Object> content;
	
	public JsonContent() {
		this.content = new HashMap<>();
	}
	
	@SuppressWarnings("unchecked")
	public JsonContent(String json) {
		this.content = new Gson().fromJson(json, Map.class);
	}

	@SuppressWarnings("unchecked")
	public JsonContent(JsonFile jsonFile) throws IOException {
		byte[] fileContent = Files.readAllBytes(jsonFile.getPath());
		this.content = new Gson().fromJson(new String(fileContent), Map.class);
	}

	public Boolean getAsBoolean(String key, Boolean defaultValue) {
		Boolean ret = (Boolean) this.content.get(key);
		return ret != null ? ret : defaultValue; 
	}
	
	public Integer getAsInteger(String key, Integer defaultValue) {
		Integer ret = ((Double) this.content.get(key)).intValue();
		return ret != null ? ret : defaultValue; 
	}
	
	public Double getAsDouble(String key, Double defaultValue) {
		Double ret = (Double) this.content.get(key);
		return ret != null ? ret : defaultValue; 
	}
	
	public String getAsString(String key, String defaultValue) {
		String ret = (String) this.content.get(key);
		return ret != null ? ret : defaultValue; 
	}
	
	public List<?> getAsArray(String key, List<?> defaultValue) {
		List<?> ret = (List<?>) this.content.get(key);
		return ret != null ? ret : defaultValue; 
	}
	
	public JsonContent getAsObject(String key, JsonContent defaultValue) {
		Map<?, ?> ret = (Map<?, ?>) this.content.get(key);
		return ret != null ? new InnerJsonContent(ret) : defaultValue; 
	}
	
	public void put(String key, Object value) {
		this.content.put(key, value);
	}
	
	public void remove(String key) {
		this.content.remove(key);
	}
	
	public Map<String, Object> getJsonMap() {
		return Collections.unmodifiableMap(this.content);
	}

	public byte[] toByteArray() {
		Gson gson = new GsonBuilder()
			.setPrettyPrinting()
			.excludeFieldsWithoutExposeAnnotation()
			.registerTypeHierarchyAdapter(JsonContent.class, new JsonContentSerializer())
			.create();
		return gson.toJson(this.content).getBytes();
	}
	
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		
		if (o instanceof JsonContent) {
			JsonContent jc = (JsonContent) o;
			return this.content.equals(jc.content);
		}
		return false;
	}
	
	private class InnerJsonContent extends JsonContent {

		private Map<String, Object> originalMap;
		
		@SuppressWarnings("unchecked")
		private InnerJsonContent(Map<?, ?> map) {
			super();
			this.originalMap = (Map<String, Object>) map;
			for (Entry<?, ?> e : map.entrySet()) {
				this.put((String) e.getKey(), e.getValue());
			}
		}
		
		public void put(String key, Object value) {
			super.put(key, value);
			this.originalMap.put(key, value);
		}
		
		public void remove(String key) {
			super.remove(key);
			this.originalMap.remove(key);
		}
		
	}

}
