package com.lynx.core.game;

import com.lynx.core.game.node.Node;
import com.lynx.core.game.save.Savable;
import com.lynx.core.interpreter.IInterpreter;
import com.lynx.core.json.model.GameInfoModel;
import com.lynx.core.namespace.Namespace;
import com.lynx.core.translation.Translator;

public interface IGame extends Comparable<IGame>, Savable {
	
	public void start();
	
	public GameInfoModel getInfo();
	
	public Namespace getRoot();
	
	public Namespace getStoryRoot();
	
	public Namespace getLangRoot();
	
	public Namespace getSaveRoot();
	
	public Translator getTranslator();
	
	public void setTranslator(Translator gameTranslator);
	
	public IInterpreter getInterpreter();
	
	public Node getCurrentNode();
	
	public void setCurrentNode(String nodeName);
	
	public String getCurrentNodeName();
	
	public boolean next(String answer);
	
}
