package com.lynx.console.commands;

import com.lynx.console.Command;
import com.lynx.console.ConsoleKernel;

public class ClearCommand extends Command {

	@Override
	public String getName() {
		return "clear";
	}

	@Override
	public String getDescription() {
		return "Clear the terminal to only display the last demand of answer.";
	}

	@Override
	public String getManual() {
		return "-";
	}

	@Override
	public void execute(ConsoleKernel kernel, String... args) {
		kernel.clear();
	}

}
