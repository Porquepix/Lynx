package core.game.tree;

import java.util.List;
import java.util.Map;

import core.ContentKey;
import core.game.Game;
import core.json.JsonContent;
import core.json.JsonFile;

public class StateNode extends Node<JsonContent> {
	
	private static final String URI_FORMAT = "story/%s.json"; 
	
	public static final String TEXT = "text";
	public static final String AUTHOR = "author";
	protected static final String CHOICES = "choices";
	protected static final String ANSWER = "answer";
	protected static final String NEXT = "next";
	protected static final String CONDITION = "condition";

	private Game game;
	private String stateId;
	
	private AnswerNode answerData;
	private ChoicesNode<?> choicesNode;
	private NextNode nextData;

	public StateNode(Game game, String stateId) {
		 super(null, new JsonContent());
		 this.game = game;
		 this.stateId = stateId;
		 loadContentFromFile(stateId);
		 loadChildrenNode();
    }

	@SuppressWarnings("unchecked")
    private void loadChildrenNode() {
		this.answerData = new AnswerNode(this, content.getAsObject(ANSWER, null));
	    this.nextData = new NextNode(this, (List<Map<String, String>>) content.getAsArray(NEXT, null));
	    loadChoiceNode();
    }

	@SuppressWarnings("unchecked")
    private void loadChoiceNode() {
	    if (this.answerData.isAnswerType()) {
	    	List<?> choicesContent = content.getAsArray(CHOICES, null);
	    	if (choicesContent != null && !choicesContent.isEmpty() && choicesContent.get(0) instanceof String) {
	    		this.choicesNode = new ChoicesStringNode(this, (List<String>) choicesContent);
	    	} else {
	    		this.choicesNode = new ChoicesObjectNode(this, (List<Map<String, String>>) choicesContent);	    		
	    	}
	    }
	}

	private void loadContentFromFile(String stateId) {
		ContentKey ck = new ContentKey(stateId);
		String finalPath = String.format(URI_FORMAT, ck.getFileIdAsPath().toString());
		JsonFile jsonFile = new JsonFile(game.getRoot().resolve(finalPath));
		jsonFile.loadContentOrFail();
		this.content = jsonFile.getContent().getAsObject(ck.getContentId(), new JsonContent());
    }
	
	public Game getGame() {
		return this.game;
	}
	
	public String getId() {
		return this.stateId;
	}
	
	public AnswerNode getAnswerNode() {
		return this.answerData;
	}
	
	public NextNode getNextNode() {
		return this.nextData;
	}
	
	public ChoicesNode<?> getChoicesNode() {
		return this.choicesNode;
	}
	
	public String getText() {
		return this.getContent().getAsString(TEXT, null);
	}
	
	public String getAuthor() {
		return this.getContent().getAsString(AUTHOR, null);
	}
	
}
