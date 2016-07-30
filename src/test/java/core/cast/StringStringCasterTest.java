package core.cast;

import static org.junit.Assert.*;

import org.junit.Test;

public class StringStringCasterTest {

    private StringStringCaster caster = new StringStringCaster();

    @Test
    public void testCast() {
	assertEquals(caster.cast("test"), "test");
	assertEquals(caster.cast(""), "");
	assertEquals(caster.cast(null), null);
    }

}
