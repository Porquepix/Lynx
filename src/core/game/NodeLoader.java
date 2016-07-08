package core.game;

import java.util.Map;

import core.cache.Cache;
import core.cache.LruCache;
import core.json.controller.NodeController;
import core.json.model.node.NodeModel;
import core.namespace.Namespace;
import core.namespace.Resource;

public class NodeLoader {

    private Cache<Namespace, Map<String, NodeModel>> cache;
    
    public NodeLoader(int cachesize) {
	this.cache = new LruCache<>(cachesize);
    }
        
    public NodeModel get(Resource id) {
	if (!cache.containsKey(id.getNamespace())) {
	    load(id.getNamespace());
	}
	return cache.get(id.getNamespace()).get(id.getId());	
    }

    private void load(Namespace file) {
	NodeController nodeController = new NodeController(file);
	cache.add(file, nodeController.fetch());
    }
 
}
