package com.lynx.core.namespace;

import static org.junit.Assert.*;

import java.nio.file.Paths;

import org.junit.Test;

import com.lynx.core.namespace.Extension;
import com.lynx.core.namespace.Namespace;
import com.lynx.core.namespace.NamespaceResolver;

public class NamespaceResolverTest {

    private Namespace ns1 = new Namespace("a.b.c.d");
    private NamespaceResolver nsr1 = ns1.getResolver();
    private Namespace ns2 = new Namespace("e.f.g");
    private NamespaceResolver nsr2 = ns2.getResolver();

    @Test
    public void testGetFilePath() {
	assertEquals(nsr1.getFilePath(Extension.JSON), Paths.get("a/b/c/d.json"));
	assertEquals(nsr2.getFilePath(Extension.JSON), Paths.get("e/f/g.json"));
    }

    @Test
    public void testGetFilePathAsString() {
	assertEquals(nsr1.getFilePathAsString(Extension.JSON), "a\\b\\c\\d.json");
	assertEquals(nsr2.getFilePathAsString(Extension.JSON), "e\\f\\g.json");
    }

    @Test
    public void testGetDirectoryPath() {
	assertEquals(nsr1.getDirectoryPath(), Paths.get("a/b/c/d"));
	assertEquals(nsr2.getDirectoryPath(), Paths.get("e/f/g"));
    }

    @Test
    public void testGetDirectoryPathAsString() {
	assertEquals(nsr1.getDirectoryPathAsString(), "a\\b\\c\\d");
	assertEquals(nsr2.getDirectoryPathAsString(), "e\\f\\g");
    }

    @Test
    public void testMergedResolver() {
	NamespaceResolver nsr3 = ns1.merge(ns2).getResolver();

	assertEquals(nsr3.getFilePath(Extension.JSON), Paths.get("a/b/c/d/e/f/g.json"));
	assertEquals(nsr3.getFilePathAsString(Extension.JSON), "a\\b\\c\\d\\e\\f\\g.json");
	assertEquals(nsr3.getDirectoryPath(), Paths.get("a/b/c/d/e/f/g"));
	assertEquals(nsr3.getDirectoryPathAsString(), "a\\b\\c\\d\\e\\f\\g");
    }

}
