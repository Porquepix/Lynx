package core;

import static org.junit.Assert.*;

import org.junit.Test;

public abstract class CoreTest {
    
    @Test
    public void testSingleton() {
	assertSame(Core.getInstance(), Core.getInstance());
    }

}
