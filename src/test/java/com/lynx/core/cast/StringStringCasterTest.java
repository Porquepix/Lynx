package com.lynx.core.cast;

import static org.junit.Assert.*;

import org.junit.Test;

import com.lynx.core.cast.StringStringCaster;

public class StringStringCasterTest {

    private StringStringCaster caster = new StringStringCaster();

    @Test
    public void testCast() {
	assertEquals(caster.cast("test"), "test");
	assertEquals(caster.cast(""), "");
	assertEquals(caster.cast(null), null);
    }

}
