package core;

public class Version {

    /**
     * XXYYZZWW: -> XX: major -> YY: minor -> ZZ: patch -> WW: other data (0 <=
     * WW < 25 => alpha; 25 <= WW < 50 => beta; 50 <= WW < 99 => candidate; WW =
     * 99 => stable)
     */
    public static final Version CURRENT = new Version(01000001, "1.0.0-alpha1");

    private String name;
    private int id;

    public Version(int id, String name) {
	this.id = id;
	this.name = name;
    }

    @Override
    public String toString() {
	return name + "(" + id + ")";
    }

}
