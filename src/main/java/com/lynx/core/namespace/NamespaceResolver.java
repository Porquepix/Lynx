package com.lynx.core.namespace;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class NamespaceResolver {

    private Namespace namespace;

    public NamespaceResolver(Namespace namespace) {
	this.namespace = namespace;
    }

    public String getDirectoryPathAsString() {
	return namespace.getKey().replace(Namespace.SEPARATOR, File.separator);
    }

    public Path getDirectoryPath() {
	return Paths.get(getDirectoryPathAsString());
    }

    public String getFilePathAsString(Extension extension) {
	return getDirectoryPathAsString() + "." + extension.toString();
    }

    public Path getFilePath(Extension extension) {
	return Paths.get(getFilePathAsString(extension));
    }

}
