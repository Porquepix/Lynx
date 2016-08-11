package com.lynx.core.game.node;

import com.lynx.core.exception.GameCorruptedException;
import com.lynx.core.logging.Loggers;

public enum NodeType {

	ANSWER(Integer.class), DOUBLE(Double.class), INTEGER(Integer.class), STRING(String.class), VOID(
	        Void.class);

	private Class<?> clazz;

	NodeType(Class<?> clazz) {
		this.clazz = clazz;
	}

	public Class<?> getClazz() {
		return this.clazz;
	}

	public static NodeType getByName(String name) {
		try {
			return NodeType.valueOf(name.toUpperCase());
		} catch (Exception e) {
			Loggers.getLogger(NodeType.class).error("NodeType not found for name: {}.", name);
			throw new GameCorruptedException();
		}
	}

}