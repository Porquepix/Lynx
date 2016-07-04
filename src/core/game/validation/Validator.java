package core.game.validation;

import core.logging.Loggers;
import core.logging.LynxLogger;

public interface Validator {
    
    static final LynxLogger logger = Loggers.getLogger(Validator.class);

    public boolean validate(Object data);

}
