import java.nio.file.Paths;

import bsh.EvalError;
import core.Core;
import core.translation.TranslateManager;

public class Lynx {
	
	public static void main(String[] args) throws EvalError {
		//Core c = new Core();
		TranslateManager tm = new TranslateManager(Paths.get("vendor"), "fr");
		System.out.println(tm.translate("Hello World !"));
		System.out.println(tm.translate("t$test.test"));
	}

}
