package console;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import console.commands.List;
import console.commands.Manual;

public abstract class Command {

	private static Map<String, Command> commands = new HashMap<>();
	
	public static Map<String, Command> getCommands() {
		return Collections.unmodifiableMap(commands);
	}
	
	public static final Command LIST = new List();
	public static final Command MAN = new Manual();
	
	public Command() {
		commands.put(getName(), this);
	}
	
	public Command(String name) {
		commands.put(name, this);
	}
	
	public abstract String getName();
	public abstract String getDescription();
	public abstract String getManual();
	public abstract void execute(String... args);
	
}
