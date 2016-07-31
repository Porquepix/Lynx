package com.lynx.core.game.node;

import java.util.List;

import com.lynx.core.exception.GameCorruptedException;
import com.lynx.core.interpreter.IInterpreter;
import com.lynx.core.json.model.ChoiceModel;
import com.lynx.core.json.model.NextModel;
import com.lynx.core.json.model.NodeModel;
import com.lynx.core.logging.Loggers;
import com.lynx.core.logging.LynxLogger;

public class Node {

    private static final LynxLogger logger = Loggers.getLogger(Node.class);

    private NodeModel model;
    private NodeType type;

    protected Node(NodeModel model) {
	this.model = model;
	checkNodeValidity();
	this.type = NodeType.getByName(model.getAnswer().getType());
    }

    private void checkNodeValidity() {
	trueOrFail(model.getText() != null);
	trueOrFail(model.getAnswer() != null);
	trueOrFail(model.getNexts() != null);
    }

    private void trueOrFail(boolean test) {
	if (!test) {
	    throw new GameCorruptedException();
	}
    }

    public NodeModel getModel() {
	return this.model;
    }

    public NodeType getType() {
	return this.type;
    }

    public boolean isClosedAnswer() {
	return getType().equals(NodeType.ANSWER);
    }

    public boolean isSkippable() {
	return getType().equals(NodeType.VOID);
    }

    public List<ChoiceModel> getChoices() {
	return getModel().getChoices();
    }

    public NextModel getNext(IInterpreter interpreter) {
	for (NextModel next : getModel().getNexts()) {
	    if (!next.hasCondition() || interpreter.evalCondition(next.getCondition())) {
		return next;
	    }
	}
	logger.warn("No next node found !");
	return null;
    }

}
