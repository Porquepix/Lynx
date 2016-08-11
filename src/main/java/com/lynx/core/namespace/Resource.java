package com.lynx.core.namespace;

import java.nio.file.Path;

import com.google.common.base.Optional;

public class Resource {

	public static final String RESSOURCE_SEPARATOR = ".";

	private Namespace namespace;
	private String id;
	private String key;

	public Resource(String key) {
		this.key = key;
		int lastSeparator = key.lastIndexOf(RESSOURCE_SEPARATOR);
		this.namespace = new Namespace(key.substring(0, lastSeparator));
		this.id = key.substring(lastSeparator + 1);
	}

	public Resource(Resource other) {
		this.namespace = new Namespace(other.getNamespace());
		this.id = other.getId();
	}
	
	public String getKey() {
		return this.key;
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

	public Optional<Path> getRessourceFile() {
		return new FileFinder(namespace).find();
	}

	@Override
	public boolean equals(Object o) {
		if ( o == this ) { return true; }

		if ( o instanceof Resource ) {
			Resource other = (Resource) o;
			return namespace.equals(other.getNamespace()) && id.equals(other.getId());
		}

		return false;
	}

}
