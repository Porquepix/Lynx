package com.lynx.core.json;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.lynx.core.json.controller.BaseController;
import com.lynx.core.json.model.AppSettingsModel;
import com.lynx.core.json.model.GameInfoModel;
import com.lynx.core.namespace.Namespace;

public class BaseControllerTest {

    private Namespace testFile1 = new Namespace("vendor.test.BaseControllerTest1");
    private Namespace testFile2 = new Namespace("vendor.test.BaseControllerTest2");
    private Namespace testFile3 = new Namespace("vendor.test.BaseControllerTest3");
    private BaseController<GameInfoModel> controller1 = new BaseController<>(testFile1,
	    GameInfoModel.class);
    private BaseController<AppSettingsModel> controller2 = new BaseController<>(testFile2,
	    AppSettingsModel.class);
    private BaseController<GameInfoModel> controller3 = new BaseController<>(testFile3,
	    GameInfoModel.class);

    @Test
    public void testFetch1() {
	GameInfoModel model = controller1.fetch();

	List<String> languages = new ArrayList<>();
	languages.add("fr");
	languages.add("es");

	List<String> declare = new ArrayList<>();
	declare.add("i");
	declare.add("j");

	assertEquals(model.getName(), "TestGame");
	assertEquals(model.getDescription(), "t$info.description");
	assertEquals(model.getLanguages(), languages);
	assertEquals(model.getLanguage(), "fr");
	assertEquals(model.getDeclaration(), declare);
	assertEquals(model.getBoot(), "boot.boot");
    }

    @Test
    public void testFetch2() {
	AppSettingsModel model = controller2.fetch();

	assertTrue(model.getDebug());
	assertEquals(model.getNodeCacheSize(), 10);
	assertEquals(model.getTranslatorCacheSize(), 5);
    }

    @Test
    public void testStore() {
	GameInfoModel model = controller1.fetch();
	controller3.store(model);
	model = controller3.fetch();

	List<String> languages = new ArrayList<>();
	languages.add("fr");
	languages.add("es");

	List<String> declare = new ArrayList<>();
	declare.add("i");
	declare.add("j");

	assertEquals(model.getName(), "TestGame");
	assertEquals(model.getDescription(), "t$info.description");
	assertEquals(model.getLanguages(), languages);
	assertEquals(model.getLanguage(), "fr");
	assertEquals(model.getDeclaration(), declare);
	assertEquals(model.getBoot(), "boot.boot");
    }

}
