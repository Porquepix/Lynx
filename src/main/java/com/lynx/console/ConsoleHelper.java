package com.lynx.console;

import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.AnsiConsole;

public class ConsoleHelper {

    public static void display(String message) {
	Ansi a = Ansi.ansi();
	a.reset();
	a.a(message);
	a.reset();
	AnsiConsole.out.print(a);
    }

    public static void displayUnderline(String message) {
	Ansi a = Ansi.ansi();
	a.a(Ansi.Attribute.UNDERLINE);
	a.a(message);
	a.a(Ansi.Attribute.UNDERLINE_OFF);
	a.reset();
	AnsiConsole.out.print(a);
    }

    public static void displayHighlight(String message) {
	Ansi a = Ansi.ansi();
	a.fg(Ansi.Color.YELLOW);
	a.bold();
	a.bg(Ansi.Color.CYAN);
	a.a(message);
	a.reset();
	AnsiConsole.out.print(a);
    }

    public static void displaySuccess(String success) {
	Ansi a = Ansi.ansi();
	a.fg(Ansi.Color.GREEN);
	a.a(success);
	a.reset();
	AnsiConsole.out.print(a);
    }

    public static void displayError(String error) {
	Ansi a = Ansi.ansi();
	a.fg(Ansi.Color.RED);
	a.a(error);
	a.reset();
	AnsiConsole.out.print(a);
    }

}
