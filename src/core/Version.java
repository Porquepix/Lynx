package core;

public class Version {
	
	public static final Version V_1_0_0_beta1 = new Version("1.0.0 beta1");
	public static final Version CURRENT = V_1_0_0_beta1;

	private String name;
	
	public Version(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return this.name;
	}
	
}
