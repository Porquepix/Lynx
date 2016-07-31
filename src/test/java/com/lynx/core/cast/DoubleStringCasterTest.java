package com.lynx.core.cast;

import static org.junit.Assert.*;

import org.junit.Test;

import com.lynx.core.cast.DoubleStringCaster;

public class DoubleStringCasterTest {

    private DoubleStringCaster caster = new DoubleStringCaster();

    @Test
    public void testCast() {
	assertEquals(caster.cast("test"), null);
	assertEquals(caster.cast("33.3"), new Double(33.3));
	assertEquals(caster.cast(null), null);
    }

}
