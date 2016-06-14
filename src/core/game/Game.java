package core.game;

import java.nio.file.Path;

public class Game implements Comparable<Game> {

	private Path root;
	private GameInfo info;
	
	public Game(Path gameDir, GameInfo info) {
		this.root = gameDir;
		this.info = info;
	}

	@Override
	public int compareTo(Game game) {
		return this.info.getName().compareTo(game.info.getName());
	}
	
	public String toString() {
		return root.toString();
	}

}
