package core.json;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import core.exception.LynxException;

public class JsonFile {
	
	private Path path;
	private JsonContent content;
	
	public JsonFile(String path) {
		this(Paths.get(path));
	}
	
	public JsonFile(Path path) {
		this.path = path;
	}
	
	public JsonContent getContent() {
		if (this.content == null && this.exists()) {
			try {
				this.content = new JsonContent(this);
			} catch (IOException e) {
				throw new LynxException("Impossible to read the JsonFile: " + this.getPath().toString());
			}
		}
		return this.content;
	}
	
	public void setContent(JsonContent content) {
		this.content = content;
	}
	
	public void write() throws IOException {
		Files.write(this.getPath(), this.getContent().toByteArray(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
	}

	public boolean exists() {
		return Files.exists(this.getPath());
	}
	
	public Path getPath() {
		return this.path;
	}

}
