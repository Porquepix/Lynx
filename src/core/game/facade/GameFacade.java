package core.game.facade;

import core.game.Answer;
import core.game.Game;
import core.translation.CacheFileTranslator;

public class GameFacade {

    private Game game;
    private StateNodeFacade node;
    private CacheFileTranslator translator;

    public GameFacade(Game game) {
	this.game = game;
	this.translator = game.getTranslator();
    }

    public void start() {
	this.game.start();
	this.node = new StateNodeFacade(this.game.getCurrentNode(),
		this.game.getTranslator());
    }

    public String getName() {
	return translator.translate(this.game.getInfo().getName());
    }

    public StateNodeFacade getCurrentNode() {
	return this.node;
    }

    public StateNodeFacade next(Answer a) {
	if (this.game.next(a)) {
	    this.node = new StateNodeFacade(this.game.getCurrentNode(),
		    this.game.getTranslator());
	    return this.node;
	}
	return null;
    }

}
