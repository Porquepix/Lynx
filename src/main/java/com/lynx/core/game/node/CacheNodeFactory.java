package com.lynx.core.game.node;

import com.lynx.core.cache.Cache;
import com.lynx.core.cache.LruCache;
import com.lynx.core.interpreter.IInterpreter;
import com.lynx.core.json.container.NodeContainer;
import com.lynx.core.json.controller.NodeController;
import com.lynx.core.json.model.NodeModel;
import com.lynx.core.namespace.Namespace;
import com.lynx.core.namespace.Resource;

public class CacheNodeFactory extends NodeFactory {

    private Cache<Namespace, NodeContainer> cache;

    public CacheNodeFactory(int cachesize) {
	this.cache = new LruCache<>(cachesize);
    }

    @Override
    public Node buildNodeByIdentifier(Resource identifier, IInterpreter interpreter) {
	logger.info("Loading node '{}'", identifier.getRessourceFile());
	NodeModel model = getNodeModel(identifier);
	return new Node(model, interpreter);
    }

    private NodeModel getNodeModel(Resource identifier) {
	Namespace location = identifier.getNamespace();
	if (!cache.containsKey(location)) {
	    loadNodeModel(location);
	}
	return cache.get(location).get().get(identifier.getId());
    }

    private void loadNodeModel(Namespace file) {
	NodeController nodeController = new NodeController(file);
	cache.add(file, nodeController.fetch());
    }

}
