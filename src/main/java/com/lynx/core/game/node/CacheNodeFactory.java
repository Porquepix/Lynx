package com.lynx.core.game.node;

import java.util.concurrent.TimeUnit;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.lynx.core.game.IGame;
import com.lynx.core.json.container.NodeContainer;
import com.lynx.core.json.controller.NodeController;
import com.lynx.core.json.model.NodeModel;
import com.lynx.core.namespace.Namespace;
import com.lynx.core.namespace.Resource;

public class CacheNodeFactory extends NodeFactory {

	private IGame game;
	private LoadingCache<Namespace, NodeContainer> cache;

	public CacheNodeFactory(IGame game, int cachesize) {
		this.game = game;
		this.cache = CacheBuilder.newBuilder()
				.maximumSize(cachesize)
				.expireAfterAccess(10, TimeUnit.MINUTES)
				.build(
						new CacheLoader<Namespace, NodeContainer>() { 
							public NodeContainer load(Namespace key) {
								return createNodeContainer(key);
							} 
						}
				);
	}

	@Override
	public Node buildNodeByIdentifier(Resource identifier) {
		logger.info("Loading node '{}'", identifier.getRessourceFile());
		NodeModel model = getNodeModel(identifier);
		return new Node(game, model);
	}

	private NodeModel getNodeModel(Resource identifier) {
		return cache.getUnchecked(identifier.getNamespace()).get(identifier.getId());
	}

	private NodeContainer createNodeContainer(Namespace file) {
		NodeController nodeController = new NodeController(file);
		return nodeController.fetch();
	}

}
