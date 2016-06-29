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
	
	public void addVariable(String name, Object value) throws EvalError {
		this.interpreter.set(name, value);
	}
	
	public void addVariables(Map<String, Object> variables) throws EvalError {
		for (Entry<String, Object> var : variables.entrySet()) {
			this.addVariable(var.getKey(), var.getValue());
		}
	}
	
	public void removeVariable(String name) throws EvalError {
		this.interpreter.unset(name);
	}
	
	public void removeVariables(List<String> names) throws EvalError {
		for (String name : names) {
			this.removeVariable(name);
		}
	}
	
	public Object getVariable(String name) throws EvalError {
		return this.interpreter.get(name);
	}
	
	public Map<String, Object> getVariables(List<String> names) throws EvalError {
		Map<String, Object> ret = new HashMap<>();
		for (String name : names) {
			ret.put(name, this.getVariable(name));
		}
		return ret;
	}
	
	public void eval(String code) throws EvalError {
		this.interpreter.eval(code);
	}	
}
