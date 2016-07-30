package core.game.node;

import java.util.List;
import java.util.stream.Collectors;

import core.game.Facade;
import core.json.model.ChoiceModel;
import core.translation.Translator;

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
	return node.getChoices().stream().map(ChoiceModel::getText).collect(Collectors.toList());
    }

    public boolean isClosedAnswer() {
	return node.isClosedAnswer();
    }

    public boolean isSkippable() {
	return node.isSkippable();
    }

}
