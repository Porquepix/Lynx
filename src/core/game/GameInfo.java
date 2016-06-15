package core.game;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import core.Core;
import core.json.JsonContent;
import core.json.JsonFile;
import core.translation.TranslateManager;

public class GameInfo {

	private JsonContent content;
	private TranslateManager translator;
	
	public GameInfo(Path gameInfoFile) {
		this.content = new JsonFile(gameInfoFile).loadContentOrFail().getContent();
	}

	public String getName() {
		return translator.translate(this.content.getAsString("name", Core.MISSING_DATA));
	}
	
	public String getDescription() {
		return translator.translate(this.content.getAsString("description", Core.MISSING_DATA));
	}
	
	@SuppressWarnings("unchecked")
	public List<String> getVariables() {
		return (List<String>) this.content.getAsArray("declare", new ArrayList<String>());
	}
	
	@SuppressWarnings("unchecked")
	public List<String> getLangs() {
		return (List<String>) this.content.getAsArray("langs", new ArrayList<String>());
	}
	
	public String getLang() {
		return this.content.getAsString("lang", "");
	}
	
	public String getStartingPoint() {
		return this.content.getAsString("boot", "");
	}
	
	public void setTranslator(TranslateManager t) {
		this.translator = t;
	}

}
