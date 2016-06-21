package core.translation;

import core.Core;
import core.json.JsonContent;
import core.json.JsonFile;

public class Translator {

	private JsonContent translations;
	
	public Translator(JsonFile jsonFile) {
		this.translations = jsonFile.loadContent().getContent();
	}

	public String translate(String translationKey) {
		return this.translations.getAsString(translationKey, Core.MISSING_DATA);
	}

}
