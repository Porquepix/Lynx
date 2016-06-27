package core.game;


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
		return NodeType.valueOf(name.toUpperCase());
	}

}