package core.namespace;

import java.util.Arrays;

public class Namespace {
    
    public static final String NAMESPACE_SEPARATOR = ".";
    protected static final String NAMESPACE_SEPARATOR_REGEX = "\\.";
    
    private String key;
    private String[] paths;
    private NamespaceResolver resolver;
    
    public Namespace(String key) {
	this.key = key;
	this.paths = key.split(NAMESPACE_SEPARATOR_REGEX);
	this.resolver = new NamespaceResolver(this);
    }
    
    public String getKey() {
	return this.key;
    }
    
    public String getSubkey(int startSegment, int endSegment) {
	String[] subpaths = Arrays.copyOfRange(paths, startSegment, endSegment);
	return String.join(NAMESPACE_SEPARATOR, subpaths);
    }
    
    public String getSegment(int segment) {
	return paths[segment];
    }
    
    public String getFirstSegment() {
	return getSegment(0);
    }
    
    public String getLastSegment() {
	return getSegment(paths.length - 1);
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
	String mergedKey = getKey() + NAMESPACE_SEPARATOR + otherNamespace.getKey();
	return new Namespace(mergedKey);
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
