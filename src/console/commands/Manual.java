package console.commands;

import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.AnsiConsole;

import console.Command;

public class Manual extends Command {

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
    public void execute(String... args) {
		String cmdName = args.length > 1 ? args[1] : "man";
		Command cmd = Command.getCommands().get(cmdName);
		
		if (cmd == null) {
			Ansi a = Ansi.ansi();
			a.fg(Ansi.Color.RED);
			a.a("Parameter command not found ! ");
			a.reset();
			AnsiConsole.out.println(a);
		} else {
			Ansi a = Ansi.ansi();
			a.fg(Ansi.Color.YELLOW).bold().bg(Ansi.Color.CYAN).a("!" + cmdName);
			a.reset().a("\n" + cmd.getDescription() + "\n\n" + cmd.getManual());
			AnsiConsole.out.println(a);			
		}    
    }

}
