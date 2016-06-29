package core.game.tree;

import java.util.Objects;


public abstract class Node<T> {
	
	protected Node<?> parent;
	protected T content;
	
	public Node(Node<?> parent, T content) {
		Objects.requireNonNull(content);
		this.content = content;
		
		this.parent = parent;
	}
	
	public Node<?> getParent() {
		return this.parent;
	}
	
	protected T getContent() {
		return this.content;
	}

}
