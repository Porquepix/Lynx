package core.game.validation;

import java.util.List;

import core.exception.LynxException;
import core.game.Request;
import core.logging.Log;

public class RequestValidator {
	
	private List<Validator> validators;
	
	protected RequestValidator(RequestValidatorBuilder builder) {
		this.validators = builder.validators;
	}
	
	public boolean validate(Request r) {
		boolean valid = true;
		for (Validator v : this.validators) {
			if (v.isValidatable()) {
				valid &= v.validate(r);
			}
		}
		return valid;
	}

	public boolean validateOrFail(Request r) {
		boolean ret = this.validate(r);
		if (!ret) {
			Log.get().error("Validation failed for request {} and validator {}", r, this);
			throw new LynxException("Validation failled !");
		}
		return ret;
	}


}
