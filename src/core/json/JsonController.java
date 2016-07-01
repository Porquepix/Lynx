package core.json;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import com.google.gson.Gson;

import core.exception.LynxException;
import core.key.FileKey;
import core.logging.Log;

public abstract class JsonController<T> {

	private FileKey file;
	protected Gson gson;
	
	public JsonController(FileKey file) {
		this.file = file;
		this.gson = new Gson();
	}
	
	public FileKey getFile() {
		return this.file;
	}
	
	protected final Reader getReader() {
		try {
	        return Files.newBufferedReader(file.getPath(), StandardCharsets.UTF_8);
        } catch (IOException e) {
        	Log.get().error("Impossible to get the reader of the file '{}'", e, file.getKey());
	        throw new LynxException("Impossible to get the reader of the file: " + file.getKey());
        }
	}
	
	protected final Writer getWriter() {
		try {
	        return Files.newBufferedWriter(file.getPath(), StandardCharsets.UTF_8);
        } catch (IOException e) {
        	Log.get().error("Impossible to get the writer of the file '{}'", e, file.getKey());
	        throw new LynxException("Impossible to get the writer of the file: " + file.getKey());
        }
	}
	
	public abstract T fetch();
	
	public abstract void store(T model);
	
}
