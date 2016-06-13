package core.game.validation.validators;

import core.game.Request;
import core.game.validation.Validator;

public class TypeValidator implements Validator {

	private Class<?> clazz;

	public TypeValidator(Class<?> clazz) {
		this.clazz = clazz;
	}
	
	@Override
	public boolean isValidatable() {
		return true;
	}

	@Override
	public boolean validate(Request r) {
		Class<?> data = r.getInputType();
		return this.clazz.getName().equals(data.getName());
	}

}
