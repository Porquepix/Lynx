package core.namespace;

import java.nio.file.Path;

public class Resource {

    public static final String RESSOURCE_SEPARATOR = ".";
    private static final String RESSOURCE_EXTENSION = "json";

    private Namespace namespace;
    private String id;

    public Resource(String key) {
	int lastSeparator = key.lastIndexOf(RESSOURCE_SEPARATOR);
	this.namespace = new Namespace(key.substring(0, lastSeparator));
	this.id = key.substring(lastSeparator + 1);
    }

    public String getId() {
	return this.id;
    }

    public Namespace getNamespace() {
	return this.namespace;
    }

    public void setNamespace(Namespace namespace) {
	this.namespace = namespace;
    }

    public Path getRessourceFile() {
	return namespace.getResolver().getFilePath(RESSOURCE_EXTENSION);
    }

    @Override
    public boolean equals(Object o) {
	if (o == this) {
	    return true;
	}

	if (o instanceof Resource) {
	    Resource other = (Resource) o;
	    return namespace.equals(other.getNamespace())
		    && id.equals(other.getId());
	}

	return false;
    }

}
