package com.lynx.core.cast;

import static org.junit.Assert.*;

import org.junit.Test;

import com.lynx.core.cast.StringIntegerCaster;

public class StringIntegerCasterTest {

	private StringIntegerCaster caster = new StringIntegerCaster();

	@Test
	public void testCast() {
		assertFalse(caster.cast("test").isPresent());
		assertEquals(caster.cast("33").get(), new Integer(33));
		assertFalse(caster.cast(null).isPresent());
	}

}
