package com.lynx.core.cast;

import java.util.Optional;

import com.lynx.core.logging.Loggers;
import com.lynx.core.logging.LynxLogger;

public interface Caster<T, K> {

	public static final LynxLogger logger = Loggers.getLogger(Caster.class);

	public Optional<K> cast(T source);

}
