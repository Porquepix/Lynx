package core.game;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import core.Core;
import core.json.JsonContent;
import core.json.JsonFile;
import core.translation.TranslateManager;

public class GameInfo {
	
	private static final String NAME = "name";
	private static final String DESCRIPTION = "description";
	private static final String VARIABLES = "declare";
	private static final String LAGUAGES = "langs";
	private static final String DEFAULT_LANGUAGE = "lang";
	private static final String STARTING_NODE = "boot";

	private JsonContent content;
	private TranslateManager translator;
	
	public GameInfo(Path gameInfoFile) {
		this.content = new JsonFile(gameInfoFile).loadContentOrFail().getContent();
	}

	public String getName() {
		return translator.translate(this.content.getAsString(NAME, Core.MISSING_DATA));
	}
	
	public String getDescription() {
		return translator.translate(this.content.getAsString(DESCRIPTION, Core.MISSING_DATA));
	}
	
	@SuppressWarnings("unchecked")
	public List<String> getLangs() {
		return (List<String>) this.content.getAsArray(LAGUAGES, new ArrayList<String>());
	}
	
	public String getLang() {
		return this.content.getAsString(DEFAULT_LANGUAGE, "");
	}
	
	@SuppressWarnings("unchecked")
	public List<String> getVariables() {
		return (List<String>) this.content.getAsArray(VARIABLES, new ArrayList<String>());
	}
	
	public String getStartingPoint() {
		return this.content.getAsString(STARTING_NODE, "");
	}
	
	public void setTranslator(TranslateManager t) {
		this.translator = t;
	}

}
