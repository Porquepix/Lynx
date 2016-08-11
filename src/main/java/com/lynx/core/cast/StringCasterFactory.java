package com.lynx.core.cast;

import java.util.Optional;


public class StringCasterFactory extends CasterFactory<String> {

	public StringCasterFactory() {
	}

	@SuppressWarnings("unchecked")
	@Override
	public <K> Optional<Caster<String, K>> getCaster(Class<K> target) {
		if ( Integer.class.equals(target) ) {
			return Optional.of((Caster<String, K>) new StringIntegerCaster());
		} else if ( Double.class.equals(target) ) {
			return Optional.of((Caster<String, K>) new StringDoubleCaster());
		} else if ( String.class.equals(target) ) { 
			return Optional.of((Caster<String, K>) new StringStringCaster()); 
		}
		return Optional.empty();
	}

}
