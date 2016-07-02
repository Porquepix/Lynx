package core.json.controller;

import java.io.IOException;
import java.io.Writer;

import core.json.JsonController;
import core.key.FileKey;
import core.logging.Log;

public class BaseController<T> extends JsonController<T> {

	private Class<T> type;
	
	public BaseController(FileKey file, Class<T> type) {
	    super(file);
	    this.type = type;
    }
	
	public Class<T> getType() {
		return this.type;
	}
	
    public T fetch() {
	    return this.gson.fromJson(this.getReader(), this.getType());
    }

    public void store(T model) {
	    String json = this.gson.toJson(model);
	    Writer writer = this.getWriter();
	    try {
	        writer.write(json);
	        writer.flush();
        } catch (IOException e) {
        	Log.get().warn("Impossible to get the write in the file '{}'", e, this.getFile().getKey());
        }
    }

}
