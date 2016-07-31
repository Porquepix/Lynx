package com.lynx.core.json.container;

import java.util.Map;

import com.lynx.core.json.model.NodeModel;

public class NodeContainer extends JsonMapContainer<String, NodeModel> {

    public NodeContainer(Map<String, NodeModel> data) {
	super(data);
    }

}
