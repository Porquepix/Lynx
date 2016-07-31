package com.lynx.core.json.model;

import com.lynx.core.json.JsonModel;

public class ChoiceModel extends JsonModel {

    private String condition;
    private String text;
    private String after;

    public ChoiceModel() {
    }

    public String getCondition() {
	return this.condition;
    }

    public boolean hasCondition() {
	return this.getCondition() != null;
    }

    public String getText() {
	return this.text;
    }

    public String getAfter() {
	return this.after;
    }

    public boolean hasAfter() {
	return this.getAfter() != null;
    }

}
