package core.translation;

import java.nio.file.Path;

import core.ContentKey;
import core.cache.Cache;
import core.cache.LruCache;

public class TranslateManager {
	
	private static final String TRANSLATION_ID_START = "t$";
	
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
		
		ContentKey ck = new ContentKey(translationId);
		Translator translator = this.getTranslator(ck);
		
		return translator.translate(ck.getContentId());
	}

	private Translator getTranslator(ContentKey ck) {
		if (!this.cache.containsKey(ck.getFileId())) {
			this.loadTranslator(ck);
		}
		return this.cache.get(ck.getFileId());
	}

	private void loadTranslator(ContentKey ck) {
		Translator translator = new Translator(this.root, this.lang, ck.getFileIdAsPath());
		this.cache.add(ck.getFileId(), translator);
	}

}
