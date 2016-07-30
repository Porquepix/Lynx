package console;

import java.util.List;
import java.util.Scanner;

import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.AnsiConsole;

import core.Core;
import core.game.GameFacade;
import core.game.answer.Answer;
import core.game.node.NodeFacade;
import core.json.controller.BaseController;
import core.namespace.Namespace;
import core.validation.GlobalValidatorBuilder;
import core.validation.Validator;

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

	BaseController<ConsoleModel> consoleController = new BaseController<>(CONSOLE,
	        ConsoleModel.class);
	this.config = consoleController.fetch();

	AnsiConsole.systemInstall();
    }

    public void start() {
	displayHeader();

	this.gameCore = Core.getInstance();

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
	List<GameFacade> games = gameCore.getGames();

	String input;
	while (!exit && selectedGame == null) {
	    AnsiConsole.out.println("Select a game:");

	    int i = 0;
	    for (GameFacade g : games) {
		ConsoleHelper.displayHighlight(Integer.toString(i));

		Ansi a = Ansi.ansi();
		a.reset().a(" - " + g.getName() + "\t\t");
		AnsiConsole.out.print(a);

		i++;
	    }
	    AnsiConsole.out.print("\n");
	    input = getAnswer();

	    if (input != null) {
		Answer userAnswer = new Answer(input);
		Validator validator = new GlobalValidatorBuilder().type(Integer.class, true)
		        .range(0, games.size()).build();

		if (validator.validate(userAnswer.getValue())) {
		    selectedGame = games.get((int) userAnswer.getValue());
		    selectedGame.start();
		} else {
		    ConsoleHelper.displayError("Invalid input ! \n");
		}
	    }
	}
    }

    private void play() {
	while (!exit) {
	    if (selectedGame.getCurrentNode().hasAuthor()) {
		ConsoleHelper.displayUnderline(selectedGame.getCurrentNode().getAuthor() + ":");
	    }
	    ConsoleHelper.display(" " + selectedGame.getCurrentNode().getText() + "\n");

	    if (!selectedGame.getCurrentNode().isSkippable()) {
		if (selectedGame.getCurrentNode().isClosedAnswer()) {
		    displayChoices();
		}
		String input = this.getAnswer();

		if (input != null) {
		    NodeFacade next = selectedGame.next(input);

		    if (next == null) {
			ConsoleHelper.displayError("Invalid input ! \n");
		    }
		}
	    } else {
		selectedGame.next("null");
	    }
	}
    }

    private void displayChoices() {
	int i = 0;
	for (String choice : selectedGame.getCurrentNode().getChoices()) {
	    ConsoleHelper.displayHighlight(Integer.toString(i));

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
	    ConsoleHelper
		    .displayError("Command not found ! (Type '!list' to get the list of all available commands) \n");
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
