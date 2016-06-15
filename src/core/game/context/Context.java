package core.game.context;

import java.util.List;
import java.util.Map;

import bsh.EvalError;
import core.Core;
import core.game.Request;
import core.game.interpreter.InterpreterFrame;
import core.json.JsonContent;
import core.logging.Log;
import core.translation.TranslateManager;

public class Context {
	
	private JsonContent frame;
	private InterpreterFrame interpreter;
	private TranslateManager translator;
	private RequestHandler requestHandler;
	
	public Context(Map<String, Object> variables, JsonContent frame) throws EvalError {
		this.frame = frame;
		this.requestHandler = new RequestHandler(frame.getAsObject("answer", new JsonContent()));
		
		this.interpreter = new InterpreterFrame(variables);
		this.interpreter.eval(frame.getAsString("init", ""));
	}
	
	public String getText() {
		return this.translator.translate(this.frame.getAsString("text", Core.MISSING_DATA));
	}
	
	public Class<?> getAttendedType() {
		return this.requestHandler.getAttendedType();
	}

	public void setTranslator(TranslateManager t) {
		this.translator = t;		
	}
	
	public Map<String, Object> save(List<String> variables) throws EvalError {
		if (variables == null)
			throw new NullPointerException("List c'ant be null !");
		return interpreter.getVariables(variables);
	}
	

	public String getNextContextID(Request r) {
		if (requestHandler.isValidRequest(r)) {
			try {
				this.interpreter.addVariable("__anwser", r.getInput());
			} catch (EvalError e) {
				Log.get().warn("Impossible to add anwser variable '{}' of type {}", r.getInput(), r.getInputType());
			}
			return "ok";
		}
		return null;
	}

}
