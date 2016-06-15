import java.util.Map.Entry;
import java.util.Scanner;

import bsh.EvalError;
import core.Core;
import core.game.Game;
import core.game.Request;

public class Lynx {
	
	public static void main(String[] args) throws EvalError {
		Core c = new Core();
		
		String input;
		do {
			int i = 0;
			for (Game g : c.getGames()) {
				System.out.print(i + " - " + g.getName() + "\t\t");
				i++;
			}
			System.out.print("\n$> ");
			input = new Scanner(System.in).nextLine();
		} while(c.selectGame(new Request(Integer.parseInt(input))) == null);
		
		Game g = c.selectGame(new Request(Integer.parseInt(input)));
		g.start();
		
		while (true) {
			System.out.print(g.getCurrentText());
			
			input = null;
			do {
				if (input != null) {
					System.out.print("Wrong anwser");
				}
				System.out.print("\n$> ");
				input = new Scanner(System.in).nextLine();	
			} while (!g.nextContext(new Request(input)));
		}
	}

}
