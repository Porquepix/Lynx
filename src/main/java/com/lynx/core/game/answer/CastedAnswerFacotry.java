package com.lynx.core.game.answer;

import java.util.Optional;

import com.lynx.core.cast.Caster;
import com.lynx.core.cast.CasterFactory;

public class CastedAnswerFacotry extends AnswerFactory {

	private CasterFactory<String> factory;

	public CastedAnswerFacotry(CasterFactory<String> factory) {
		this.factory = factory;
	}

	@Override
	public <K> Optional<Answer> buildAnswerByString(String string, Class<K> target) {
		Optional<Caster<String, K>> caster = factory.getCaster(target);
		if ( caster.isPresent() ) {
			Optional<K> casted = caster.get().cast(string);
			if ( casted.isPresent() ) { 
				Answer answer = new Answer(casted.get()); 
				return Optional.of(answer);
			}
		}
		return Optional.empty();
	}

}
