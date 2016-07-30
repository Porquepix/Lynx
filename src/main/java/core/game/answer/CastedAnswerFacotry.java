package core.game.answer;

import core.cast.Caster;
import core.cast.CasterFactory;

public class CastedAnswerFacotry extends AnswerFactory {

    private CasterFactory<String> factory;

    public CastedAnswerFacotry(CasterFactory<String> factory) {
	this.factory = factory;
    }

    @Override
    public Answer buildAnswerByString(String answer, Class<?> target) {
	Caster<String, ?> caster = factory.getCaster(target);
	if (caster != null) {
	    Object casted = caster.cast(answer);
	    if (casted != null) {
		return new Answer(casted);
	    }
	}
	return null;
    }

}
