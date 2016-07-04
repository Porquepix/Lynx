package console.commands;

import static console.ConsoleHelper.*;

import console.Command;
import console.ConsoleKernel;

public class ManualCommand extends Command {

    @Override
    public String getName() {
	return "man";
    }

    @Override
    public String getDescription() {
	return "Provide help about a command.";
    }

    @Override
    public String getManual() {
	return "Usage : !man <command_name>";
    }

    @Override
    public void execute(ConsoleKernel kernel, String... args) {
	if (args.length <= 1) {
	    displayError(this.getManual() + "\n");
	    return;
	}

	Command cmd = Command.getCommands().get(args[1]);

	if (cmd == null) {
	    displayError("'" + args[1] + "': command not found ! \n");
	} else {
	    displayHighlight("!" + args[1]);
	    display("\n" + cmd.getDescription() + "\n\n"
		    + cmd.getManual() + "\n");
	}
    }

}
