package com.lynx.core.cast;


public class StringStringCaster implements StringCaster<String> {

    public StringStringCaster() {
    }

    @Override
    public String cast(String source) {
	return source;
    }

}
