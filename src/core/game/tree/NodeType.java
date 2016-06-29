package core.game.tree;

import core.game.Game;
import core.logging.Log;

public enum NodeType {

	ANSWER(Integer.class), 
	DOUBLE(Double.class),
	INTEGER(Integer.class), 
	STRING(String.class), 
	VOID(Void.class);

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
			Log.get().error("NodeType not found for name: {}.", name);
			Game.gameCorruptedException();
		}
		return null;
	}

}