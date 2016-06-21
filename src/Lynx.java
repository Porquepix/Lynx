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
			
			input = "";
			boolean first = false;
			while (!g.nextContext(new Request(input))) {
				if (first) {
					System.out.print("Wrong anwser");
				} else {
					first = !first;
				}
				System.out.print("\n$> ");
				input = new Scanner(System.in).nextLine();	
			}
			if (!first) {
				System.out.print("\n");
			}
		}
	}

}
