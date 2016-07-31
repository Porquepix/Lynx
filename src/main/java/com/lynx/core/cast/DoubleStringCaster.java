package com.lynx.core.cast;

public class DoubleStringCaster implements StringCaster<Double> {

    public DoubleStringCaster() {
    }

    @Override
    public Double cast(String source) {
	try {
	    return Double.parseDouble(source);
	} catch (Exception e) {
	    logger.warn("Impossible to cast (string -> double) for value '{}'", source);
	}
	return null;
    }

}
