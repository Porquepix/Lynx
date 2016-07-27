package core.validation;

import java.util.Objects;

public class TypeValidator implements Validator {

    private Class<?> clazz;

    public TypeValidator(Class<?> clazz) {
	this.clazz = Objects.requireNonNull(clazz);
    }

    @Override
    public boolean validate(Object data) {
	if (data == null)
	    return false;

	return data.getClass().equals(this.clazz);
    }

}