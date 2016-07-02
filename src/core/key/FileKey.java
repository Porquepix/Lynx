package core.key;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;


public class FileKey {
	
	private static final String ID_DELIMITER = ".";
	private static final String EXTENSION = ".json";
	
	private String key;
	private Path path;
	
	public FileKey(String key) {
		this.key = key;
		this.path =  Paths.get(this.key.replace(ID_DELIMITER, File.separator) + EXTENSION);
	}
	
	public FileKey append(String other) {
		Objects.requireNonNull(other);
		return this.merge(new FileKey(other));
	}
	
	public FileKey merge(FileKey other) {
		Objects.requireNonNull(other);
		return new FileKey(this.key + "." + other.key);
	}
	
	public String getKey() {
		return this.key;
	}
	
	public Path getPath() {
		return this.path;
	}
	
	public String getPathAsString() {
		return this.getPath().toString();
	}
	
	@Override
	public int hashCode() {
		return this.key.hashCode();
	}

	@Override
	public boolean equals(Object o) {
		if ( o == this ) { return true; }

		if ( o instanceof FileKey ) {
			FileKey other = (FileKey) o;
			return this.key.equals(other.key);
		}

		return false;
	}

}
