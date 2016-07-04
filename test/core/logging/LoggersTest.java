package core.logging;

import static org.junit.Assert.*;

import org.apache.logging.log4j.Level;
import org.junit.Test;

public class LoggersTest {
    
    private LynxLogger logger = Loggers.getLogger(LoggersTest.class);
    
    @Test
    public void testSingleton() {
	assertSame(logger, Loggers.getLogger(LoggersTest.class));
	assertNotSame(logger, Loggers.getLogger(Object.class));
    }
    
    @Test
    public void testSetLevelWarn() {
	Loggers.setRootLevel(Level.WARN);
	
	assertTrue(logger.isErrorEnabled());
	assertTrue(logger.isWarnEnabled());
	assertFalse(logger.isInfoEnabled());
	assertFalse(logger.isDebugEnabled());
    }
    
    @Test
    public void testSetLevelDebug() {
	Loggers.setRootLevel(Level.DEBUG);
	
	assertTrue(logger.isErrorEnabled());
	assertTrue(logger.isWarnEnabled());
	assertTrue(logger.isInfoEnabled());
	assertTrue(logger.isDebugEnabled());
    }

}
