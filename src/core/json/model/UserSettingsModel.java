package core.json.model;

import core.json.JsonModel;

public class UserSettingsModel extends JsonModel {
    
    private String lang = "en";
    
    public UserSettingsModel() {
    }

    public void setLang(String lang) {
	this.lang = lang;
    }

    public String getLang() {
	return this.lang;
    }

}
