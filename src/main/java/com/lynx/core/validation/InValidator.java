package com.lynx.core.validation;

import java.util.Objects;

public class InValidator implements Validator {

	private Object[] validValues;

	public InValidator(Object[] validValues) {
		this.validValues = Objects.requireNonNull(validValues);
		;
	}

	@Override
	public boolean validate(Object data) {
		boolean found = false;
		int i = 0;
		while ( !found && i < this.validValues.length ) {
			if ( areSameOrEquals(data, validValues[i]) ) {
				found = true;
			}
			i++;
		}
		return found;
	}
	
	public boolean areSameOrEquals(Object o1, Object o2) {
		return o1 == o2 || o1.equals(o2);
	}

	@Override
	public String toString() {
		return "in: " + validValues.toString();
	}

}
