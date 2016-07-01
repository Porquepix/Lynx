package core.json.controller;

import java.lang.reflect.Type;
import java.util.Map;

import com.google.gson.reflect.TypeToken;

import core.json.JsonController;
import core.json.model.node.NodeModel;
import core.key.FileKey;

public class NodeController extends JsonController<Map<String, NodeModel>> {

	public NodeController(FileKey file) {
	    super(file);
    }

	@Override
    public Map<String, NodeModel> fetch() {
		Type type = new TypeToken<Map<String, NodeModel>>(){}.getType();
	    return this.gson.fromJson(this.getReader(), type);
    }

	@Override
    public void store(Map<String, NodeModel> model) {
		throw new UnsupportedOperationException("Impossible to write on game file.");
	}

}
