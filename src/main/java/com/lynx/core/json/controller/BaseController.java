package com.lynx.core.json.controller;

import java.io.IOException;
import java.io.Writer;

import com.lynx.core.json.JsonController;
import com.lynx.core.json.JsonModel;
import com.lynx.core.namespace.Namespace;

public class BaseController<T extends JsonModel> extends JsonController<T> {

    private Class<T> type;

    public BaseController(Namespace namespace, Class<T> type) {
	super(namespace);
	this.type = type;
    }

    public Class<T> getType() {
	return this.type;
    }

    public T fetch() {
	return this.gson.fromJson(this.getReader(), this.getType());
    }

    public void store(T model) {
	String json = this.gson.toJson(model);
	Writer writer = this.getWriter();
	try {
	    writer.write(json);
	    writer.flush();
	} catch (IOException e) {
	    logger.warn("Impossible to get the write in the file '{}'", e, this.getNamespace()
		    .getKey());
	}
    }

}
