package core.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import core.ContentKey;
import core.game.validation.GlobalValidatorBuilder;
import core.game.validation.Validator;
import core.json.JsonContent;
import core.json.JsonFile;
import core.translation.TranslateManager;

public class Node {

	private static final String URI_FORMAT = "story/%s.json"; 
	
	private Game game;
	private JsonContent node;
	private String id;
	private TranslateManager translator;
	private Validator answerValidator;

	protected Node(String id, Game game, JsonContent content) {
		this.id = id;
		this.game = game;
		this.node = content;
		this.translator = game.getTranslator();
		buildAnswerValidator();
	}

	public String getId() {
		return this.id;
	}

	public String getText() {
		String text = this.node.getAsString("text", null);
		Objects.requireNonNull(text);
		return this.translator.translate(text);
	}

	public boolean hasAuthor() {
		return this.getAuthor() != null;
	}

	public String getAuthor() {
		String author = this.node.getAsString("author", null);
		return author == null ? author : this.translator.translate(author);
	}

	public NodeType getType() {
		String type = this.node.getAsObject("answer", new JsonContent())
		        .getAsString("type", "void");
		Objects.requireNonNull(type);
		return NodeType.getByName(type);
	}

	protected Node getNext(Answer a) {
		if ( this.getType().equals(NodeType.VOID) || this.getAnswerValidator().validate(a) ) {
			this.game.execute("__answer=" + a.getValue());
			List<Map<String, Object>> candidateNodes = (List<Map<String, Object>>) this.node.getAsArray("next", new ArrayList<Map<String, Object>>());
			for (Map<String, Object> node : candidateNodes) {
				String condition = (String) node.get("condition");
				if ( condition == null || evalCondition(condition) ) {
					return Node.getFromId((String) node.get("context"), game);
				}
			}
		}
		return null;
	}

	private boolean evalCondition(String condition) {
	    this.game.execute("__condition=" + condition);
	    return (boolean) this.game.getVariable("__condition");
    }

	private Validator getAnswerValidator() {
		return this.answerValidator;
	}

	private void buildAnswerValidator() {
		this.answerValidator = new GlobalValidatorBuilder().type(this.getType().getClass()).build();
	}

	public static Node getFromId(String nodeId, Game game) {
		ContentKey ck = new ContentKey(nodeId);
		String finalPath = String.format(URI_FORMAT, ck.getFileIdAsPath().toString());
		JsonFile jsonFile = new JsonFile(game.getRoot().resolve(finalPath));
		jsonFile.loadContentOrFail();
		JsonContent nodeContent = jsonFile.getContent().getAsObject(ck.getContentId(), new JsonContent());
		return new Node(nodeId, game, nodeContent);
	}

}
