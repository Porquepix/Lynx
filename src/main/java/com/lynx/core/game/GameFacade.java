package com.lynx.core.game;

import com.lynx.core.game.node.NodeFacade;

public class GameFacade extends Facade {

    private Game game;
    private NodeFacade node;

    public GameFacade(Game game) {
	super(game.getTranslator());
	this.game = game;
    }

    public void start() {
	this.game.start();
	this.node = new NodeFacade(game.getCurrentNode(), translator);
    }

    public String getName() {
	return translator.translate(game.getInfo().getName());
    }

    public NodeFacade getCurrentNode() {
	return this.node;
    }

    public NodeFacade next(String answer) {
	if (this.game.next(answer)) {
	    this.node = new NodeFacade(game.getCurrentNode(), translator);
	    return this.node;
	}
	return null;
    }

}
