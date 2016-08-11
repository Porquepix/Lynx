package com.lynx.core.json.container;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.lynx.core.json.JsonContainer;

public class JsonMapContainer<T, K> extends JsonContainer implements Map<T, K> {

	protected Map<T, K> data = new HashMap<>();

	public JsonMapContainer(Map<T, K> data) {
		this.data = data;
	}

	@Override
	public int size() {
		return data.size();
	}

	@Override
	public boolean isEmpty() {
		return data.isEmpty();
	}

	@Override
	public boolean containsKey(Object key) {
		return data.containsKey(key);
	}

	@Override
	public boolean containsValue(Object value) {
		return data.containsValue(value);
	}

	@Override
	public K get(Object key) {
		return data.get(key);
	}

	@Override
	public K put(T key, K value) {
		return data.put(key, value);
	}

	@Override
	public K remove(Object key) {
		return data.remove(key);
	}

	@Override
	public void putAll(Map<? extends T, ? extends K> m) {
		data.putAll(m);
	}

	@Override
	public void clear() {
		data.clear();
	}

	@Override
	public Set<T> keySet() {
		return data.keySet();
	}

	@Override
	public Collection<K> values() {
		return data.values();
	}

	@Override
	public Set<java.util.Map.Entry<T, K>> entrySet() {
		return data.entrySet();
	}

}
