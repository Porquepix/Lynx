package test.core.json;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.*;

import org.junit.Test;

import core.json.JsonContent;
import core.json.JsonFile;

public class JsonFileTest {
	
	@Test
	public void testGetPath() {
		Path p = Paths.get("a/b.json");

		JsonFile fileFromString = new JsonFile("a/b.json");
		assertEquals(p, fileFromString.getPath());
		
		JsonFile fileFromPath = new JsonFile(p);
		assertEquals(p, fileFromPath.getPath());
	}
	
	@Test
	public void testExists() {
		Path p = Paths.get("a/b.json");
		JsonFile fileNotExists = new JsonFile(p);
		assertFalse(fileNotExists.exists());
		
		Path p1 = Paths.get("vendor/test/test.json");
		JsonFile fileExists = new JsonFile(p1);
		assertTrue(fileExists.exists());
	}
	
	@Test
	public void testSetContent() {
		JsonFile fileFromString = new JsonFile("vendor/test/test.json");
		fileFromString.setContent(new JsonContent());
		assertEquals(new JsonContent(), fileFromString.getContent());
	}

}
