package core.game.answer;

import core.game.node.Node;
import core.interpreter.IInterpreter;
import core.json.model.ChoiceModel;
import core.validation.Validator;

public class AnswerManager {

    private Node node;
    private Validator validator;
    private IInterpreter interpreter;
    private AnswerFactory factory;

    public AnswerManager(Node node, IInterpreter interpreter, AnswerFactory factory) {
	this.node = node;
	this.interpreter = interpreter;
	this.validator = new AnswerValidator(node);
	this.factory = factory;
    }

    public Answer toAnswer(String answer) {
	return factory.buildAnswerByString(answer, node.getType().getClazz());
    }

    public boolean validate(Answer answer) {
	boolean result = false;

	if (answer == null) {
	    return false;
	}

	if (node.isSkippable()) {
	    result = true;
	} else {
	    result = validator.validate(answer.getValue());

	    if (result) {
		addAnswerVariable(answer);
	    }
	}

	if (result) {
	    executeNodeAfter();
	    if (node.isClosedAnswer()) {
		executeChoiceAfter(node.getModel().getChoices().get((int) answer.getValue()));
	    }
	}

	return result;
    }

    private void addAnswerVariable(Answer answer) {
	interpreter.addVariable("__answer", answer.getValue());
    }

    private void executeChoiceAfter(ChoiceModel model) {
	interpreter.evalIfNotNull(node.getModel().getAfter());
    }

    private void executeNodeAfter() {
	interpreter.evalIfNotNull(node.getModel().getAfter());
    }

}
