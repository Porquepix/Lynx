package com.lynx.core.cast;

import java.util.Optional;


public class StringIntegerCaster implements Caster<String, Integer> {

	public StringIntegerCaster() {
	}

	@Override
	public Optional<Integer> cast(String source) {
		try {
			return Optional.of(Integer.parseInt(source));
		} catch (Exception e) {
			logger.warn("Impossible to cast (string -> integer) for value '{}'", source);
		}
		return Optional.empty();
	}

}
