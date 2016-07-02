package console;

import java.util.Scanner;

import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.AnsiConsole;

import core.Core;
import core.game.Answer;
import core.game.facade.GameFacade;
import core.game.facade.StateNodeFacade;
import core.json.controller.BaseController;
import core.key.FileKey;

public class ConsoleKernel {
	
	private static final FileKey CONSOLE = new FileKey("vendor.config.console");

	private Scanner scanner;
	private ConsoleModel config;
	private Core gameCore;
	private GameFacade selectedGame;
	
	private boolean exit = false;
	private boolean clear = false;
	
	public ConsoleKernel() {
		this.scanner = new Scanner(System.in);
		
		BaseController<ConsoleModel> consoleController = new BaseController<>(CONSOLE, ConsoleModel.class);
		this.config = consoleController.fetch();
		
		AnsiConsole.systemInstall();
	}
	
	public void start() {
		displayHeader();
		
		this.gameCore = new Core();
		
		while (!exit) {
			selectGame();
			play();
		}
	}

	public void exit() {
		this.exit = true;
		AnsiConsole.systemUninstall();
	}
	
	public void clear() {
		this.clear = true;
		Ansi a = Ansi.ansi().eraseScreen();
		AnsiConsole.out.print(a);
	}

	private void displayHeader() {
		Ansi a = Ansi.ansi();
		
		a.bg(Ansi.Color.CYAN).fgDefault();
		a.a("+---------------------------------------------------+\n|");
		a.fg(Ansi.Color.YELLOW).bold().a("                   LYNX TERMINAL                   ");
		a.boldOff().fgDefault().a("|\n|");
		a.fgBlack().a("   developed by Porquepix [github.com/Porquepix]   ");
		a.fgDefault().a("|\n");
		a.a("+---------------------------------------------------+\n\n");
		a.reset();
		a.a("Tips: type '!list' to get the list of all available commands.\n");
		
		AnsiConsole.out.println(a);
    }
	
	private void selectGame() {
		String input;
		while (!exit && this.selectedGame == null) {
			AnsiConsole.out.println("Select a game:");
			
			int i = 0;
			for (GameFacade g : this.gameCore.getGames()) {
				displayHighlight(Integer.toString(i));
				
				Ansi a = Ansi.ansi();				
				a.reset().a(" - " + g.getName() + "\t\t");
				AnsiConsole.out.print(a);
				
				i++;
			}
			AnsiConsole.out.print("\n");
			input = this.getAnswer();
			
			if (input != null) {
				Answer userAnswer = new Answer(input);
				this.selectedGame = this.gameCore.selectGame(userAnswer.toInteger());
				
				if (this.selectedGame != null) {
					this.selectedGame.start();
				} else {
					displayError("Invalid input ! \n");
				}
			}
		}	    
    }
	
	private void play() {
		while (!exit) {
			if (this.selectedGame.getCurrentNode().hasAuthor()) {
				displayUnderline(this.selectedGame.getCurrentNode().getAuthor() + ":");
			}
			display(" " + this.selectedGame.getCurrentNode().getText() + "\n");

			if (!this.selectedGame.getCurrentNode().isVoidType()) {
				if (this.selectedGame.getCurrentNode().isAnswerType()) {
					displayChoices();
				}
				String input = this.getAnswer();
				
				if (input != null) {
					Answer userAnswer = new Answer(input);
					StateNodeFacade next = this.selectedGame.next(userAnswer);
					
					if (next == null) {
						displayError("Invalid input ! \n");
					}
				}
			} else {
				this.selectedGame.next(new Answer("null"));
			}
		}
    }
	
	private void displayChoices() {
	   int i = 0;
	   for (String choice : this.selectedGame.getCurrentNode().getChoices()) {
			displayHighlight(Integer.toString(i));
			
			Ansi a = Ansi.ansi();				
			a.reset().a(" - " + choice + "\t\t");
			AnsiConsole.out.print(a);
			
			i++;
	   }
	   AnsiConsole.out.print("\n");
    }

	public String getAnswer() {
		String ret = null;
		while (ret == null) {
			displayPrompt();
			ret = this.scanner.nextLine();
			
			if (Command.isCommand(ret)) {
				executeCommand(ret);
				ret = null;
				
				if (clear || exit) {
					break;
				}
			} 
		}
		return ret;
	}

	private void executeCommand(String ret) {
		Command c = Command.findByName(ret);
		if (c != null) {
			String[] args = ret.split("\\s+");
			c.execute(this, args);
		} else {
			displayError("Command not found ! (Type '!list' to get the list of all available commands) \n");
		}	    
    }

	private void displayPrompt() {
		if (this.selectedGame != null) {
			AnsiConsole.out.print("(" + this.selectedGame.getName() + ") ");
		}
		AnsiConsole.out.print(config.getPrompt());
		AnsiConsole.out.print(" ");
		AnsiConsole.out.flush();
    }
	
	public void display(String message) {
		Ansi a = Ansi.ansi();
		a.reset();
		a.a(message);
		a.reset();
		AnsiConsole.out.print(a);	
	}
	
	public void displayUnderline(String message) {
		Ansi a = Ansi.ansi();
		a.a(Ansi.Attribute.UNDERLINE);
		a.a(message);
		a.a(Ansi.Attribute.UNDERLINE_OFF);
		a.reset();
		AnsiConsole.out.print(a);	
	}
	
	public void displayHighlight(String message) {
		Ansi a = Ansi.ansi();
		a.fg(Ansi.Color.YELLOW);
		a.bold();
		a.bg(Ansi.Color.CYAN);
		a.a(message);
		a.reset();
		AnsiConsole.out.print(a);		
	}
	
	public void displayError(String error) {
		Ansi a = Ansi.ansi();
		a.fg(Ansi.Color.RED);
		a.a(error);
		a.reset();
		AnsiConsole.out.print(a);
	}

}
