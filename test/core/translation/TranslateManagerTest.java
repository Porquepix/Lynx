package core.translation;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import core.Core;
import core.namespace.Namespace;

public class TranslateManagerTest {
    
    private TranslateManager managerFr = new TranslateManager(new Namespace("vendor.test"), "fr");
    private TranslateManager managerEn = new TranslateManager(new Namespace("vendor.test"), "en");
    
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
	assertEquals(managerFr.getRoot(), new Namespace("vendor.test"));
	assertEquals(managerEn.getRoot(), new Namespace("vendor.test"));
    }
    
    @Test
    public void testTranslate() {
	assertEquals(managerFr.translate("t$translation.hello"), "Bonjour");
	assertEquals(managerFr.translate("hello"), "hello");
	assertEquals(managerFr.translate("t$translation.hello1"), Core.MISSING_DATA);
	
	assertEquals(managerEn.translate("t$translation.hello"), "Hello");
	assertEquals(managerEn.translate("hello"), "hello");
	assertEquals(managerEn.translate("t$translation.hello1"), Core.MISSING_DATA);
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
    
    @Test
    public void testGetValidLanguage() {
	List<String> availiableLangs = new ArrayList<>();
	availiableLangs.add("fr");
	availiableLangs.add("en");
	
	assertEquals(TranslateManager.getValidLanguage(availiableLangs, "fr", "en"), "fr");
	assertEquals(TranslateManager.getValidLanguage(availiableLangs, "en", "es", "fr"), "en");
	assertEquals(TranslateManager.getValidLanguage(availiableLangs, "es", "it", "de"), null);
    }

}
