package com.lynx.core.namespace;

import java.util.Arrays;

public final class Namespace {

    public static final String SEPARATOR = ".";
    public static final String SEPARATOR_REGEX = "\\.";

    private String key;
    private String[] segments;
    private NamespaceResolver resolver;

    public Namespace(String key) {
	this.key = key;
	this.segments = key.split(SEPARATOR_REGEX);
	this.resolver = new NamespaceResolver(this);
    }

    public Namespace(Namespace other) {
	this(other.getKey());
    }

    public String getKey() {
	return this.key;
    }

    public String getSubkey(int startSegment, int endSegment) {
	String[] subpaths = Arrays.copyOfRange(segments, startSegment, endSegment);
	return String.join(SEPARATOR, subpaths);
    }

    public String getSegment(int segment) {
	return segments[segment];
    }

    public String getFirstSegment() {
	return getSegment(0);
    }

    public String getLastSegment() {
	return getSegment(segments.length - 1);
    }

    public Namespace append(String otherKey) {
	Namespace otherNamespace = new Namespace(otherKey);
	return this.merge(otherNamespace);
    }

    public Namespace prepend(String otherKey) {
	Namespace otherNamespace = new Namespace(otherKey);
	return otherNamespace.merge(this);
    }

    public Namespace merge(Namespace otherNamespace) {
	String mergedKey = getKey() + SEPARATOR + otherNamespace.getKey();
	return new Namespace(mergedKey);
    }

    public Resource getResource(String relativeName) {
	Resource relative = new Resource(relativeName);
	relative.setNamespace(this.merge(relative.getNamespace()));
	return relative;
    }

    public NamespaceResolver getResolver() {
	return this.resolver;
    }

    @Override
    public int hashCode() {
	return this.key.hashCode();
    }

    @Override
    public boolean equals(Object o) {
	if (o == this) {
	    return true;
	}

	if (o instanceof Namespace) {
	    Namespace other = (Namespace) o;
	    return this.key.equals(other.getKey());
	}

	return false;
    }

}
