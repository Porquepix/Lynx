package core.game.validation.validators;

import core.game.Request;
import core.game.validation.Validator;
import core.logging.Log;

public class RangeValidator implements Validator {

	private double min;
	private double max;

	public RangeValidator(double min, double max) {
		if (min > max)
			throw new IllegalArgumentException("The right bound of the range must be greater or equal than the left bound.");

		this.min = min;
		this.max = max;
	}

	@Override
	public boolean isValidatable() {
		return this.min != Double.NaN && this.max != Double.NaN;
	}

	@Override
	public boolean validate(Request r) {
		Object data = r.getInput();
		if (data instanceof String) {
			String s = (String) data;
			return this.min <= s.length() && s.length() < this.max;
		} else if (data instanceof Number) {
			Number n = (Number) data;
			return this.min <= n.doubleValue() && n.doubleValue() < this.max;
		} else {
			Log.get().warn("Range validation failled: unknow type {}", data.getClass());
			return false;
		}
	}

}
