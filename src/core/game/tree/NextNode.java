package core.game.tree;

import java.util.List;
import java.util.Map;

import core.game.Game;

public class NextNode extends Node<List<Map<String, String>>> {

	public NextNode(StateNode parent, List<Map<String, String>> content) {
	    super(parent, content);
    }

	@Override
	public StateNode getParent() {
		return (StateNode) super.getParent();
	}
	
	public StateNode getNextStateNode() {
		int i = 0;
		boolean found = false;
		
		while (i < this.getContent().size() && !found) {
			String condition = this.getContent().get(i).get("condition");
			if ( condition == null || this.evalCondition(condition) ) {
				found = true;
			} else {
				i++;
			}
		}

		if (found) {
			Game game = this.getParent().getGame();
			String stateId = this.getContent().get(i).get("context");
			return new StateNode(game, stateId);
		} else {
			return null;
		}
    }

	private boolean evalCondition(String condition) {
		return this.getParent().getGame().evalCondition(condition);
	}

}
