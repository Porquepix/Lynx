package core.translation;

import java.nio.file.Path;
import java.nio.file.Paths;

import core.cache.Cache;
import core.cache.LruCache;

public class TranslateManager {
	
	private static final String TRANSLATION_ID_START = "t$";
	private static final String ID_DELIMITER = ".";
	
	private Path root;
	private String lang;
	private Cache<String, Translator> cache;
	
	public TranslateManager(Path root, String lang) {
		this.root = root;
		this.lang = lang;
		this.cache = new LruCache<>(10);
	}
	
	public String getLang() {
		return this.lang;
	}
	
	public Path getRoot() {
		return this.root;
	}
	
	public String translate(String s) {
		if (s.startsWith(TRANSLATION_ID_START)) {
			return translateWithTranslator(s);
		} else {
			return s;					
		}
	}

	private String translateWithTranslator(String s) {
		String translationId = s.substring(TRANSLATION_ID_START.length());
		
		int lastDelimiter = translationId.lastIndexOf(ID_DELIMITER);
		String fileId = translationId.substring(0, lastDelimiter);
		Translator translator = this.getTranslator(fileId);
		
		return translator.translate(translationId.substring(lastDelimiter + 1));
	}

	private Translator getTranslator(String fileId) {
		if (!this.cache.containsKey(fileId)) {
			this.loadTranslator(fileId);
		}
		return this.cache.get(fileId);
	}

	private void loadTranslator(String fileId) {
		String filePath = fileId.replace(ID_DELIMITER, "/");
		Translator translator = new Translator(this.root, this.lang, Paths.get(filePath));
		this.cache.add(fileId, translator);
	}

}
