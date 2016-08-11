package com.lynx.console;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.lynx.console.commands.ClearCommand;
import com.lynx.console.commands.ExitCommand;
import com.lynx.console.commands.ListCommand;
import com.lynx.console.commands.ManualCommand;
import com.lynx.console.commands.SaveCommand;

public abstract class Command implements Comparable<Command> {

	private static final String COMMAND_REGEX = "^!([a-z]+)(\\s(.*))?";
	private static final Pattern COMMAND_PATTERN = Pattern.compile(COMMAND_REGEX);

	private static Map<String, Command> commands = new HashMap<>();

	public static Map<String, Command> getCommands() {
		return Collections.unmodifiableMap(commands);
	}

	public static final Command LIST = new ListCommand();
	public static final Command MAN = new ManualCommand();
	public static final Command CLEAR = new ClearCommand();
	public static final Command EXIT = new ExitCommand();
	public static final Command SAVE = new SaveCommand();

	public Command() {
		commands.put(getName(), this);
	}

	public Command(String name) {
		commands.put(name, this);
	}

	public abstract String getName();

	public abstract String getDescription();

	public abstract String getManual();

	public abstract void execute(ConsoleKernel kernel, String... args);

	@Override
	public final int compareTo(Command other) {
		return this.getName().compareTo(other.getName());
	}

	public static boolean isCommand(String s) {
		return COMMAND_PATTERN.matcher(s).find();
	}

	public static Command findByName(String s) {
		Matcher m = COMMAND_PATTERN.matcher(s);
		if ( m.matches() ) {
			String cmdName = m.group(1);
			return commands.get(cmdName);
		}
		return null;
	}

}
