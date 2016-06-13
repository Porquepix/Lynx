package core.game.validation.validators;

import core.game.Request;
import core.game.validation.Validator;

public class InValidator implements Validator {

	private Object[] validValues;
	
	public InValidator(Object[] validValues) {
		this.validValues = validValues;
	}

	@Override
	public boolean isValidatable() {
		return this.validValues != null && this.validValues.length > 0;
	}

	@Override
	public boolean validate(Request r) {
		Object data = r.getInput();
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
