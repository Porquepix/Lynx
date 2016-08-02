package com.lynx.core.json;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import com.google.gson.Gson;
import com.lynx.core.exception.LynxException;
import com.lynx.core.logging.Loggers;
import com.lynx.core.logging.LynxLogger;
import com.lynx.core.namespace.FileFinder;
import com.lynx.core.namespace.Namespace;

public abstract class JsonController<T extends JsonModel> {

    protected static final LynxLogger logger = Loggers.getLogger(JsonController.class);

    private Namespace namespace;
    private FileFinder finder;
    protected Gson gson;

    public JsonController(Namespace namespace) {
	this.namespace = namespace;
	this.finder = new FileFinder(namespace);
	this.gson = new Gson();
    }

    public Namespace getNamespace() {
	return this.namespace;
    }

    protected final Reader getReader() {
	try {
	    Path path = finder.find();
	    return Files.newBufferedReader(path, StandardCharsets.UTF_8);
	} catch (IOException e) {
	    logger.error("Impossible to get the reader of the file '{}'", e, namespace.getKey());
	    throw new LynxException("Impossible to get the reader of the file: "
		    + namespace.getKey());
	}
    }

    protected final Writer getWriter() {
	try {
	    Path path = finder.find();
	    return Files.newBufferedWriter(path, StandardCharsets.UTF_8);
	} catch (IOException e) {
	    logger.error("Impossible to get the writer of the file '{}'", e, namespace.getKey());
	    throw new LynxException("Impossible to get the writer of the file: "
		    + namespace.getKey());
	}
    }

    public abstract T fetch();

    public abstract void store(T model);

}
