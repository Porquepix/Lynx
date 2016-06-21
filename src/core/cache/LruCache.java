package core.cache;

import java.util.ArrayList;
import java.util.List;

public class LruCache<T, K> extends Cache<T, K> {

	private List<T> keyUsage;
	
	public LruCache(int capacity) {
		super(capacity);
		this.keyUsage = new ArrayList<>(capacity);
	}
	
	@Override
	public void add(T key, K value) {
		if (this.size() >= this.capacity()) {
			removeLeastRecentlyUsedKey();
		}
		super.add(key, value);
		this.keyUsage.add(0, key);
	}
	
	@Override
	public void remove(T key) {
		super.remove(key);
		this.keyUsage.remove(key);
	}

	private void removeLeastRecentlyUsedKey() {
		T leastUsedKey = this.getLeastRecentlyUsedKey();
		this.remove(leastUsedKey);
	}

	private T getLeastRecentlyUsedKey() {
		return this.keyUsage.get(this.size() - 1);
	}
	
	@Override
	public K get(T key) {
		refreshKeyUsage(key);
		return super.get(key);
	}

	private void refreshKeyUsage(T key) {
		int elementIndex = this.keyUsage.indexOf(key);
		this.keyUsage.remove(elementIndex);
		this.keyUsage.add(0, key);
	}

}
