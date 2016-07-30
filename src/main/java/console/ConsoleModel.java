package console;

import core.json.JsonModel;

public class ConsoleModel extends JsonModel {

    private String prompt;

    public ConsoleModel() {
    }

    public String getPrompt() {
	return this.prompt;
    }

}
