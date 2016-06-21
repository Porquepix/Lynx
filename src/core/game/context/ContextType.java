package core.game.context;

public enum ContextType {
	
	ANSWER(Integer.class),
	VOID(Void.class),
	INTEGER(Integer.class),
	DOUBLE(Double.class),
	STRING(String.class);
	
	private Class<?> clazz;
	
	ContextType(Class<?> clazz) {
		this.clazz = clazz;
	}
	
	public Class<?> getClazz() {
		return this.clazz;
	}
	
	public static ContextType getByName(String name) {
		return ContextType.valueOf(name.toUpperCase());
	}

}
