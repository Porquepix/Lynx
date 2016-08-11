package com.lynx.core.game.answer;

public class Answer {

	private Object value;
	private Class<?> clazz;

	public Answer(Object value) {
		this.value = value;
		if ( value != null ) {
			this.clazz = value.getClass();
		} else {
			this.clazz = Void.class;
		}
	}

	public Object getValue() {
		return this.value;
	}

	public Class<?> getValueType() {
		return this.clazz;
	}

	public boolean hasType(Class<String> class1) {
		return this.getValueType().equals(clazz);
	}

}
