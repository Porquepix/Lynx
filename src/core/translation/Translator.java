package core.translation;

import java.util.ArrayList;
import java.util.List;

import core.Core;
import core.json.controller.TranslationController;
import core.json.model.TranslationModel;
import core.namespace.Namespace;

public class Translator {

    private static final Namespace ROOT = new Namespace("lang");

    private String lang;
    private Namespace namespace;
    private TranslationModel translations;

    public Translator(Namespace root, String lang, Namespace file) {
	this.lang = lang;

	this.namespace = root.merge(ROOT).append(lang).merge(file);

	TranslationController transController = new TranslationController(
		this.namespace);
	this.translations = transController.fetch();
    }

    public String getLang() {
	return this.lang;
    }

    public String translate(String translationKey) {
	String translation = this.translations.getTranslation(translationKey);
	return translation != null ? translation : Core.MISSING_DATA;
    }
    
    public List<String> translateAll(List<String> sl) {
	List<String> ret = new ArrayList<>(sl.size());
	for (String s : sl) {
	    ret.add(this.translate(s));
	}
	return ret;
    }

}
