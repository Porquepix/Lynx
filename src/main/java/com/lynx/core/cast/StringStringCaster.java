package com.lynx.core.cast;

import java.util.Optional;


public class StringStringCaster implements Caster<String, String> {

	public StringStringCaster() {
	}

	@Override
	public Optional<String> cast(String source) {
		return Optional.ofNullable(source);
	}

}
