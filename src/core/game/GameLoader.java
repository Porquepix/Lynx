package core.game;

import java.io.File;
import java.io.FilenameFilter;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import core.Core;
import core.game.facade.GameFacade;
import core.json.model.GameInfoModel;
import core.key.FileKey;
import core.logging.Log;
import core.translation.TranslateManager;

/**
 * Load and store all available games.
 * 
 * @author Alexis
 *
 */
public class GameLoader {
	
	private static final FileKey GAME_DIRECTORY = new FileKey("games");
	
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
		
		String[] gameDirectories = getGameDirectoriesName();
		for (String gameDirectory : gameDirectories) {
			if ( isValidGameDirectory(gameDirectory) ) {
				Game game = loadGame(GAME_DIRECTORY.append(gameDirectory));
				this.games.add(game);
				Log.get().info("Game '{}' has successfully loaded.", game.getInfo().getName());				
			}
		}
		Collections.sort(this.games);
	}
	
	private boolean isValidGameDirectory(String gameDirectory) {
	    return Files.exists(GAME_DIRECTORY.append(gameDirectory).append(Game.INFO_FILE).getPath());
    }

	private Game loadGame(FileKey gameDirectory) {
		Game game = new Game(gameDirectory);
		
		// assign translator based on the user language
		TranslateManager gameTranslator = this.createGameTranlator(game);
		game.setTranslator(gameTranslator);
		
		return game;
    }

	private TranslateManager createGameTranlator(Game game) {
		GameInfoModel gim = game.getInfo();
		String userLang = TranslateManager.getValidLanguage(gim.getLanguages(), this.core.getUserLang(), gim.getLanguage());
		return new TranslateManager(game.getRoot(), userLang);
    }
	
	private String[] getGameDirectoriesName() {
		return new File(GAME_DIRECTORY.getKey()).list(new FilenameFilter() {
			@Override
			public boolean accept(File current, String name) {
				return new File(current, name).isDirectory();
			}
		});
	}

}
