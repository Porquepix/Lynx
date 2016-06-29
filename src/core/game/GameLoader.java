package core.game;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import core.Core;
import core.exception.LynxException;
import core.game.facade.GameFacade;
import core.logging.Log;
import core.translation.TranslateManager;

/**
 * Load and store all available games.
 * 
 * @author Alexis
 *
 */
public class GameLoader {
	
	private static final Path GAME_DIRECTORY = Paths.get("games");
	private static final String GAME_INFO_FILE = "base.json";
	
	private List<Game> games;
	private Core core;
	
	public GameLoader(Core core) {
		this.games = new ArrayList<>();
		this.core = core;
		this.loadGames();
	}
	
	public List<GameFacade> getGames() {
		List<GameFacade> ret = new ArrayList<>();
		for (Game g : this.games) {
			ret.add(new GameFacade(g));
		}
		return ret;
	}
	
	public Game getGame(int gameNumber) {
		return this.games.get(gameNumber);
	}

	private void loadGames() {
		Log.get().info("Loading games.");
		
		DirectoryStream<Path> gameDirectoryStream = getGameDirectoryStream();
		for (Path gameDirectory : gameDirectoryStream) {
			if (this.containsInfoFile(gameDirectory)) {
				Game game = loadGame(gameDirectory);
				this.games.add(game);
				Log.get().info("Game '{}' has successfully loaded.", game.getInfo().getName());
			}
		}
		Collections.sort(this.games);
	}
	
	private Game loadGame(Path gameDirectory) {
		GameInfo gameInfo = new GameInfo(this.getInfoFile(gameDirectory));
		Game game = new Game(gameDirectory, gameInfo);
		
		// assign translator based on the user language
		TranslateManager gameTranslator = this.createGameTranlator(game, gameInfo);
		gameInfo.setTranslator(gameTranslator);
		game.setTranslator(gameTranslator);
		
		return game;
    }

	private TranslateManager createGameTranlator(Game game, GameInfo gameInfo) {
		String userLang = TranslateManager.getValidLanguage(gameInfo.getLangs(), this.core.getUserLang(), gameInfo.getLang());
		return new TranslateManager(game.getRoot(), userLang);
    }
	
	private Path getInfoFile(Path gameDirectory) {
		return gameDirectory.resolve(GAME_INFO_FILE);
	}

	private boolean containsInfoFile(Path gameDirectory) {
		return Files.exists(this.getInfoFile(gameDirectory));
	}

	private DirectoryStream<Path> getGameDirectoryStream() {
		try {
			return Files.newDirectoryStream(GAME_DIRECTORY);
		} catch (IOException e) {
			Log.get().warn("IOException ", e);
			try {
				this.createGameDirectoryIfNotExists();
				return Files.newDirectoryStream(GAME_DIRECTORY);
			} catch (IOException e1) {
				Log.get().error("IOException ", e);
				throw new LynxException("Impossible to find or to access the game direcotry.");
			}
		}
	}
	
	private void createGameDirectoryIfNotExists() throws IOException {
		if (!Files.exists(GAME_DIRECTORY)) {
			Log.get().debug("Trying to create the game directory...");
			Files.createDirectories(GAME_DIRECTORY);
			Log.get().debug("Game directory created...");
		}
    }

}
