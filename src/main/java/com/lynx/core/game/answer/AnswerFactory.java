package com.lynx.core.game.answer;

import java.util.Optional;

public abstract class AnswerFactory {

	public AnswerFactory() {
	}

	public abstract <K> Optional<Answer> buildAnswerByString(String string, Class<K> target);

}
