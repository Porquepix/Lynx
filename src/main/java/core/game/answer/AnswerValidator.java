package core.game.answer;

import core.game.node.Node;
import core.json.model.AnswerModel;
import core.validation.GlobalValidatorBuilder;
import core.validation.TypeValidator;
import core.validation.Validator;

public class AnswerValidator implements Validator {

    private Node node;
    private Validator validator;
    private GlobalValidatorBuilder builder;

    public AnswerValidator(Node node) {
	this.node = node;
	this.builder = new GlobalValidatorBuilder();
	buildValidator();
    }

    private void buildValidator() {
	builder.type(node.getType().getClazz(), true);

	AnswerModel model = node.getModel().getAnswer();
	if (model.hasRange()) {
	    builder.range(model.getRange().getMin(), model.getRange().getMax());
	}

	if (node.isClosedAnswer()) {
	    builder.range(0, node.getModel().getChoices().size());
	}

	validator = builder.build();
    }

    @Override
    public boolean validate(Object data) {
	TypeValidator type = new TypeValidator(Answer.class, false);
	if (type.validate(data)) {
	    Answer answer = (Answer) data;
	    return validator.validate(answer.getValue());
	}
	return false;
    }

}
