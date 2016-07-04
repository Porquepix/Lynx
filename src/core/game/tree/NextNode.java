package core.game.tree;

import java.util.List;

import core.game.Game;
import core.json.model.node.NextModel;

public class NextNode extends Node<List<NextModel>> {

    public NextNode(StateNode parent, List<NextModel> content) {
	super(parent, content);
    }

    @Override
    public StateNode getParent() {
	return (StateNode) super.getParent();
    }

    public StateNode getNextStateNode() {
	int i = 0;
	boolean found = false;

	NextModel model = null;
	while (i < this.getContent().size() && !found) {
	    model = content.get(i);
	    if (evalCondition(model)) {
		found = true;
	    } else {
		i++;
	    }
	}

	if (found) {
	    Game game = this.getParent().getGame();
	    this.executeAfter(model);
	    String stateId = model.getNode();
	    return new StateNode(game, stateId);
	} else {
	    return null;
	}
    }

    private boolean evalCondition(NextModel model) {
	return !model.hasCondition()
		|| this.getParent().getGame()
			.evalCondition(model.getCondition());
    }

    public void executeAfter(NextModel model) {
	if (model.hasAfter()) {
	    this.getParent().getGame().eval(model.getAfter());
	}
    }
}
