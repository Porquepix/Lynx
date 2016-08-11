package com.lynx.core.json.controller;

import java.io.IOException;
import java.io.Writer;
import java.util.Objects;

import com.lynx.core.json.JsonController;
import com.lynx.core.json.JsonModel;
import com.lynx.core.namespace.Namespace;

public class BaseController<T extends JsonModel> extends JsonController<T> {

	private Class<T> type;

	public BaseController(Namespace namespace, Class<T> type) {
		super(namespace);
		this.type = Objects.requireNonNull(type);
	}

	public Class<T> getType() {
		return this.type;
	}

	@Override
	public T fetch() {
		return gson.fromJson(getReader(), type);
	}

	@Override
	public void store(T model) {
		String json = gson.toJson(model);
		Writer writer = getWriter();
		try {
			writer.write(json);
			writer.flush();
		} catch (IOException e) {
			logger.warn("Impossible to get the write in the file '{}'", e, namespace);
		} finally {
			try {
	            writer.close();
            } catch (IOException e) {
    			logger.warn("Impossible to close writer for the file '{}'", e, namespace);
            }
		}
	}

}
