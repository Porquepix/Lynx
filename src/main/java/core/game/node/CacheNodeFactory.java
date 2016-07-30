package core.game.node;

import core.cache.Cache;
import core.cache.LruCache;
import core.json.container.NodeContainer;
import core.json.controller.NodeController;
import core.json.model.NodeModel;
import core.namespace.Namespace;
import core.namespace.Resource;

public class CacheNodeFactory extends NodeFactory {

    private Cache<Namespace, NodeContainer> cache;

    public CacheNodeFactory(int cachesize) {
	this.cache = new LruCache<>(cachesize);
    }

    @Override
    public Node buildNodeByIdentifier(Resource identifier) {
	logger.info("Loading node '{}'", identifier.getRessourceFile());
	NodeModel model = getNodeModel(identifier);
	return new Node(model);
    }

    private NodeModel getNodeModel(Resource identifier) {
	Namespace location = identifier.getNamespace();
	if (!cache.containsKey(location)) {
	    loadNodeModel(location);
	}
	return cache.get(location).get(identifier.getId());
    }

    private void loadNodeModel(Namespace file) {
	NodeController nodeController = new NodeController(file);
	cache.add(file, nodeController.fetch());
    }

}
