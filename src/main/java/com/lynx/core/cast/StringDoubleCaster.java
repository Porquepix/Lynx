package com.lynx.core.cast;

import java.util.Optional;

public class StringDoubleCaster implements Caster<String, Double> {

	public StringDoubleCaster() {
	}

	@Override
	public Optional<Double> cast(String source) {
		try {
			return Optional.of(Double.parseDouble(source));
		} catch (Exception e) {
			logger.warn("Impossible to cast (string -> double) for value '{}'", source);
		}
		return Optional.empty();
	}

}
