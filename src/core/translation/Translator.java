package core.translation;

import java.nio.file.Path;
import java.nio.file.Paths;

import core.Core;
import core.json.JsonContent;
import core.json.JsonFile;

public class Translator {
	
	private static final String URI_FORMAT = "lang/%s/%s"; 

	private String lang;
	private JsonFile translationsFile;
	private JsonContent translations;
	
	public Translator(String root, String lang, String file) {
		this(Paths.get(root), lang, Paths.get(file));
	}
	
	public Translator(Path root, String lang, Path file) {
		this.lang = lang;
		this.translationsFile = new JsonFile(this.getFullPath(root, file));
		this.translations = this.translationsFile.loadContent().getContent();
	}

	private Path getFullPath(Path root, Path file) {
		Path finalPath = root.resolve(String.format(URI_FORMAT, this.lang, file.toString()));
		return finalPath;
	}
	
	public String getLang() {
		return this.lang;
	}

	public String translate(String translationKey) {
		return this.translations.getAsString(translationKey, Core.MISSING_DATA);
	}

}
