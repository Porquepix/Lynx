package core.json.controller;

import java.util.Map;

import core.json.JsonController;
import core.json.model.TranslationModel;
import core.namespace.Namespace;

public class TranslationController extends JsonController<TranslationModel> {

    public TranslationController(Namespace namespace) {
	super(namespace);
    }

    @Override
    public TranslationModel fetch() {
	@SuppressWarnings("unchecked")
	Map<String, String> translations = this.gson.fromJson(this.getReader(),
		Map.class);

	return new TranslationModel(translations);
    }

    @Override
    public void store(TranslationModel model) {
	throw new UnsupportedOperationException(
		"Impossible to write on translation file.");
    }

}
