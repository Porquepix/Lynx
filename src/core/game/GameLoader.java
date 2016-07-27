package core.game;

import java.io.File;
import java.io.FilenameFilter;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import core.Core;
import core.json.model.GameInfoModel;
import core.logging.Loggers;
import core.logging.LynxLogger;
import core.namespace.Extension;
import core.namespace.Namespace;
import core.translation.CacheFileTranslator;
import core.translation.Translator;

public class GameLoader {

    private static final LynxLogger logger = Loggers.getLogger(GameLoader.class);

    private static final Namespace GAME_DIRECTORY = new Namespace("games");

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
	logger.info("Loading games.");

	String[] gameDirectories = getGameDirectoriesName();
	for (String gameDirectory : gameDirectories) {
	    if (isValidGameDirectory(gameDirectory)) {
		Game game = loadGame(GAME_DIRECTORY.append(gameDirectory));
		this.games.add(game);
		logger.info("Game directory '{}' has successfully loaded.", gameDirectory);
	    }
	}
	Collections.sort(this.games);
    }

    private boolean isValidGameDirectory(String gameDirectory) {
	return Files.exists(GAME_DIRECTORY.append(gameDirectory)
		.append(Game.INFO_FILE).getResolver()
		.getFilePath(Extension.JSON));
    }

    private Game loadGame(Namespace gameDirectory) {
	Game game = new Game(gameDirectory);

	// assign translator based on the user language
	CacheFileTranslator gameTranslator = this.createGameTranlator(game);
	game.setTranslator(gameTranslator);

	return game;
    }

    private CacheFileTranslator createGameTranlator(Game game) {
	GameInfoModel gim = game.getInfo();
	String userLang = Translator.getValidLanguage(gim.getLanguages(),
		this.core.getUserSettings().getLang(), gim.getLanguage());
	return new CacheFileTranslator(game.getLangRoot(), userLang, core.getAppSettings().getTranslatorCacheSize());
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
