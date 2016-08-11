package com.lynx.core.translation;

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Strings;
import com.lynx.core.json.controller.TranslationController;
import com.lynx.core.json.model.TranslationModel;
import com.lynx.core.namespace.Namespace;

public class FileTranslator implements Translator {

	private Namespace namespace;
	private TranslationModel translations;

	public FileTranslator(Namespace file) {

		this.namespace = file;

		TranslationController transController = new TranslationController(this.namespace);
		this.translations = transController.fetch();
	}

	@Override
	public String translate(String translationKey) {
		String escaped = Strings.nullToEmpty(translationKey);
		String translation = this.translations.getTranslation(escaped);
		return translation != null ? translation : escaped;
	}

	@Override
	public List<String> translateAll(List<String> sl) {
		List<String> ret = new ArrayList<>(sl.size());
		for ( String s : sl ) {
			ret.add(this.translate(s));
		}
		return ret;
	}

}
