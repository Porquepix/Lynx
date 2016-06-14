package core.translation;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import core.Core;
import core.json.JsonContent;
import core.json.JsonFile;

public class TranslateManager {
	
	private static final String TRANSLATION_ID_START = "t$";
	private static final String ID_DELIMITER = ".";
	private static final String URI_FORMAT = "lang/%s/%s.json"; 
	
	private Path root;
	private String lang;
	private Map<String, JsonContent> cache;
	
	public TranslateManager(Path root, String lang) {
		this.root = root;
		this.lang = lang;
		this.cache = new HashMap<>();
	}
	
	public String getLang() {
		return this.lang;
	}
	
	public Path getRoot() {
		return this.root;
	}
	
	public String translate(String s) {
		if (s.startsWith(TRANSLATION_ID_START)) {
			return translateFromFile(s.substring(TRANSLATION_ID_START.length()));
		}
		return s;		
	}

	private String translateFromFile(String s) {
		int lastDelimiter = s.lastIndexOf(ID_DELIMITER);
		
		String file = s.substring(0, lastDelimiter).replace(ID_DELIMITER, "/");
		JsonContent json = null;
		if (this.cache.containsKey(file)) {
			json = this.cache.get(file);
		} else {
			Path finalPath = root.resolve(String.format(URI_FORMAT, this.lang, file));
			json = new JsonFile(finalPath).loadContent().getContent();
			this.cache.put(file, json);
		}
		
		String key = s.substring(lastDelimiter + 1);		
		return json.getAsString(key, Core.MISSING_DATA);
	}

}
