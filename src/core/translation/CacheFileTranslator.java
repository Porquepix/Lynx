package core.translation;

import java.util.ArrayList;
import java.util.List;

import core.cache.Cache;
import core.cache.LruCache;
import core.namespace.Namespace;
import core.namespace.Resource;

public class CacheFileTranslator implements Translator {

	 private static final Namespace TRANSLATION_DIR = new Namespace("lang");
    private static final String TRANSLATION_ID_START = "t$";

    private Namespace root;
    private String lang;
    private Cache<Namespace, Translator> cache;
    
    public CacheFileTranslator(Namespace root, String lang, int cachesize) {
	this.root = root.merge(TRANSLATION_DIR).append(lang);
	this.lang = lang;
	this.cache = new LruCache<>(cachesize);
    }

    public String getLang() {
	return this.lang;
    }

    public Namespace getRoot() {
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
	Resource res = new Resource(translationId);
	Translator translator = this.getTranslator(res.getNamespace());
	return translator.translate(res.getId());
    }

    public Translator getTranslator(Namespace name) {
	if (!this.cache.containsKey(name)) {
	    this.loadTranslator(name);
	}
	return this.cache.get(name);
    }

    private void loadTranslator(Namespace name) {
	Translator translator = new FileTranslator(this.root.merge(name));
	this.cache.add(name, translator);
    }

}