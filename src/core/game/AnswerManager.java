package core.game;

import core.game.node.Node;
import core.interpreter.IInterpreter;
import core.json.model.AnswerModel;
import core.json.model.ChoiceModel;
import core.validation.GlobalValidatorBuilder;
import core.validation.Validator;


public class AnswerManager {
    
    private Node node;
    private Validator validator;
    private IInterpreter interpreter;

    public AnswerManager(Node node, IInterpreter interpreter) {
	this.node = node;
	this.interpreter = interpreter;
	buildAnswerValidator();
    }
    
    private void buildAnswerValidator() {
	AnswerModel model = node.getModel().getAnswer();
	GlobalValidatorBuilder builder = new GlobalValidatorBuilder();

	builder.type(node.getType().getClazz());

	if (model.hasRange()) {
	    builder.range(model.getRange().getMin(), model.getRange()
		    .getMax());
	}

	if (node.isClosedAnswer()) {
	    builder.range(0, node.getModel().getChoices().size());
	}

	this.validator = builder.build();
    }
    
    public boolean validate(Answer answer) {
	boolean result = false;
	
	Answer casted = null;
	if (node.isSkippable()) {
	    result = true;
	} else {
	    casted = answer.cast(node.getType().getClazz());
	    result = validator.validate(casted.getValue());
	    
	    if (result) {
		addAnswerVariable(casted);		
	    }
	}
	
	if (result) {
	    executeNodeAfter();
	    if (node.isClosedAnswer()) {
		executeChoiceAfter(node.getModel().getChoices().get((int) casted.getValue()));
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
