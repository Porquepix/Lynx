package com.lynx.core.cast;

import java.util.Optional;


public abstract class CasterFactory<T> {

	public CasterFactory() {
	}

	public abstract <K> Optional<Caster<T, K>> getCaster(Class<K> target);

}
