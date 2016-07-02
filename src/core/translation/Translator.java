package core.translation;

import core.Core;
import core.json.controller.TranslationController;
import core.json.model.TranslationModel;
import core.key.FileKey;

public class Translator {

	private String lang;
	private FileKey translationsFile;
	private TranslationModel translations;
	
	public Translator(FileKey root, String lang, FileKey file) {
		this.lang = lang;
		
		FileKey baseFk = new FileKey("lang." + this.lang);
		this.translationsFile = root.merge(baseFk).merge(file);
		
		TranslationController transController = new TranslationController(this.translationsFile);
		this.translations = transController.fetch();
	}
	
	public String getLang() {
		return this.lang;
	}

	public String translate(String translationKey) {
		String translation = this.translations.getTranslation(translationKey);
		return translation != null ? translation : Core.MISSING_DATA;
	}

}
