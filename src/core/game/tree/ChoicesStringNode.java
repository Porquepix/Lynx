package core.game.tree;

import java.util.List;

public class ChoicesStringNode extends ChoicesNode<List<String>> {

	public ChoicesStringNode(StateNode parent, List<String> content) {
	    super(parent, content);
    }

	@Override
    public List<String> getChoices() {
	    return this.getContent();
    }

}
