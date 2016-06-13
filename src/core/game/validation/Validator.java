package core.game.validation;

import core.game.Request;

public interface Validator {
	
	public boolean isValidatable();
	public boolean validate(Request r);

}
