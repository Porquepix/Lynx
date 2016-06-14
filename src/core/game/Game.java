package core.game;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class Game implements Comparable<Game> {

	private Path root;
	private GameInfo info;
	private Map<String, Object> variables;
	
	public Game(Path gameDir, GameInfo info) {
		this.root = gameDir;
		this.info = info;
		this.variables = new HashMap<>();
	}

	@Override
	public int compareTo(Game game) {
		return this.info.getName().compareTo(game.info.getName());
	}
	
	public String toString() {
		return root.toString();
	}

	public String getName() {
		return this.info.getName();
	}

}
