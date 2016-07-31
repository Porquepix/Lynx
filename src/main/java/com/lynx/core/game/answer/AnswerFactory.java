package com.lynx.core.game.answer;

public abstract class AnswerFactory {

    public AnswerFactory() {
    }

    public abstract Answer buildAnswerByString(String answer, Class<?> target);

}
