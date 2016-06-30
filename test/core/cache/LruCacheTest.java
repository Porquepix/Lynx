package core.cache;

import org.junit.Test;

import junit.framework.TestCase;

public class LruCacheTest extends TestCase {
	
	private LruCache<String, String> cache = new LruCache<>(3);
	
	@Test
	public void testClear() {
		cache.add("1", "test1");
		cache.add("2", "test2");
		cache.clear();
		
		assertEquals(cache.size(), 0);
	}
	
	@Test
	public void testAdd() {
		cache.clear();
		cache.add("1", "test1");
		cache.add("2", "test2");
		
		assertEquals(cache.containsKey("1"), true);
		assertEquals(cache.containsKey("2"), true);
		assertEquals(cache.containsKey("3"), false);
	}
	
	
	@Test
	public void testAddMaxCapacity() {
		cache.clear();
		cache.add("1", "test1");
		cache.add("2", "test2");
		cache.add("3", "test3");
		cache.add("4", "test4");
		
		assertEquals(cache.containsKey("1"), false);
		assertEquals(cache.containsKey("2"), true);
		assertEquals(cache.containsKey("3"), true);
		assertEquals(cache.containsKey("4"), true);
	}
	
	@Test
	public void testRemove() {
		cache.clear();
		cache.add("1", "test1");
		cache.add("2", "test2");
		cache.remove("2");
		
		assertEquals(cache.containsKey("1"), true);
		assertEquals(cache.containsKey("2"), false);
		assertEquals(cache.containsKey("3"), false);
	}
	
	@Test
	public void testGet() {
		cache.clear();
		cache.add("1", "test1");
		cache.add("2", "test2");
		
		assertEquals(cache.get("1"), "test1");
		assertEquals(cache.get("2"), "test2");
		assertEquals(cache.get("3"), null);
	}
	
	@Test
	public void testGetLeastRecentlyUsedKey() {
		cache.clear();
		cache.add("1", "test1");
		cache.add("2", "test2");
		
		assertEquals(cache.getLeastRecentlyUsedKey(), "1");
		
		cache.get("1");
		
		assertEquals(cache.getLeastRecentlyUsedKey(), "2");
	}

}
