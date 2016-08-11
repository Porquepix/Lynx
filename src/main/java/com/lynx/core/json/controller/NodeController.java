package com.lynx.core.json.controller;

import java.lang.reflect.Type;
import java.util.Map;

import com.google.gson.reflect.TypeToken;
import com.lynx.core.json.JsonController;
import com.lynx.core.json.container.NodeContainer;
import com.lynx.core.json.model.NodeModel;
import com.lynx.core.namespace.Namespace;

public class NodeController extends JsonController<NodeContainer> {

	public NodeController(Namespace namespace) {
		super(namespace);
	}

	@Override
	public NodeContainer fetch() {
		Type type = new TypeToken<Map<String, NodeModel>>() {}.getType();
		return new NodeContainer(gson.fromJson(getReader(), type));
	}

	@Override
	public void store(NodeContainer model) {
		throw new UnsupportedOperationException("Impossible to write on game file.");
	}

}
