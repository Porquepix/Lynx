package core.key;

import java.nio.file.Paths;

import org.junit.Test;

import junit.framework.TestCase;

public class FileKeyTest extends TestCase {
	
	private FileKey fk1 = new FileKey("a.b.c.d");
	private FileKey fk2 = new FileKey("e.f.g");

	@Test
	public void testGetKey() {
		assertEquals(fk1.getKey(), "a.b.c.d");
		assertEquals(fk2.getKey(), "e.f.g");
	}
	
	@Test
	public void testGetPath() {
		assertEquals(fk1.getPath(), Paths.get("a/b/c/d.json"));
		assertEquals(fk2.getPath(), Paths.get("e/f/g.json"));
	}
	
	@Test
	public void testGetPathAsString() {
		assertEquals(fk1.getPathAsString(), "a\\b\\c\\d.json");
		assertEquals(fk2.getPathAsString(), "e\\f\\g.json");
	}
	
	@Test
	public void testMerge() {
		FileKey fk3 = fk1.merge(fk2);
		assertEquals(fk3.getKey(), "a.b.c.d.e.f.g");
		assertEquals(fk3.getPath(), Paths.get("a/b/c/d/e/f/g.json"));
		assertEquals(fk3.getPathAsString(), "a\\b\\c\\d\\e\\f\\g.json");
	}
	
	@Test
	public void testEquals() {
		FileKey fk3 = new FileKey("a.b.c.d");
		assertEquals(fk1, fk3);
	}

	@Test
	public void testHashCode() {
		FileKey fk3 = new FileKey("a.b.c.d");
		assertEquals(fk1.hashCode(), fk3.hashCode());
	}
	
}
