import java.io.IOException;

import core.json.JsonContent;
import core.json.JsonFile;


public class Lynx {
	
	public static void main(String[] args) {
		JsonContent tmp = new JsonFile("vendor/test/test.json").getContent();
		
		JsonFile f = new JsonFile("vendor/test/testWrite.json");
		f.setContent(tmp);
		
		tmp.put("testInt", 12);
		tmp.put("testWrite", "ok");
		tmp.put("testArray", new Object[] {55, 11.11, "ok"});
		
		try {
			f.write();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
