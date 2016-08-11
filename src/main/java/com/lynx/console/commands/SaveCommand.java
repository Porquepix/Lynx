package com.lynx.console.commands;

import java.util.Arrays;

import com.lynx.console.Command;
import com.lynx.console.ConsoleKernel;

public class SaveCommand extends Command {

	public SaveCommand() {
	}

	@Override
	public String getName() {
		return "save";
	}

	@Override
	public String getDescription() {
		return "Save the state of the game.";
	}

	@Override
	public String getManual() {
		return "Usage: !save [-l|-f] <file>\n\n"
		        + "Save the state of the game in the file. Error if the file already exists.\n"
		        + "-l: Load a save from the file.\n"
		        + "-f: Force the core to save. If a file already exists overide it.";
	}

	@Override
	public void execute(ConsoleKernel kernel, String... args) {
		if (Arrays.asList(args).contains("-l")) {
			kernel.getSelectedGame().load("save");
			kernel.clear();
		} else {
			kernel.getSelectedGame().save("save");
		}
	}

}
