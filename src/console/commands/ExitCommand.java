package console.commands;

import console.Command;
import console.ConsoleKernel;

public class ExitCommand extends Command {

	@Override
	public String getName() {
		return "exit";
	}

	@Override
	public String getDescription() {
		return "Close the terminal properly.";
	}

	@Override
	public String getManual() {
		return "-";
	}

	@Override
	public void execute(ConsoleKernel kernel, String... args) {
		kernel.display("Good bye !");
		kernel.exit();
	}

}