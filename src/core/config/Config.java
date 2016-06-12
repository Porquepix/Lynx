package core.config;

import core.json.JsonFile;


public class Config {
	
	public static final JsonFile APP = new JsonFile("vendor/config/app.conf");
	public static final JsonFile CONSOLE = new JsonFile("vendor/config/console.conf");
	public static final JsonFile GUI = new JsonFile("vendor/config/gui.conf");
	public static final JsonFile USER = new JsonFile("vendor/config/user.conf");

}
