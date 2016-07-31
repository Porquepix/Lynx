package com.lynx.core.json;

import static org.junit.Assert.*;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.lynx.core.json.controller.TranslationController;
import com.lynx.core.json.model.TranslationModel;
import com.lynx.core.namespace.Namespace;

public class TranslationControllerTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Namespace testFile = new Namespace("vendor.test.TranslationControllerTest");
    private TranslationController controller = new TranslationController(testFile);

    @Test
    public void testFetch() {
	TranslationModel model = controller.fetch();

	assertEquals(model.getTranslation("hello"), "hello");
	assertEquals(model.getTranslation("name"), "My name is John Doe");
	assertNull(model.getTranslation("empty"));
    }

    @Test
    public void testStore() {
	thrown.expect(UnsupportedOperationException.class);
	controller.store(controller.fetch());
    }

}
