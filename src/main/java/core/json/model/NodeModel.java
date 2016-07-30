package core.json.model;

import java.util.List;

import core.json.JsonModel;

public class NodeModel extends JsonModel {

    private String text;
    private String author;
    private String init;
    private String after;
    private AnswerModel answer;
    private List<ChoiceModel> choices;
    private List<NextModel> nexts;

    public NodeModel() {
    }

    public String getText() {
	return this.text;
    }

    public String getAuthor() {
	return this.author;
    }

    public boolean hasAuthor() {
	return this.getAuthor() != null;
    }

    public String getInit() {
	return this.init;
    }

    public boolean hasInit() {
	return this.getInit() != null;
    }

    public String getAfter() {
	return this.after;
    }

    public boolean hasAfter() {
	return this.getAfter() != null;
    }

    public AnswerModel getAnswer() {
	return this.answer;
    }

    public List<ChoiceModel> getChoices() {
	return this.choices;
    }

    public boolean hasChoices() {
	return this.getChoices() != null;
    }

    public List<NextModel> getNexts() {
	return this.nexts;
    }

}
