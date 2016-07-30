package core.validation;

import java.util.Objects;

public class TypeValidator implements Validator {

    private Class<?> clazz;
    private boolean strict;

    public TypeValidator(Class<?> clazz) {
	this(clazz, true);
    }

    public TypeValidator(Class<?> clazz, boolean strict) {
	this.clazz = Objects.requireNonNull(clazz);
	this.strict = strict;
    }

    @Override
    public boolean validate(Object data) {
	if (data == null)
	    return clazz.equals(Void.class);

	return strict ? strictValidate(data) : nonStrictValidate(data);
    }

    private boolean strictValidate(Object data) {
	return clazz.equals(data.getClass());
    }

    private boolean nonStrictValidate(Object data) {
	return clazz.isAssignableFrom(data.getClass());
    }

}
