package core.game;

import core.logging.Log;

public class Request {

	private Object input;
	private Class<?> clazz;
	
	public Request(Object value) {
		this.input = value;
		this.clazz = this.input.getClass();
	}

	public Object getInput() {
		return this.input;
	}

	public Class<?> getInputType() {
		return this.clazz;
	}

	public boolean typeEquals(Class<?> clazz) {
		return this.getInputType().equals(clazz);
	}

	public Request toInteger() {
		if (this.typeEquals(String.class)) {
			try {
				return new Request(Integer.parseInt((String) this.getInput()));
			} catch (NumberFormatException  e) {
				Log.get().warn("Request convertion (string -> integer) failed for value {}", this.getInput());
			}
		}
		return this;
	}
	
	public Request toDouble() {
		if (this.typeEquals(String.class)) {
			try {
				return new Request(Double.parseDouble((String) this.getInput()));
			} catch (NumberFormatException  e) {
				Log.get().warn("Request convertion (string -> double) failed for value {}", this.getInput());
			}
		}
		return this;
	}

}
