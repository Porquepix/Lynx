package core.game.validation;

import java.util.ArrayList;
import java.util.List;

import core.game.validation.validators.InValidator;
import core.game.validation.validators.RangeValidator;
import core.game.validation.validators.TypeValidator;


public class RequestValidatorBuilder {
	
	protected List<Validator> validators;
	
	public RequestValidatorBuilder() {
		this.validators = new ArrayList<>();
	}
	
	public RequestValidatorBuilder type(Class<?> clazz) {
		this.validators.add(new TypeValidator(clazz));
		return this;
	}
	
	public RequestValidatorBuilder range(double min, double max) {
		this.validators.add(new RangeValidator(min, max));
		return this;
	}
	
	public RequestValidatorBuilder in(Object... validValues) {
		this.validators.add(new InValidator(validValues));
		return this;
	}
	
	public RequestValidator build() {
		return new RequestValidator(this);
	}
	
}
