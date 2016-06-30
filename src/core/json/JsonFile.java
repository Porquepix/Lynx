package core.json;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import core.exception.LynxException;
import core.key.FileKey;
import core.logging.Log;

public class JsonFile {
	
	private Path path;
	private JsonContent content;
	private boolean loaded;
	
	public JsonFile(String path) {
		this(Paths.get(path));
	}
	
	public JsonFile(FileKey key) {
		this(key.getPath());
	}
	
	public JsonFile(Path path) {
		this.path = path;
		this.loaded = false;
		this.content = new JsonContent();
	}
	
	public JsonFile loadContent() {
		if (this.content.isEmpty() && this.exists()) {
			try {
				this.content = new JsonContent(this);
				this.loaded = true;
			} catch (IOException e) {
				Log.get().error("IOException file {}", e, this.path.toString());
			}
		}
		return this;
	}
	
	public JsonFile loadContentOrFail() {
		this.loadContent();
		if (!this.isLoaded()) {
			throw new LynxException("Impossible to read the JsonFile: " + this.getPath().toString());				
		}
		return this;
	}
	
	public boolean isLoaded() {
		return this.loaded;
	}
	
	public JsonContent getContent() {
		return this.content;
	}
	
	public void setContent(JsonContent content) {
		this.content = content;
		this.loaded = false;
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
