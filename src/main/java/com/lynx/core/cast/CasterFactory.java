package com.lynx.core.cast;


public abstract class CasterFactory<T> {

    public CasterFactory() {
    }

    public abstract <K> Caster<T, K> getCaster(Class<K> target);

}
