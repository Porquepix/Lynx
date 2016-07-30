package core.validation;

import java.util.ArrayList;
import java.util.List;

import core.exception.LynxException;
import core.game.answer.Answer;

public class GlobalValidator implements Validator {

    private List<Validator> validators;

    protected GlobalValidator(GlobalValidatorBuilder builder) {
	this.validators = new ArrayList<>(builder.validators);
    }

    @Override
    public boolean validate(Object data) {
	boolean valid = true;
	for (Validator v : this.validators) {
	    valid &= v.validate(data);
	}
	return valid;
    }

    public boolean validateOrFail(Answer r) {
	boolean ret = this.validate(r);
	if (!ret) {
	    logger.error("Validation failed for request {} and validator {}", r, this);
	    throw new LynxException("Validation failled !");
	}
	return ret;
    }
}
