package console;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import console.commands.ClearCommand;
import console.commands.ExitCommand;
import console.commands.ListCommand;
import console.commands.ManualCommand;

public abstract class Command implements Comparable<Command> {

	private static final Pattern COMMAND_PATTERN = Pattern.compile("^!([a-z]+)(\\s(.*))?");
	
	private static Map<String, Command> commands = new HashMap<>();
	
	public static Map<String, Command> getCommands() {
		return Collections.unmodifiableMap(commands);
	}
	
	public static final Command LIST = new ListCommand();
	public static final Command MAN = new ManualCommand();
	public static final Command CLEAR = new ClearCommand();
	public static final Command EXIT = new ExitCommand();
	
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
		Matcher m =  COMMAND_PATTERN.matcher(s);
		if (m.matches()) {
			String cmdName = m.group(1);
			return commands.get(cmdName);
		}
		return null;
    }
	
}
