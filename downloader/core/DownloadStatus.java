package core;

public enum DownloadStatus {
	
	DOWNLOADING,
	PAUSED,
	COMPLETE,
	CANCELLED,
	ERROR;
	
	@Override
	public String toString() {
		// Camel case the name
		String name = this.name();
		
		String firstLetter = name.substring(0, 1).toUpperCase();
		String other = name.substring(1).toLowerCase();
		
		return firstLetter + other;
	}

}
