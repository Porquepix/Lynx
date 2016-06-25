package core.game.validation;

import java.util.ArrayList;
import java.util.List;

import core.game.validation.validators.InValidator;
import core.game.validation.validators.RangeValidator;
import core.game.validation.validators.TypeValidator;


public class GlobalValidatorBuilder {
	
	protected List<Validator> validators;
	
	public GlobalValidatorBuilder() {
		this.validators = new ArrayList<>();
	}
	
	public GlobalValidatorBuilder type(Class<?> clazz) {
		this.validators.add(new TypeValidator(clazz));
		return this;
	}
	
	public GlobalValidatorBuilder range(double min, double max) {
		this.validators.add(new RangeValidator(min, max));
		return this;
	}
	
	public GlobalValidatorBuilder in(Object... validValues) {
		this.validators.add(new InValidator(validValues));
		return this;
	}
	
	public GlobalValidator build() {
		return new GlobalValidator(this);
	}
	
}
