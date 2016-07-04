package core.json.model.node;

public class NextModel {

    private String condition;
    private String node;
    private String after;

    public NextModel() {
    }

    public String getCondition() {
	return this.condition;
    }

    public boolean hasCondition() {
	return this.getCondition() != null;
    }

    public String getNode() {
	return this.node;
    }

    public String getAfter() {
	return this.after;
    }

    public boolean hasAfter() {
	return this.getAfter() != null;
    }

}
