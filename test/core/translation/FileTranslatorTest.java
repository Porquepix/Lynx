package core.translation;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import core.Core;
import core.namespace.Namespace;

public class FileTranslatorTest {
    
    private Translator t1 = new FileTranslator(new Namespace("vendor.test.lang.fr.translation"));
    private Translator t2 = new FileTranslator(new Namespace("vendor.test.lang.en.translation"));
    
    @Test
    public void testTranslate() {
	assertEquals(t1.translate("hello"), "Bonjour");
	assertEquals(t1.translate("hello1"), Core.MISSING_DATA);
	
	assertEquals(t2.translate("hello"), "Hello");
	assertEquals(t2.translate("hello1"), Core.MISSING_DATA);
    }
    
    @Test
    public void testTranslateAll() {
	List<String> expected = new ArrayList<>();
	expected.add("Bonjour");
	expected.add("Monde");
	
	List<String> test = new ArrayList<>();
	test.add("hello");
	test.add("world");
	
	assertEquals(t1.translateAll(test), expected);
    }

}
