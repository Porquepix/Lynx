package core.json.controller;

import java.io.IOException;
import java.io.Writer;

import core.json.JsonController;
import core.json.model.AppSettingsModel;
import core.key.FileKey;
import core.logging.Log;

public class AppSettingsController extends JsonController<AppSettingsModel> {

	public AppSettingsController(FileKey file) {
	    super(file);
    }

	@Override
    public AppSettingsModel fetch() {
	    return this.gson.fromJson(this.getReader(), AppSettingsModel.class);
    }

	@Override
    public void store(AppSettingsModel model) {
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
