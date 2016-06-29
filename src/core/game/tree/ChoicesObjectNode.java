package core.game.tree;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ChoicesObjectNode extends ChoicesNode<List<Map<String, String>>> {

	public ChoicesObjectNode(StateNode parent, List<Map<String, String>> content) {
	    super(parent, content);
    }
	
	@Override
	public StateNode getParent() {
		return (StateNode) super.getParent();
	}

	@Override
    public List<String> getChoices() {
		List<String> ret = new ArrayList<>();
		for (Map<String, String> choice : this.getContent()) {
			String condition = choice.get(StateNode.CONDITION);
			if ( condition == null || this.evalCondition(condition) ) {
				ret.add(choice.get(StateNode.TEXT));
			}
		}
		return ret;
    }
	
	private boolean evalCondition(String condition) {
		return this.getParent().getGame().evalCondition(condition);
	}

}
