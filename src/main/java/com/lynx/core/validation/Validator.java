package com.lynx.core.validation;

import com.lynx.core.logging.Loggers;
import com.lynx.core.logging.LynxLogger;

public interface Validator {

    static final LynxLogger logger = Loggers.getLogger(Validator.class);

    public boolean validate(Object data);

}
