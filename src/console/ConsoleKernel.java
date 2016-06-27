package console;

import java.util.Scanner;
import java.util.regex.Pattern;

import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.AnsiConsole;

import core.Core;
import core.game.Answer;
import core.game.Game;
import core.json.JsonContent;
import core.json.JsonFile;

public class ConsoleKernel {
	
	private static final JsonFile CONSOLE = new JsonFile("vendor/config/console.conf");
	private static final Pattern COMMAND_PATTERN = Pattern.compile("^!([a-z]+)(\\s(.*))?");

	private Scanner scanner;
	private JsonContent config;
	private Core gameCore;
	
	public ConsoleKernel() {
		this.scanner = new Scanner(System.in);
		this.config = CONSOLE.loadContentOrFail().getContent();
		AnsiConsole.systemInstall();
	}
	
	public void start() {
		printStartMessage();
		
		this.gameCore = new Core();
		
		while (true) {
			selectGame();
			play();
		}
	}

	public void stop() {
		AnsiConsole.systemUninstall();
	}

	private void printStartMessage() {
		Ansi a = Ansi.ansi();
		
		a.bg(Ansi.Color.CYAN).fgDefault();
		a.a("+---------------------------------------------------+\n|");
		a.fg(Ansi.Color.YELLOW).bold().a("                   LYNX TERMINAL                   ");
		a.boldOff().fgDefault().a("|\n|");
		a.fgBlack().a("   developed by Porquepix [github.com/Porquepix]   ");
		a.fgDefault().a("|\n");
		a.a("+---------------------------------------------------+\n");
		a.reset();
		
		AnsiConsole.out.println(a);
    }
	
	private void selectGame() {
	    AnsiConsole.out.println("Select a game:");
	    
	    Game selectedGame = null;
		String input;
		while (selectedGame == null) {
			int i = 0;
			for (Game g : this.gameCore.getGames()) {
				Ansi a = Ansi.ansi();
				
				a.fg(Ansi.Color.YELLOW).bold().bg(Ansi.Color.CYAN).a(i);
				a.reset().a(" - " + g.getName() + "\t\t");
				AnsiConsole.out.print(a);
				
				i++;
			}
			AnsiConsole.out.print("\n");
			input = this.getAnwser();
			
			Answer userAnswer = new Answer(input);
			selectedGame = this.gameCore.selectGame(userAnswer.toInteger());
			
			if (selectedGame == null) {
				Ansi a = Ansi.ansi();
				a.fg(Ansi.Color.RED);
				a.a("Invalid input ! ");
				a.reset();
				AnsiConsole.out.println(a);
			}
		}	    
    }
	
	private void play() {
	    // TODO Auto-generated method stub
	    
    }
	
	public String getAnwser() {
		displayPrompt();
		String ret = this.scanner.nextLine();
		while (this.isCommand(ret)) {
			String[] args = ret.split("\\s+");
			String cmdName = args[0].substring(1);
			Command c = Command.getCommands().get(cmdName);
			if (c != null)
				c.execute(args);
			else {
				Ansi a = Ansi.ansi();
				a.fg(Ansi.Color.RED);
				a.a("Command not found ! ");
				a.reset();
				AnsiConsole.out.println(a);
			}
			
			displayPrompt();
			ret = this.scanner.nextLine();
		}
		return ret;
	}

	private void displayPrompt() {
		AnsiConsole.out.print(this.config.getAsString("prompt", "$>") + " ");
		AnsiConsole.out.flush();
    }

	private boolean isCommand(String s) {
	    return COMMAND_PATTERN.matcher(s).find();
    }
	
}
