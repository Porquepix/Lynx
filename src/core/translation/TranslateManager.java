package core.translation;

import java.util.ArrayList;
import java.util.List;

import core.cache.Cache;
import core.cache.LruCache;
import core.key.ContentKey;
import core.key.FileKey;

public class TranslateManager {
	
	private static final String TRANSLATION_ID_START = "t$";
	
	private FileKey root;
	private String lang;
	private Cache<FileKey, Translator> cache;
	
	public TranslateManager(FileKey root, String lang) {
		this.root = root;
		this.lang = lang;
		this.cache = new LruCache<>(10);
	}
	
	public String getLang() {
		return this.lang;
	}
	
	public FileKey getRoot() {
		return this.root;
	}
	
	public String translate(String s) {
		if (s.startsWith(TRANSLATION_ID_START)) {
			return translateWithTranslator(s);
		} else {
			return s;					
		}
	}
	
	public List<String> translateAll(List<String> sl) {
	    List<String> ret = new ArrayList<>(sl.size());
	    for (String s : sl) {
	    	ret.add(this.translate(s));
	    }
	    return ret;
    }

	private String translateWithTranslator(String s) {
		String translationId = s.substring(TRANSLATION_ID_START.length());
		
		ContentKey ck = new ContentKey(translationId);
		Translator translator = this.getTranslator(ck);
		
		return translator.translate(ck.getContentId());
	}

	private Translator getTranslator(ContentKey ck) {
		if (!this.cache.containsKey(ck.getFileKey())) {
			this.loadTranslator(ck);
		}
		return this.cache.get(ck.getFileKey());
	}

	private void loadTranslator(ContentKey ck) {
		Translator translator = new Translator(this.root, this.lang, ck.getFileKey());
		this.cache.add(ck.getFileKey(), translator);
	}

	public static String getValidLanguage(List<String> availiableLangs, String... attemptLangs) {
	    int i = 0;
	    boolean found = false;
	    while (i < attemptLangs.length && !found) {
	    	if (availiableLangs.contains(attemptLangs[i])) {
	    		found = true;
	    	} else {
	    		i++;
	    	}
	    }
	    return found ? attemptLangs[i] : null;
    }

}
