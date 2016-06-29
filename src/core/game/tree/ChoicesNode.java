package core.game.tree;

import java.util.List;

public abstract class ChoicesNode<T> extends Node<T> {

	public ChoicesNode(StateNode parent, T content) {
	    super(parent, content);
    }

	public abstract List<String> getChoices();
	
}
