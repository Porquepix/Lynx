package core.translation;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class TranslatorTest {

    @Test
    public void testGetValidLanguage() {
	List<String> availiableLangs = new ArrayList<>();
	availiableLangs.add("fr");
	availiableLangs.add("en");

	assertEquals(Translator.getValidLanguage(availiableLangs, "fr", "en"), "fr");
	assertEquals(Translator.getValidLanguage(availiableLangs, "en", "es", "fr"), "en");
	assertEquals(Translator.getValidLanguage(availiableLangs, "es", "it", "de"), null);
    }

}
