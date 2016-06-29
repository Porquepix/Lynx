package core.json;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonContent {
	
	private JsonMapWrapper content;
	
	public JsonContent() {
		this.content = new JsonMapWrapper();
	}
	
	@SuppressWarnings("unchecked")
	public JsonContent(String json) {
		Map<String, Object> jsonMap = new Gson().fromJson(json, Map.class);
		this.content = new JsonMapWrapper(jsonMap);
	}

	@SuppressWarnings("unchecked")
	public JsonContent(JsonFile jsonFile) throws IOException {
		byte[] fileContent = Files.readAllBytes(jsonFile.getPath());
		String fileContentAsString = new String(fileContent, StandardCharsets.UTF_8);
		Map<String, Object> jsonMap = new Gson().fromJson(fileContentAsString, Map.class);
		this.content = new JsonMapWrapper(jsonMap);
	}
	
	private JsonContent(Map<String, Object> jsonMap) {
		this.content = new JsonMapWrapper(jsonMap);
	}

	public Boolean getAsBoolean(String key, Boolean defaultValue) {
		return this.content.getAsBoolean(key, defaultValue);
	}
	
	public Integer getAsInteger(String key, Integer defaultValue) {
		return this.content.getAsInteger(key, defaultValue);
	}
	
	public Double getAsDouble(String key, Double defaultValue) {
		return this.content.getAsDouble(key, defaultValue);
	}
	
	public String getAsString(String key, String defaultValue) {
		return this.content.getAsString(key, defaultValue);
	}
	
	public List<?> getAsArray(String key, List<?> defaultValue) {
		return this.content.getAsArray(key, defaultValue);
	}
	
	public JsonContent getAsObject(String key, JsonContent defaultValue) {	
		if (defaultValue == null) {
			return new JsonContent(this.content.getAsObject(key, null));	
		} else {
			return new JsonContent(this.content.getAsObject(key, defaultValue.getJsonMap()));			
		}
	}
	
	public JsonContent safeGetAsObject(String key, JsonContent defaultValue) {
		JsonContent content = this.getAsObject(key, defaultValue);
		content.fillIfNotExists(defaultValue);
		return content;
	}
	
	private void fillIfNotExists(JsonContent defaultValue) {
		this.content.fillIfNotExists(defaultValue.getJsonMap());
	}

	public void put(String key, Object value) {
		this.content.put(key, value);
	}
	
	public void remove(String key) {
		this.content.remove(key);
	}
	
	public Map<String, Object> getJsonMap() {
		return this.content.getMap();
	}

	public byte[] toByteArray() {
		return this.toString().getBytes();
	}
	
	public String toString() {
		Gson gson = new GsonBuilder()
		.setPrettyPrinting()
		.excludeFieldsWithoutExposeAnnotation()
		.registerTypeHierarchyAdapter(JsonContent.class, new JsonContentSerializer())
		.create();
		return gson.toJson(this); 
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

	public boolean isEmpty() {
		return this.content.isEmpty();
	}

}
