package com.lynx.console.commands;

import com.lynx.console.Command;
import com.lynx.console.ConsoleHelper;
import com.lynx.console.ConsoleKernel;

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
		if ( args.length <= 1 ) {
			ConsoleHelper.displayError(this.getManual() + "\n");
			return;
		}

		Command cmd = Command.getCommands().get(args[1]);

		if ( cmd == null ) {
			ConsoleHelper.displayError("'" + args[1] + "': command not found ! \n");
		} else {
			ConsoleHelper.displayHighlight("!" + args[1]);
			ConsoleHelper.display("\n" + cmd.getDescription() + "\n\n" + cmd.getManual() + "\n");
		}
	}

}
