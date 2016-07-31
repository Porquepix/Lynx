package com.lynx.core.interpreter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.lynx.core.exception.LynxException;
import com.lynx.core.logging.Loggers;
import com.lynx.core.logging.LynxLogger;

import bsh.EvalError;
import bsh.Interpreter;

public class InterpreterManager implements IInterpreter {

    private static final LynxLogger logger = Loggers.getLogger(InterpreterManager.class);

    private Interpreter interpreter;

    public InterpreterManager(Interpreter interpreter) {
	this.interpreter = interpreter;
    }

    @Override
    public void addVariable(String name, Object value) {
	try {
	    this.interpreter.set(name, value);
	} catch (EvalError e) {
	    logger.warn("Impossible to add variable '{}' with value '{}' to the interpreter.", e,
		    name, value);
	}
    }

    public void addVariables(Map<String, Object> variables) {
	for (Entry<String, Object> var : variables.entrySet()) {
	    this.addVariable(var.getKey(), var.getValue());
	}
    }

    public void declareVariable(String variable) {
	this.addVariable(variable, null);
    }

    public void declareVariables(List<String> variables) {
	for (String var : variables) {
	    this.declareVariable(var);
	}
    }

    @Override
    public void removeVariable(String name) {
	try {
	    this.interpreter.unset(name);
	} catch (EvalError e) {
	    logger.warn("Impossible to remove variable '{}' from the interpreter.", e, name);
	}
    }

    public void removeVariables(List<String> names) {
	for (String name : names) {
	    this.removeVariable(name);
	}
    }

    public Object getVariable(String name) {
	try {
	    return this.interpreter.get(name);
	} catch (EvalError e) {
	    logger.warn("Impossible to retrieve variable '{}' from the interpreter.", e, name);
	    return null;
	}
    }

    public Map<String, Object> getVariables(List<String> names) {
	Map<String, Object> ret = new HashMap<>();
	for (String name : names) {
	    ret.put(name, this.getVariable(name));
	}
	return ret;
    }

    @Override
    public void eval(String code) {
	try {
	    this.interpreter.eval(code);
	} catch (EvalError e) {
	    logger.error("Impossible to execute code '{}' in the interpreter.", e, code);
	    throw new LynxException("Intrernal error: impossible to execute code.");
	}
    }

    @Override
    public void evalIfNotNull(String code) {
	if (code != null) {
	    this.eval(code);
	}
    }

    @Override
    public boolean evalCondition(String condition) {
	this.eval("__condition=(" + condition + ")");
	return (boolean) this.getVariable("__condition");
    }

}
