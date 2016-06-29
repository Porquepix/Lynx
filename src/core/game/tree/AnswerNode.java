package core.game.tree;

import core.game.Answer;
import core.game.validation.GlobalValidatorBuilder;
import core.game.validation.Validator;
import core.json.JsonContent;

public class AnswerNode extends Node<JsonContent> {
	
	protected static final String TYPE = "type";
	
	private Validator answerValidator;
	private NodeType type;

	public AnswerNode(StateNode parent, JsonContent content) {
	    super(parent, content);
	    this.type = NodeType.getByName(content.getAsString(TYPE, null));
	    buildAnswerValidator();
    }
	
	@Override
	public StateNode getParent() {
		return (StateNode) super.getParent();
	}
	
	private void buildAnswerValidator() {
	    GlobalValidatorBuilder builder = new GlobalValidatorBuilder();
	    
	    builder.type(this.getAnswerType().getClazz());
	    
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
