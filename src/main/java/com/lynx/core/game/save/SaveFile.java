package com.lynx.core.game.save;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.lynx.core.namespace.Namespace;

public class SaveFile {
	
	protected static final String EXT = "lys";
	
	private Path path;
	
	public SaveFile(Namespace namespace) {
		this.path = Paths.get(namespace.getPathAsString() + "." + EXT);
    }
	
	public void create() throws IOException {
		Files.createDirectories(path);
	}
	
	public void createIfNotExists() throws IOException {
		if (!Files.exists(path)) {
			create();
		}
	}
	
	public OutputStream output(OpenOption... options) throws IOException {
		return Files.newOutputStream(path, options);
	}
	
	public InputStream input(OpenOption... options) throws IOException {
		return Files.newInputStream(path, options);
	}	

}
