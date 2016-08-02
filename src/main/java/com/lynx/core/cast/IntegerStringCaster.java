package com.lynx.core.cast;


public class IntegerStringCaster implements StringCaster<Integer> {

    public IntegerStringCaster() {
    }

    @Override
    public Integer cast(String source) {
	Integer ret = null;
	try {
	    ret = Integer.parseInt(source);
	} catch (Exception e) {
	    logger.warn("Impossible to cast (string -> integer) for value '{}'", source);
	}
	return ret;
    }

}
