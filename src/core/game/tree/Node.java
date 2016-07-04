package core.game.tree;

import java.util.Objects;

public abstract class Node<T> {

    protected T content;
    protected Node<?> parent;

    public Node(Node<?> parent, T content) {
	setContent(content);
	this.parent = parent;
    }

    protected final T getContent() {
	return this.content;
    }

    protected final void setContent(T content) {
	Objects.requireNonNull(content);
	this.content = content;
    }

    public Node<?> getParent() {
	return this.parent;
    }

}
