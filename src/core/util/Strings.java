package core.util;

public final class Strings {
    
    public static final String EMPTY = "";
    
    private Strings() {
    }
    
    public static String nullToEmpty(String s) {
	return s == null ? EMPTY : s;
    }

}
