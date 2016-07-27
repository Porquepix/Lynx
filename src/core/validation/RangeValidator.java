package core.validation;


public class RangeValidator implements Validator {

    private double min;
    private double max;

    public RangeValidator(double min, double max) {
	if (min > max)
	    throw new IllegalArgumentException(
		    "The right bound of the range must be greater or equal than the left bound.");
	if (min == Double.NaN || max == Double.NaN)
	    throw new IllegalArgumentException("The bounds must be numbers.");

	this.min = min;
	this.max = max;
    }

    @Override
    public boolean validate(Object data) {
	if (data instanceof String) {
	    return validateString((String) data);
	} else if (data instanceof Number) {
	    return validateNumber((Number) data);
	} else {
	    logger.warn("Range validation failled: unknow type {}",
		    data.getClass());
	    return false;
	}
    }

    private boolean validateString(String data) {
	return this.min <= data.length() && data.length() < this.max;
    }

    private boolean validateNumber(Number data) {
	return this.min <= data.doubleValue() && data.doubleValue() < this.max;
    }

}
