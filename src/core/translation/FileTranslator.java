package core.translation;

import java.util.ArrayList;
import java.util.List;

import core.json.controller.TranslationController;
import core.json.model.TranslationModel;
import core.namespace.Namespace;
import core.util.Strings;

public class FileTranslator implements Translator {

    private Namespace namespace;
    private TranslationModel translations;

    public FileTranslator(Namespace file) {

	this.namespace = file; 
	
	TranslationController transController = new TranslationController(
		this.namespace);
	this.translations = transController.fetch();
    }

    public String translate(String translationKey) {
	String escaped = Strings.nullToEmpty(translationKey);
	String translation = this.translations.getTranslation(escaped);
	return translation != null ? translation : escaped;
    }
    
    public List<String> translateAll(List<String> sl) {
	List<String> ret = new ArrayList<>(sl.size());
	for (String s : sl) {
	    ret.add(this.translate(s));
	}
	return ret;
    }

}
