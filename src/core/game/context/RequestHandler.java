package core.game.context;

import core.game.Request;
import core.game.validation.RequestValidator;
import core.game.validation.RequestValidatorBuilder;
import core.json.JsonContent;

public class RequestHandler {
	
	private JsonContent content;
	private RequestValidator validator;
	private Class<?> attendedType;
	
	public RequestHandler(JsonContent content) {
		this.content = content;
		this.attendedType = getClassForName(this.content.getAsString("type", "integer"));
		this.validator = this.buildValidator();
	}

	public boolean isValidRequest(Request r) {
		Request request = r;
		if (!request.typeEquals(this.attendedType)) {
			request = this.rebuildWithAttendedType(r);
		}
		return this.validator.validate(request);
	}
	
	private Request rebuildWithAttendedType(Request r) {
		if (attendedType.equals(Integer.class)) {
			return r.toInteger();
		} else if (attendedType.equals(Double.class)) {
			return r.toDouble();
		}
		return r;
	}

	public Class<?> getAttendedType() {
		return this.attendedType;
	}
	
	private RequestValidator buildValidator() {
		RequestValidatorBuilder builder  = new RequestValidatorBuilder();

		Class<?> clazz = this.getAttendedType();
		builder.type(clazz);
			
		return builder.build();
	}

	private Class<?> getClassForName(String clazz) {
		switch (clazz) {
		case "string":
			return String.class;
		case "double":
			return Double.class;
		case "integer":
		default:
			return Integer.class;
		}
	}


}
