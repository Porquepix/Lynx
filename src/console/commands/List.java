package console.commands;

import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.AnsiConsole;

import console.Command;

public class List extends Command {
	
	@Override
    public String getName() {
	    return "list";
    }

	@Override
    public String getDescription() {
	    return "List all availiable commands.";
    }

	@Override
    public String getManual() {
	    return "No parameters !";
    }

	@Override
    public void execute(String... args) {
	    for (Command c : Command.getCommands().values()) {
	    	Ansi a = Ansi.ansi();
	    	a.fg(Ansi.Color.YELLOW).bold().bg(Ansi.Color.CYAN).a("!" + c.getName());
			a.reset().a(" - " + c.getDescription());
			AnsiConsole.out.println(a);
	    }
    }

}
