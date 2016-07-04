package core.game;

import core.logging.Loggers;
import core.logging.LynxLogger;

public class Answer {

    private static final LynxLogger logger = Loggers.getLogger(Answer.class);

    private Object value;
    private Class<?> clazz;

    public Answer(Object value) {
	this.value = value;
	this.clazz = this.value.getClass();
    }

    public Object getValue() {
	return this.value;
    }

    public Class<?> getValueType() {
	return this.clazz;
    }

    public boolean typeEquals(Class<?> clazz) {
	return this.getValueType().equals(clazz);
    }

    public Answer toInteger() {
	if (this.typeEquals(String.class)) {
	    try {
		return new Answer(Integer.parseInt((String) this.getValue()));
	    } catch (NumberFormatException e) {
		logger.warn(
			"Request convertion (string -> integer) failed for value {}",
			this.getValue());
	    }
	}
	return this;
    }

    public Answer toDouble() {
	if (this.typeEquals(String.class)) {
	    try {
		return new Answer(Double.parseDouble((String) this.getValue()));
	    } catch (NumberFormatException e) {
		logger.warn(
			"Request convertion (string -> double) failed for value {}",
			this.getValue());
	    }
	}
	return this;
    }

    public Answer cast(Class<?> clazz) {
	if (clazz.equals(Integer.class)) {
	    return this.toInteger();
	} else if (clazz.equals(Double.class)) {
	    return this.toDouble();
	}
	return this;
    }

}
