package core.game.context;

import core.game.Request;
import core.game.validation.RequestValidator;
import core.game.validation.RequestValidatorBuilder;
import core.json.JsonContent;

public class RequestHandler {
	
	private JsonContent content;
	private RequestValidator validator;
	private ContextType attendedType;
	private Request lastRequest;
	
	public RequestHandler(JsonContent content) {
		this.content = content;
		this.attendedType = ContextType.getByName(this.content.getAsString("type", "void"));
		this.validator = this.buildValidator();
		this.lastRequest = null;
	}

	public boolean isValidRequest(Request r) {		
		Request request = r;
		if (!request.typeEquals(this.getAttendedClass())) {
			request = this.rebuildWithAttendedType(r);
		}
		this.lastRequest = request;
		return this.validator.validate(request);
	}
	
	private Request rebuildWithAttendedType(Request r) {
		if (this.getAttendedClass().equals(Integer.class)) {
			return r.toInteger();
		} else if (this.getAttendedClass().equals(Double.class)) {
			return r.toDouble();
		}
		return r;
	}

	public ContextType getAttendedType() {
		return this.attendedType;
	}
	
	public Class<?> getAttendedClass() {
		return this.getAttendedType().getClazz();
	}
	
	public boolean isVoidAttended() {
		return this.getAttendedType().equals(ContextType.VOID);
	}
	
	public boolean isAnswerAttended() {
		return this.getAttendedType().equals(ContextType.ANSWER);
	}
	
	private RequestValidator buildValidator() {
		RequestValidatorBuilder builder  = new RequestValidatorBuilder();

		Class<?> clazz = this.getAttendedClass();
		builder.type(clazz);
		
		buildRange(builder);
			
		return builder.build();
	}

	private void buildRange(RequestValidatorBuilder builder) {
		if (this.isAnswerAttended()) {
			builder.range(0, 10);
		} else {
			JsonContent jc = this.content.getAsObject("range", new JsonContent());
			if (!jc.isEmpty()) {
				if (this.getAttendedType().equals(Integer.class) || this.getAttendedType().equals(Double.class) || this.getAttendedType().equals(String.class)) {
					builder.range(jc.getAsDouble("min", Double.NaN), jc.getAsDouble("max", Double.NaN));
				}
			}			
		}
	}

	public Object getInputAsRealType() {
		return this.lastRequest.getInput();
	}


}
