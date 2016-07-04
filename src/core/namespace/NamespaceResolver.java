package core.namespace;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class NamespaceResolver {

    private Namespace namespace;

    public NamespaceResolver(Namespace namespace) {
	this.namespace = namespace;
    }

    public String getDirectoryPathAsString() {
	return namespace.getKey().replace(Namespace.NAMESPACE_SEPARATOR, File.separator);
    }
    
    public Path getDirectoryPath() {
	return Paths.get(getDirectoryPathAsString());
    }
    
    public String getFilePathAsString(String extension) {
	return getDirectoryPathAsString() + "." + extension;
    }
    
    public Path getFilePath(String extension) {
	return Paths.get(getFilePathAsString(extension));
    }

}
