package core.game.tree;

import core.game.Game;
import core.json.controller.NodeController;
import core.json.model.node.NodeModel;
import core.key.ContentKey;
import core.key.FileKey;

public class StateNode extends Node<NodeModel> {
	
	private static final FileKey ROOT = new FileKey("story"); 
	
	private Game game;
	private String stateId;
	
	private AnswerNode answerData;
	private ChoicesNode choicesNode;
	private NextNode nextData;

	public StateNode(Game game, String stateId) {
		 super(null, new NodeModel());
		 this.game = game;
		 this.stateId = stateId;
		 
		 loadModel(stateId);
		 loadChildrenNode();
    }
	
	private void loadModel(String stateId) {
		ContentKey ck = new ContentKey(stateId);
		FileKey finalPath = game.getRoot().merge(ROOT).merge(ck.getFileKey());
		
		NodeController nodeController = new NodeController(finalPath);
		this.setContent(nodeController.fetch().get(ck.getContentId()));
    }
	
    private void loadChildrenNode() {
		this.answerData = new AnswerNode(this, content.getAnswer());
	    this.nextData = new NextNode(this, content.getNexts());
	    if (content.hasChoices()) {
	    	this.choicesNode = new ChoicesNode(this, content.getChoices());	    	
	    }
    }
	
	public Game getGame() {
		return this.game;
	}
	
	public String getId() {
		return this.stateId;
	}
	
	public String getText() {
		return content.getText();
	}
	
	public String getAuthor() {
		return content.getAuthor();
	}
	
	public AnswerNode getAnswerNode() {
		return this.answerData;
	}
	
	public NextNode getNextNode() {
		return this.nextData;
	}
	
	public ChoicesNode getChoicesNode() {
		return this.choicesNode;
	}
	
	public double nbChoices() {
	    return content.getChoices().size();
    }
	
}
