package core.game.tree;

import core.game.Answer;
import core.game.validation.GlobalValidatorBuilder;
import core.game.validation.Validator;
import core.json.model.node.AnswerModel;

public class AnswerNode extends Node<AnswerModel> {

    private Validator answerValidator;
    private NodeType type;

    public AnswerNode(StateNode parent, AnswerModel content) {
	super(parent, content);
	this.type = NodeType.getByName(content.getType());
	buildAnswerValidator();
    }

    @Override
    public StateNode getParent() {
	return (StateNode) super.getParent();
    }

    private void buildAnswerValidator() {
	GlobalValidatorBuilder builder = new GlobalValidatorBuilder();

	builder.type(this.getAnswerType().getClazz());

	if (content.hasRange()) {
	    builder.range(content.getRange().getMin(), content.getRange()
		    .getMax());
	}

	if (this.isAnswerType()) {
	    builder.range(0, this.getParent().nbChoices());
	}

	this.answerValidator = builder.build();
    }

    public NodeType getAnswerType() {
	return this.type;
    }

    public boolean isAnswerType() {
	return this.getAnswerType().equals(NodeType.ANSWER);
    }

    public boolean isVoidType() {
	return this.getAnswerType().equals(NodeType.VOID);
    }

    public boolean isValidAnswer(Answer a) {
	return answerValidator.validate(this.getCastedAnswer(a).getValue());
    }

    public Answer getCastedAnswer(Answer a) {
	return a.cast(this.getAnswerType().getClazz());
    }

}
