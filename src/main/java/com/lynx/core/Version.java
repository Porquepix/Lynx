package com.lynx.core;

public class Version {

	public static final Version CURRENT = new Version("1.0.0-beta");

	private String name;

	public Version(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}

}
