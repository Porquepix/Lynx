package com.lynx.core.validation;

import java.util.ArrayList;
import java.util.List;

import com.lynx.core.exception.LynxException;

public class GlobalValidator implements Validator {

	private List<Validator> validators;

	protected GlobalValidator(GlobalValidatorBuilder builder) {
		this.validators = new ArrayList<>(builder.validators);
	}

	@Override
	public boolean validate(Object data) {
		boolean valid = true;
		for ( Validator v : this.validators ) {
			valid &= v.validate(data);
		}
		if ( !valid ) {
			logger.warn("Validation failed for request {} and validator {}", data, this);
		}
		return valid;
	}

	public boolean validateOrFail(Object data) {
		boolean ret = this.validate(data);
		if ( !ret ) { throw new LynxException("Validation failled !"); }
		return ret;
	}

	@Override
	public String toString() {
		return validators.toString();
	}

}
