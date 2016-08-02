package com.lynx.core.translation;

import java.util.ArrayList;
import java.util.List;

import com.lynx.core.cache.Cache;
import com.lynx.core.cache.LruCache;
import com.lynx.core.namespace.Namespace;
import com.lynx.core.namespace.Resource;
import com.lynx.core.util.Strings;

public class CacheFileTranslator implements Translator {

    private static final String TRANSLATION_ID_START = "t$";

    private Namespace root;
    private String lang;
    private Cache<Namespace, Translator> cache;

    public CacheFileTranslator(Namespace root, String lang, int cachesize) {
	this.root = root.append(lang);
	this.lang = lang;
	this.cache = new LruCache<>(cachesize);
    }

    public String getLang() {
	return this.lang;
    }

    public Namespace getRoot() {
	return this.root;
    }

    @Override
    public String translate(String s) {
	String escaped = Strings.nullToEmpty(s);
	if (escaped.startsWith(TRANSLATION_ID_START)) {
	    return translateWithTranslator(escaped);
	} else {
	    return escaped;
	}
    }

    @Override
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
	return this.cache.get(name).get();
    }

    private void loadTranslator(Namespace name) {
	Translator translator = new FileTranslator(root.merge(name));
	this.cache.add(name, translator);
    }

}
