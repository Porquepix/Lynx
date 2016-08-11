package com.lynx.core.game;

import com.lynx.core.game.node.NodeFacade;
import com.lynx.core.game.save.SaveManager;

public class GameFacade extends Facade {

	private IGame game;

	public GameFacade(IGame game) {
		super(game.getTranslator());
		this.game = game;
	}

	public void start() {
		this.game.start();
	}

	public String getName() {
		return translator.translate(game.getInfo().getName());
	}

	public NodeFacade getCurrentNode() {
		return new NodeFacade(game.getCurrentNode(), translator);
	}

	public NodeFacade next(String answer) {
		if ( this.game.next(answer) ) {
			return this.getCurrentNode();
		}
		return null;
	}

	public void save(String name) {
		SaveManager sm = new SaveManager(game);
		sm.save(game.getSaveRoot().append(name));
	}

	public void load(String name) {
		SaveManager sm = new SaveManager(game);
		sm.load(game.getSaveRoot().append(name));
	}

}
