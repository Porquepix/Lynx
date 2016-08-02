package com.lynx.core.namespace;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.lynx.core.logging.Loggers;
import com.lynx.core.logging.LynxLogger;

public class FileFinder {

    private static final LynxLogger logger = Loggers.getLogger(FileFinder.class);

    private String path;
    private List<String> extensions;

    public FileFinder(Namespace namespace) {
	this(namespace, new String[] { "json" });
    }

    public FileFinder(Namespace namespace, String[] extensions) {
	this(namespace, Arrays.asList(extensions));
    }

    public FileFinder(Namespace namespace, List<String> extensions) {
	this.path = namespace.getPathAsString();
	this.extensions = extensions;
    }

    public Path find() {
	for (String extension : extensions) {
	    Path p = getFullPath(extension);
	    if (Files.exists(p)) {
		return p;
	    }
	}
	logger.warn("No file found for path '{}' and extensions '{}'", path, extensions);
	return null;
    }

    public List<Path> findPossibleFiles() {
	List<Path> paths = new ArrayList<>();
	for (String extension : extensions) {
	    Path p = getFullPath(extension);
	    if (Files.exists(p)) {
		paths.add(p);
	    }
	}
	return paths;
    }

    private Path getFullPath(String extension) {
	return Paths.get(path + "." + extension);
    }

}
