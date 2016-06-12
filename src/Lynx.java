import core.Core;
import core.json.JsonFile;

public class Lynx {
	
	public static void main(String[] args) {
		Core c = new Core();
		JsonFile jf = new JsonFile("vendor/config/gui.conf");
		jf.getContent();
	}

}
