package core.json;

import java.lang.reflect.Type;
import java.util.Map.Entry;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class JsonContentSerializer implements JsonSerializer<JsonContent> {

	public JsonContentSerializer() {
		super();
	}
	
	@Override
	public JsonElement serialize(JsonContent value, Type type, JsonSerializationContext context) {
		final JsonObject jsonObject = new JsonObject();
		
		for (Entry<String, Object> e : value.getJsonMap().entrySet()) {
			jsonObject.add(e.getKey(), context.serialize(e.getValue()));
		}
		
		return jsonObject;
	}

}
