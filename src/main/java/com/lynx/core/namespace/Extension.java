package com.lynx.core.namespace;

public enum Extension {

    JSON;

    @Override
    public String toString() {
	return this.name().toLowerCase();
    }

}
