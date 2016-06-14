package core.game;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import core.exception.LynxException;
import core.logging.Log;

public class GameManager {
	
	private static final Path GAME_DIRECTORY = Paths.get("games");
	private static final String GAME_INFO_FILE = "base.json";
	
	private List<Game> games;
	
	public GameManager() {
		this.games = new ArrayList<>();
		this.loadGames();
	}
	
	public List<Game> getGames() {
		return Collections.unmodifiableList(this.games);
	}

	private void loadGames() {
		Log.get().info("Loading games.");
		DirectoryStream<Path> gameDirStream = getGamesDirStream();
		for (Path gameDir : gameDirStream) {
			Path gameInfoFile = gameDir.resolve(GAME_INFO_FILE);
			if (Files.exists(gameInfoFile)) {
				GameInfo gi = new GameInfo(gameInfoFile);
				Game g = new Game(gameDir, gi);
				this.games.add(g);
			}
		}
		Collections.sort(this.games);
		Log.get().info("Games have successfully loaded.");
	}

	private DirectoryStream<Path> getGamesDirStream() {
		try {
			return Files.newDirectoryStream(GAME_DIRECTORY);
		} catch (IOException e) {
			Log.get().warn("IOException ", e);
			try {
				if (!Files.exists(GAME_DIRECTORY)) {
					Log.get().debug("Trying to create the game directory...");
					Files.createDirectories(GAME_DIRECTORY);
					Log.get().debug("Game directory created...");
				}
				return Files.newDirectoryStream(GAME_DIRECTORY);
			} catch (IOException e1) {
				Log.get().error("IOException ", e);
				throw new LynxException("Impossible to find or to access the game direcotry.");
			}
		}
	}
	

}
