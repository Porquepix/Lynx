package core.json.model.node;

public class ChoiceModel {
	
	private String condition;
	private String text;
	private String after;
	
	public ChoiceModel() {}
	
	public String getCondition() {
		return this.condition;
	}
	
	public boolean hasCondition() {
		return this.getCondition() != null;
	}
	
	public String getText() {
		return this.text;
	}
	
	public String getAfter() {
		return this.after;
	}
	
	public boolean hasAfter() {
		return this.getAfter() != null;
	}

}
