package com.lynx.core.game.node;

import com.lynx.core.interpreter.IInterpreter;
import com.lynx.core.namespace.Namespace;

public class NodeManager {

    private Namespace root;
    private NodeFactory factory;
    private IInterpreter interpreter;

    public NodeManager(Namespace root, NodeFactory factory, IInterpreter interpreter) {
	this.root = root;
	this.factory = factory;
	this.interpreter = interpreter;
    }

    public Node getNode(String nodeName) {
	return factory.buildNodeByIdentifier(root.getResource(nodeName), interpreter);
    }

}
