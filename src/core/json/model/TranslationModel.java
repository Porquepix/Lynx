package core.json.model;

import java.util.Map;

public class TranslationModel {

    private Map<String, String> translations;

    public TranslationModel(Map<String, String> translations) {
	this.translations = translations;
    }

    public String getTranslation(String key) {
	return this.translations.get(key);
    }

}
