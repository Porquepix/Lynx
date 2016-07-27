package core.game.node;

import core.logging.Loggers;
import core.logging.LynxLogger;
import core.namespace.Resource;

public abstract class NodeFactory {
    
    protected static final LynxLogger logger = Loggers.getLogger(NodeFactory.class);

    public abstract Node buildNodeByLocation(Resource identifier);
    
}
