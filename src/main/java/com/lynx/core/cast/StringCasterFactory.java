package com.lynx.core.cast;

import java.util.Objects;

public class StringCasterFactory extends CasterFactory<String> {

    public StringCasterFactory() {
    }

    @SuppressWarnings("unchecked")
    @Override
    public <K> Caster<String, K> getCaster(Class<K> target) {
	Objects.requireNonNull(target);
	Caster<String, K> ret = null;
	if (target.equals(Integer.class)) {
	    ret = (Caster<String, K>) new IntegerStringCaster();
	} else if (target.equals(Double.class)) {
	    ret = (Caster<String, K>) new DoubleStringCaster();
	} else if (target.equals(String.class)) {
	    ret = (Caster<String, K>) new StringStringCaster();
	}
	return ret;
    }

}
