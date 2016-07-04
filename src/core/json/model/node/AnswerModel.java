package core.json.model.node;

public class AnswerModel {

    private String type;
    private RangeModel range;

    public AnswerModel() {
    }

    public String getType() {
	return this.type;
    }

    public RangeModel getRange() {
	return this.range;
    }

    public void setRange(RangeModel range) {
	this.range = range;
    }

    public boolean hasRange() {
	return this.getRange() != null;
    }

}
