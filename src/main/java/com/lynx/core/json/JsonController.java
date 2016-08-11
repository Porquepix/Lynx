package com.lynx.core.json;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

import com.google.common.base.Optional;
import com.google.gson.Gson;
import com.lynx.core.exception.LynxException;
import com.lynx.core.logging.Loggers;
import com.lynx.core.logging.LynxLogger;
import com.lynx.core.namespace.FileFinder;
import com.lynx.core.namespace.Namespace;

public abstract class JsonController<T extends JsonModel> {

	protected static final LynxLogger logger = Loggers.getLogger(JsonController.class);

	protected Namespace namespace;
	protected Path path;
	protected Gson gson;

	public JsonController(Namespace namespace) {
		this.namespace = Objects.requireNonNull(namespace);
		this.gson = new Gson();

		Optional<Path> path = new FileFinder(namespace).find();
		if ( path.isPresent() ) {
			this.path = path.get();
		} else {
			throw new LynxException("File not found:" + namespace);
		}
	}

	public Namespace getNamespace() {
		return this.namespace;
	}

	protected final Reader getReader() {
		try {
			return Files.newBufferedReader(path, StandardCharsets.UTF_8);
		} catch (IOException e) {
			logger.error("Impossible to get the reader of the file '{}'", e, namespace);
			throw new LynxException("Impossible to get the reader of the file: " + namespace);
		}
	}

	protected final Writer getWriter() {
		try {
			return Files.newBufferedWriter(path, StandardCharsets.UTF_8);
		} catch (IOException e) {
			logger.error("Impossible to get the writer of the file '{}'", e, namespace);
			throw new LynxException("Impossible to get the writer of the file: " + namespace);
		}
	}

	public abstract T fetch();

	public abstract void store(T model);

}
