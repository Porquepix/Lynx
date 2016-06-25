package core.game.validation.validators;

import java.util.Objects;

import core.game.validation.Validator;

public class InValidator implements Validator {

	private Object[] validValues;
	
	public InValidator(Object[] validValues) {
		this.validValues = Objects.requireNonNull(validValues);;
	}

	@Override
	public boolean validate(Object data) {
		boolean found = false;
		int i = 0;
		while (!found && i < this.validValues.length) {
			if (data.equals(this.validValues[i])) {
				found = true;
			}
			i++;
		}
		return found;
	}

}
