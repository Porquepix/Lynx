package core.cast;

import static org.junit.Assert.*;

import org.junit.Test;

public class StringCasterFacotryTest {

    private StringCasterFactory factory = new StringCasterFactory();

    @Test
    public void testGetCaster() {
	assertEquals(factory.getCaster(Integer.class).getClass(), IntegerStringCaster.class);
	assertEquals(factory.getCaster(Double.class).getClass(), DoubleStringCaster.class);
	assertEquals(factory.getCaster(String.class).getClass(), StringStringCaster.class);
	assertEquals(factory.getCaster(Object.class), null);
    }

}
