package core.game.interpreter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import bsh.EvalError;
import bsh.Interpreter;

public class InterpreterFrame {
	
	private Interpreter interpreter;
	
	public InterpreterFrame() {
		this.interpreter = new Interpreter();
	}

	public InterpreterFrame(Map<String, Object> variables) throws EvalError {
		this();
		for (Entry<String, Object> var : variables.entrySet()) {
			this.addVariable(var.getKey(), var.getValue());
		}
	}
	
	public void addVariable(String name, Object value) throws EvalError {
		this.interpreter.set(name, value);
	}
	
	public void removeVariable(String name) throws EvalError {
		this.interpreter.unset(name);
	}
	
	public Object getVariable(String name) throws EvalError {
		return this.interpreter.get(name);
	}
	
	public void eval(String code) throws EvalError {
		this.interpreter.eval(code);
	}
	
	public Map<String, Object> getVariables(List<String> names) throws EvalError {
		Map<String, Object> ret = new HashMap<>();
		for (String name : names) {
			ret.put(name, this.getVariable(name));
		}
		return ret;
	}
	
}
