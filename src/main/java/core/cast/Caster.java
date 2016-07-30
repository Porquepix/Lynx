package core.cast;

import core.logging.Loggers;
import core.logging.LynxLogger;

public interface Caster<T, K> {

    public static final LynxLogger logger = Loggers.getLogger(Caster.class);

    public K cast(T source);

}
