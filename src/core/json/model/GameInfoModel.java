package core.json.model;

import java.util.List;

public class GameInfoModel {

    private String name;
    private String description;
    private List<String> languages;
    private String language;
    private List<String> declare;
    private String boot;

    public GameInfoModel() {
    }

    public String getName() {
	return this.name;
    }

    public String getDescription() {
	return this.description;
    }

    public List<String> getLanguages() {
	return this.languages;
    }

    public String getLanguage() {
	return this.language;
    }

    public List<String> getDeclaration() {
	return this.declare;
    }

    public String getBoot() {
	return this.boot;
    }

}
