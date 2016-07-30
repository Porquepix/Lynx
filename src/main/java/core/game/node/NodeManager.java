package core.game.node;

import core.namespace.Namespace;

public class NodeManager {

    private Namespace root;
    private NodeFactory factory;

    public NodeManager(Namespace root, NodeFactory factory) {
	this.root = root;
	this.factory = factory;
    }

    public Node getNode(String nodeName) {
	return factory.buildNodeByIdentifier(root.getResource(nodeName));
    }

}
