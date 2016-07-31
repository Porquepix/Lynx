package com.lynx.core.game.node;

import com.lynx.core.logging.Loggers;
import com.lynx.core.logging.LynxLogger;
import com.lynx.core.namespace.Resource;

public abstract class NodeFactory {

    protected static final LynxLogger logger = Loggers.getLogger(NodeFactory.class);

    public NodeFactory() {
    }

    public abstract Node buildNodeByIdentifier(Resource identifier);

}
