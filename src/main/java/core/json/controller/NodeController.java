package core.json.controller;

import java.lang.reflect.Type;
import java.util.Map;

import com.google.gson.reflect.TypeToken;

import core.json.JsonController;
import core.json.container.NodeContainer;
import core.json.model.NodeModel;
import core.namespace.Namespace;

public class NodeController extends JsonController<NodeContainer> {

    public NodeController(Namespace namespace) {
	super(namespace);
    }

    @Override
    public NodeContainer fetch() {
	Type type = new TypeToken<Map<String, NodeModel>>() {
	}.getType();
	return new NodeContainer(this.gson.fromJson(this.getReader(), type));
    }

    @Override
    public void store(NodeContainer model) {
	throw new UnsupportedOperationException("Impossible to write on game file.");
    }

}
