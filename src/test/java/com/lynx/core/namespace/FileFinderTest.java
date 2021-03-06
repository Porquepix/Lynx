package com.lynx.core.namespace;

import static org.junit.Assert.*;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.junit.Test;

public class FileFinderTest {

	private Namespace ns1 = new Namespace("vendor.test.NodeControllerTest");
	private FileFinder finder1 = new FileFinder(ns1, new String[] { "json", "test.txt" });
	private Namespace ns2 = new Namespace("a.b.c");
	private FileFinder finder2 = new FileFinder(ns2);

	@Test
	public void testFind() {
		assertEquals(finder1.find().get(), Paths.get("vendor/test/NodeControllerTest.json"));
		assertFalse(finder2.find().isPresent());
	}

	@Test
	public void testFindAll() {
		List<Path> result1 = finder1.findAll();
		assertEquals(result1.size(), 2);
		assertTrue(result1.contains(Paths.get("vendor/test/NodeControllerTest.json")));
		assertTrue(result1.contains(Paths.get("vendor/test/NodeControllerTest.test.txt")));

		List<Path> result2 = finder2.findAll();
		assertEquals(result2.size(), 0);
	}

}
