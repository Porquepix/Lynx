package core.game.facade;

import java.util.List;

import core.game.Game;
import core.game.tree.StateNode;
import core.logging.Log;
import core.translation.TranslateManager;

public class StateNodeFacade {
	
	private StateNode node;
	private TranslateManager translator;
	
	public StateNodeFacade(StateNode node, TranslateManager translator) {
		this.node = node;
		this.translator = translator;
	}
	
	public String getText() {
		String text = this.node.getText();
		if (text == null) {
			Log.get().error("Node {} don't have text.", this.node.getId());
			Game.gameCorruptedException();
		}
		return this.translator.translate(text);
	}
	
	public boolean hasAuthor() {
		return this.getAuthor() != null;
	}

	public String getAuthor() {
		String author = this.node.getAuthor();
		return author == null ? null : this.translator.translate(author);
	}
	
	public List<String> getChoices() {
		if (this.isAnswerType()) {
			List<String> choices = this.node.getChoicesNode().getChoices();
			return this.translator.translateAll(choices);
		}
		return null;
	}
	
	public boolean isVoidType() {
		return this.node.getAnswerNode().isVoidType();
	}
	
	public boolean isAnswerType() {
		return this.node.getAnswerNode().isAnswerType();
	}
	
}
