package com.lynx.core.game;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.lynx.core.Core;
import com.lynx.core.json.model.GameInfoModel;
import com.lynx.core.logging.Loggers;
import com.lynx.core.logging.LynxLogger;
import com.lynx.core.namespace.FileFinder;
import com.lynx.core.namespace.Namespace;
import com.lynx.core.translation.CacheFileTranslator;
import com.lynx.core.translation.Translator;

public class GameLoader {

	private static final LynxLogger logger = Loggers.getLogger(GameLoader.class);

	private static final Namespace GAME_DIRECTORY = new Namespace("games");

	private List<IGame> games;
	private Core core;

	public GameLoader(Core core) {
		this.games = new ArrayList<>();
		this.core = core;
		this.loadGames();
	}

	public List<GameFacade> getGames() {
		return games.stream()
				.map((IGame game) -> new GameFacade(game))
				.collect(Collectors.toList());
	}

	public IGame getGame(int gameNumber) {
		return this.games.get(gameNumber);
	}

	private void loadGames() {
		logger.info("Loading games.");

		this.games = getGameDirectoriesName().stream()
				.map((String gameDir) -> GAME_DIRECTORY.append(gameDir))
				.filter((Namespace gameDir) -> isValidGameDirectory(gameDir))
				.map((Namespace gameDir) -> { 
					Game game = loadGame(gameDir); 
					logger.info("Game directory '{}' has successfully loaded.", gameDir); 
					return game; 
				})
				.sorted()
				.collect(Collectors.toList());
		
		logger.info("Loading done.");
	}

	private boolean isValidGameDirectory(Namespace gameDir) {
		return new FileFinder(gameDir.append(Game.INFO_FILE)).find() != null;
	}

	private Game loadGame(Namespace gameDirectory) {
		Game game = new Game(gameDirectory);

		// assign translator based on the user language
		Translator gameTranslator = this.createGameTranlator(game);
		game.setTranslator(gameTranslator);

		return game;
	}

	private CacheFileTranslator createGameTranlator(Game game) {
		GameInfoModel gim = game.getInfo();
		String userLang = Translator.getValidLanguage(gim.getLanguages(), this.core
		        .getUserSettings().getLang(), gim.getLanguage());
		return new CacheFileTranslator(game.getLangRoot(), userLang, core.getAppSettings()
		        .getTranslatorCacheSize());
	}

	private List<String> getGameDirectoriesName() {
		String[] files = new File(GAME_DIRECTORY.getKey())
				.list((File current, String name) -> new File(current, name).isDirectory());
		return Arrays.asList(files);
	}

}
