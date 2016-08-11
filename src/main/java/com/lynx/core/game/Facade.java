package com.lynx.core.game;

import com.lynx.core.translation.Translator;

public abstract class Facade {

	protected Translator translator;

	public Facade(Translator translator) {
		this.translator = translator;
	}

	protected Translator getTranslator() {
		return this.translator;
	}

}
