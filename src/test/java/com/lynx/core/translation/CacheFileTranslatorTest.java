package com.lynx.core.translation;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.lynx.core.namespace.Namespace;
import com.lynx.core.translation.CacheFileTranslator;

public class CacheFileTranslatorTest {

    private CacheFileTranslator managerFr = new CacheFileTranslator(new Namespace(
	    "vendor.test.lang"), "fr", 10);
    private CacheFileTranslator managerEn = new CacheFileTranslator(new Namespace(
	    "vendor.test.lang"), "en", 10);

    @Test
    public void testSingleton() {
	Namespace name = new Namespace("translation");
	assertSame(managerFr.getTranslator(name), managerFr.getTranslator(name));
    }

    @Test
    public void testGetLang() {
	assertEquals(managerFr.getLang(), "fr");
	assertEquals(managerEn.getLang(), "en");
    }

    @Test
    public void testGetRoot() {
	assertEquals(managerFr.getRoot(), new Namespace("vendor.test.lang.fr"));
	assertEquals(managerEn.getRoot(), new Namespace("vendor.test.lang.en"));
    }

    @Test
    public void testTranslate() {
	assertEquals(managerFr.translate("t$translation.hello"), "Bonjour");
	assertEquals(managerFr.translate("hello"), "hello");
	assertEquals(managerFr.translate("t$translation.hello1"), "hello1");
	assertEquals(managerFr.translate(null), "");

	assertEquals(managerEn.translate("t$translation.hello"), "Hello");
	assertEquals(managerEn.translate("hello"), "hello");
	assertEquals(managerEn.translate("t$translation.hello1"), "hello1");
	assertEquals(managerEn.translate(null), "");
    }

    @Test
    public void testTranslateAll() {
	List<String> expected = new ArrayList<>();
	expected.add("Bonjour");
	expected.add("Monde");

	List<String> test = new ArrayList<>();
	test.add("t$translation.hello");
	test.add("t$translation.world");

	assertEquals(managerFr.translateAll(test), expected);
    }

}
