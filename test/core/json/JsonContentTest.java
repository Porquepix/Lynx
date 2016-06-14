package core.json;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import core.json.JsonContent;
import core.json.JsonFile;

public class JsonContentTest {
	
	private static JsonContent testContent = new JsonFile("vendor/test/test.json").loadContent().getContent();
	
	@Test
	public void testConstructor() {
		JsonContent jc = new JsonContent("{"
				+ "\"testBool\": true,"
				+ "\"testInt\": 11,"
				+ "\"testDouble\": 3.1415,"
				+ "\"testString\": \"Hello World !\","
				+ "\"testArray\": [\"fr\", 1],"
				+ "\"testObject\": {"
					+ "\"x\": 3,"
					+ "\"y\": 2.3"
				+ "},"
				+ "\"testArrayOfObject\": ["
					+ "{"
						+ "\"x\": 4,"
						+ "\"y\": 1.14,"
						+ "\"abs\": true"
					+ "},"
					+ "{"
						+ "\"z\": \"Hey !\""
					+ "}"
				+ "]"
				+ "}");
		assertEquals(jc, testContent);
	}
	
	@Test
	public void testGetAsBoolean() {
		assertTrue(testContent.getAsBoolean("testBool", false));
	}
	
	@Test
	public void testGetAsInteger() {
		assertEquals(new Integer(11), testContent.getAsInteger("testInt", 0));
	}
	
	@Test
	public void testGetAsDouble() {
		assertEquals(new Double(3.1415), testContent.getAsDouble("testDouble", 0.0));
	}
	
	@Test
	public void testGetAsString() {
		assertEquals("Hello World !", testContent.getAsString("testString", ""));
	}
	
	@Test
	public void testGetAsArray() {
		List<Object> ret = new ArrayList<>();
		ret.add(0, "fr");
		ret.add(1, 1.0);
		assertEquals(ret, testContent.getAsArray("testArray", null));
	}
	
	@Test
	public void testGetAsObject() {
		JsonContent jc = new JsonContent();
		jc.put("x", 3.0);
		jc.put("y", 2.3);
		assertEquals(jc, testContent.getAsObject("testObject", null));
	}

}
