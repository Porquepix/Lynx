package console;

import static console.ConsoleHelper.*;

import java.util.Scanner;

import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.AnsiConsole;

import core.Core;
import core.game.Answer;
import core.game.facade.GameFacade;
import core.game.facade.StateNodeFacade;
import core.json.controller.BaseController;
import core.namespace.Namespace;

public class ConsoleKernel {

    private static final Namespace CONSOLE = new Namespace("vendor.config.console");

    private Scanner scanner;
    private ConsoleModel config;
    private Core gameCore;
    private GameFacade selectedGame;

    private boolean exit = false;
    private boolean clear = false;

    public ConsoleKernel() {
	this.scanner = new Scanner(System.in);

	BaseController<ConsoleModel> consoleController = new BaseController<>(
		CONSOLE, ConsoleModel.class);
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
	a.fg(Ansi.Color.YELLOW).bold()
		.a("                   LYNX TERMINAL                   ");
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
	while (!exit && selectedGame == null) {
	    AnsiConsole.out.println("Select a game:");

	    int i = 0;
	    for (GameFacade g : gameCore.getGames()) {
		displayHighlight(Integer.toString(i));

		Ansi a = Ansi.ansi();
		a.reset().a(" - " + g.getName() + "\t\t");
		AnsiConsole.out.print(a);

		i++;
	    }
	    AnsiConsole.out.print("\n");
	    input = getAnswer();

	    if (input != null) {
		Answer userAnswer = new Answer(input);
		this.selectedGame = gameCore.selectGame(userAnswer.toInteger());

		if (selectedGame != null) {
		    selectedGame.start();
		} else {
		    displayError("Invalid input ! \n");
		}
	    }
	}
    }

    private void play() {
	while (!exit) {
	    if (selectedGame.getCurrentNode().hasAuthor()) {
		displayUnderline(selectedGame.getCurrentNode().getAuthor()
			+ ":");
	    }
	    display(" " + selectedGame.getCurrentNode().getText() + "\n");

	    if (!selectedGame.getCurrentNode().isVoidType()) {
		if (selectedGame.getCurrentNode().isAnswerType()) {
		    displayChoices();
		}
		String input = this.getAnswer();

		if (input != null) {
		    Answer userAnswer = new Answer(input);
		    StateNodeFacade next = selectedGame.next(userAnswer);

		    if (next == null) {
			displayError("Invalid input ! \n");
		    }
		}
	    } else {
		selectedGame.next(new Answer("null"));
	    }
	}
    }

    private void displayChoices() {
	int i = 0;
	for (String choice : selectedGame.getCurrentNode().getChoices()) {
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
	    ret = scanner.nextLine();

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
	if (selectedGame != null) {
	    AnsiConsole.out.print("(" + selectedGame.getName() + ") ");
	}
	AnsiConsole.out.print(config.getPrompt());
	AnsiConsole.out.print(" ");
	AnsiConsole.out.flush();
    }

}
