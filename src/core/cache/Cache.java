package core.cache;

import java.util.HashMap;
import java.util.Map;


public abstract class Cache<T, K> {
	
	private int capacity;
	private Map<T, K> cache;
	
	public Cache(int capacity) {
		this.capacity = capacity;
		this.cache = new HashMap<>(capacity);
	}
	
	public final int capacity() {
		return this.capacity;
	}
	
	public int size() {
		return this.cache.size();
	}
	
	public void add(T key, K value) {
		this.cache.put(key, value);
	}

	public void remove(T key) {
		this.cache.remove(key);
	}
	
	public void clear() {
		this.cache.clear();
	}
	
	public K get(T key) {
		return this.cache.get(key);
	}
	
	public K safeGet(T key, K value) {
		if (!this.containsKey(key)) {
			this.add(key, value);
		}
		return this.get(key);
	}
	
	public boolean containsKey(T key) {
		return this.cache.containsKey(key);
	}
	
	public boolean containsValue(K value) {
		return this.cache.containsValue(value);
	}
	
}
