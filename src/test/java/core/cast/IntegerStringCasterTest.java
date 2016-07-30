package core.cast;

import static org.junit.Assert.*;

import org.junit.Test;

public class IntegerStringCasterTest {

    private IntegerStringCaster caster = new IntegerStringCaster();

    @Test
    public void testCast() {
	assertEquals(caster.cast("test"), null);
	assertEquals(caster.cast("33"), new Integer(33));
	assertEquals(caster.cast(null), null);
    }

}