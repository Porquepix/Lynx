import java.util.Scanner;

import bsh.EvalError;
import console.ConsoleKernel;
import core.Core;
import core.game.Answer;
import core.game.Game;

public class Lynx {
	
	public static void main(String[] args) throws EvalError {
		ConsoleKernel ck = new ConsoleKernel();
		ck.start();
		ck.stop();
		
	
		
//		Game g = c.selectGame(new Answer(input).toInteger());
//		g.start();
//		
//		while (true) {
//			System.out.print(g.getNode().getText());
//			
//			input = "";
//			boolean first = false;
//			while ( g.getNext(new Answer(input)) != null ) {
//				if (first) {
//					System.out.print("Wrong anwser");
//				} else {
//					first = !first;
//				}
//				System.out.print("\n$> ");
//				input = new Scanner(System.in).nextLine();	
//			}
//			if (!first) {
//				System.out.print("\n");
//			}
//		}
	}

}
