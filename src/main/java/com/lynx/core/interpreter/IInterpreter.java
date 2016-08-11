package com.lynx.core.interpreter;

public interface IInterpreter {

	public void addVariable(String name, Object value);

	public void removeVariable(String name);

	public void eval(String code);

	public void evalIfNotNull(String code);

	public boolean evalCondition(String condition);

}
