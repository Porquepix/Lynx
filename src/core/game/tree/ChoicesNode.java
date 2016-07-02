package core.game.tree;

import java.util.ArrayList;
import java.util.List;

import core.json.model.node.ChoiceModel;

public class ChoicesNode extends Node<List<ChoiceModel>> {

	public ChoicesNode(StateNode parent, List<ChoiceModel> content) {
	    super(parent, content);
    }
	
	@Override
	public StateNode getParent() {
		return (StateNode) super.getParent();
	}

	public List<String> getChoices() {
		List<String> ret = new ArrayList<>(content.size());
		for (ChoiceModel model : content) {
			if ( evalCondition(model) ) {
				ret.add(model.getText());
			}
		}
		return ret;
	}
	
	private boolean evalCondition(ChoiceModel model) {
	    return !model.hasCondition() || this.getParent().getGame().evalCondition(model.getCondition());
    }

	public void executeAfter(int choice) {
		ChoiceModel model = content.get(choice);
		if (model.hasAfter()) {
			this.getParent().getGame().eval(model.getAfter());
		}
	}
	
}
