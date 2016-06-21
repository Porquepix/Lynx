package core.game.context;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import bsh.EvalError;
import core.Core;
import core.exception.LynxException;
import core.game.Request;
import core.game.interpreter.InterpreterFrame;
import core.json.JsonContent;
import core.logging.Log;
import core.translation.TranslateManager;

public class Context {
	
	private String contextID;
	private JsonContent frame;
	private InterpreterFrame interpreter;
	private TranslateManager translator;
	private RequestHandler requestHandler;
	
	public Context(String contextID, Map<String, Object> variables, JsonContent frame) throws EvalError {
		this.contextID = contextID;
		this.frame = frame;
		this.requestHandler = new RequestHandler(frame.getAsObject("answer", new JsonContent()));
		
		this.interpreter = new InterpreterFrame(variables);
		this.interpreter.eval(frame.getAsString("init", ""));
	}
	
	public String getText() {
		return this.translator.translate(this.frame.getAsString("text", Core.MISSING_DATA));
	}
	
	public ContextType getAttendedType() {
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
	
	@SuppressWarnings("unchecked")
	public List<String> getAnswers() {
		return (List<String>) Collections.unmodifiableList(this.frame.getAsArray("answers", new ArrayList<String>()));
	}

	public String getNextContextID(Request r) {
		if (this.requestHandler.isVoidAttended()) {
			return getNextContextID();
		} else if (this.requestHandler.isValidRequest(r)) {
			try {
				this.interpreter.addVariable("__answer", this.requestHandler.getInputAsRealType());
			} catch (EvalError e) {
				Log.get().warn("Impossible to add answer variable '{}' of type {} for the context {}", r.getInput(), r.getInputType(), this.contextID);
			}
			String code = "";
			try {
				code = this.frame.getAsObject("answer", new JsonContent()).getAsString("after", "");
				this.interpreter.eval(code);
			} catch (EvalError e) {
				Log.get().warn("Impossible to execute after answer code '{}' for the context {}", e, code, this.contextID);
			}
			return getNextContextID();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	private String getNextContextID() {
		List<Map<String, Object>> json = (List<Map<String, Object>>) this.frame.getAsArray("next", new ArrayList<Map<String, Object>>());
		for (Map<String, Object> m : json) {
			if (m.containsKey("condition")) {
				try {
					this.interpreter.eval("__cond=" + m.get("condition"));
					boolean result = (boolean) this.interpreter.getVariable("__cond");
					if (result) {
						return (String) m.get("context");
					}
				} catch (EvalError e) {
					Log.get().error("Impossible to exectute the next condition '{}' for the context {}", e, m.get("condition"), this.contextID);
					throw new LynxException("Internal error : game corrupted.");
				}
			} else {
				return (String) m.get("context");
			}
		}
		return null;
	}

}
