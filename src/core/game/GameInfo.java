package core.game;

import java.nio.file.Path;

import core.Core;
import core.json.JsonContent;
import core.json.JsonFile;

public class GameInfo {

	private JsonContent content;
	
	public GameInfo(Path gameInfoFile) {
		this.content = new JsonFile(gameInfoFile).loadContentOrFail().getContent();
	}

	public String getName() {
		return this.content.getAsString("name", Core.MISSING_DATA);
	}

}
