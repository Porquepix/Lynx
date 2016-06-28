package console.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import console.Command;
import console.ConsoleKernel;

public class ListCommand extends Command {
	
	@Override
    public String getName() {
	    return "list";
    }

	@Override
    public String getDescription() {
	    return "List all available commands.";
    }

	@Override
    public String getManual() {
	    return "-";
    }

	@Override
    public void execute(ConsoleKernel kernel, String... args) {
		List<Command> commands = new ArrayList<>(Command.getCommands().values());
		Collections.sort(commands);
	    for (Command c : commands) {
	    	kernel.displayHighlight("!" + c.getName());
	    	kernel.display(" - " + c.getDescription() + "\n");
	    }
    }

}
