package com.lynx.core.game.node;

import java.util.List;
import java.util.stream.Collectors;

import com.lynx.core.game.Facade;
import com.lynx.core.json.model.ChoiceModel;
import com.lynx.core.translation.Translator;

public class NodeFacade extends Facade {

    private Node node;

    public NodeFacade(Node node, Translator translator) {
	super(translator);
	this.node = node;
    }

    public String getText() {
	return translator.translate(node.getModel().getText());
    }

    public String getAuthor() {
	return translator.translate(node.getModel().getAuthor());
    }

    public boolean hasAuthor() {
	return node.getModel().hasAuthor();
    }

    public List<String> getChoices() {
	if (isClosedAnswer()) {
	    return translator.translateAll(choicesToString());
	}
	return null;
    }

    private List<String> choicesToString() {
	return node
		.getChoices()
		.stream()
		.filter((ChoiceModel model) -> node.isDisplayable(model))
		.map(ChoiceModel::getText)
		.collect(Collectors.toList());
    }

    public boolean isClosedAnswer() {
	return node.isClosedAnswer();
    }

    public boolean isSkippable() {
	return node.isSkippable();
    }

}
