package core.translation;

import java.util.ArrayList;
import java.util.List;

import core.cache.Cache;
import core.cache.LruCache;
import core.namespace.Namespace;
import core.namespace.Ressource;

public class TranslateManager {

    private static final String TRANSLATION_ID_START = "t$";

    private Namespace root;
    private String lang;
    private Cache<Namespace, Translator> cache;

    public TranslateManager(Namespace root, String lang) {
	this.root = root;
	this.lang = lang;
	this.cache = new LruCache<>(10);
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

	Ressource res = new Ressource(translationId);
	Translator translator = this.getTranslator(res);

	return translator.translate(res.getId());
    }

    private Translator getTranslator(Ressource res) {
	if (!this.cache.containsKey(res.getNamespace())) {
	    this.loadTranslator(res);
	}
	return this.cache.get(res.getNamespace());
    }

    private void loadTranslator(Ressource res) {
	Translator translator = new Translator(this.root, this.lang,
		res.getNamespace());
	this.cache.add(res.getNamespace(), translator);
    }

    public static String getValidLanguage(List<String> availiableLangs,
	    String... attemptLangs) {
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
