import java.util.Map;

import bsh.EvalError;
import core.json.controller.NodeController;
import core.json.model.node.NodeModel;
import core.key.FileKey;

public class Lynx {
	
	public static void main(String[] args) throws EvalError {
		FileKey fk = new FileKey("games.demo.story.consil0");
		NodeController tc = new NodeController(fk);
		Map<String, NodeModel> m = tc.fetch();
		
		System.out.println(m.get("1").getText());
		System.out.println(m.get("0").getText());
		
		System.out.println(m.get("0").getNexts().get(0).hasCondition());
		System.out.println(m.get("1").getNexts().get(1).getNode());
		
		System.out.println(m.get("1").getAnswer().hasRange());
		
		System.out.println(m.get("1").hasChoices());
		System.out.println(m.get("1").getChoices().get(0).getText());
		
		//ConsoleKernel ck = new ConsoleKernel();
		//ck.start();
	}

}
