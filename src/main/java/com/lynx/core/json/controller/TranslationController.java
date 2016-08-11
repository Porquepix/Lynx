package com.lynx.core.json.controller;

import java.util.Map;

import com.lynx.core.json.JsonController;
import com.lynx.core.json.model.TranslationModel;
import com.lynx.core.namespace.Namespace;

public class TranslationController extends JsonController<TranslationModel> {

	public TranslationController(Namespace namespace) {
		super(namespace);
	}

	@Override
	public TranslationModel fetch() {
		@SuppressWarnings("unchecked")
		Map<String, String> translations = gson.fromJson(getReader(), Map.class);
		return new TranslationModel(translations);
	}

	@Override
	public void store(TranslationModel model) {
		throw new UnsupportedOperationException("Impossible to write on translation file.");
	}

}
