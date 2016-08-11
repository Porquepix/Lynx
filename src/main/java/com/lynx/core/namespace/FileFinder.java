package com.lynx.core.namespace;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.google.common.base.Optional;
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

	public Optional<Path> find() {
		for ( String extension : extensions ) {
			Path p = getFullPath(extension);
			if ( Files.exists(p) ) { 
				return Optional.of(p); 
			}
		}
		logger.warn("No file found for path '{}' and extensions '{}'", path, extensions);
		return Optional.absent();
	}

	public List<Path> findAll() {
		return extensions
		        .stream()
		        .map((String extension) -> getFullPath(extension))
		        .filter(Files::exists)
		        .collect(Collectors.toList());
	}

	public Path getFullPath(String extension) {
		return Paths.get(path + "." + extension);
	}

}
