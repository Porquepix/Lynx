package com.lynx.core.json.model;

import java.util.HashMap;
import java.util.Map;

import com.lynx.core.json.JsonModel;

public class TranslationModel extends JsonModel {

	private Map<String, String> translations = new HashMap<>();

	public TranslationModel(Map<String, String> translations) {
		this.translations = translations;
	}

	public String getTranslation(String key) {
		return this.translations.get(key);
	}

}
