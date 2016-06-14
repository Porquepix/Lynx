import core.Core;
import core.game.Game;

public class Lynx {
	
	public static void main(String[] args) {
		Core c = new Core();
		for (Game g : c.gameManager.getGames()) {
			System.out.println(g);
		}
	}

}
