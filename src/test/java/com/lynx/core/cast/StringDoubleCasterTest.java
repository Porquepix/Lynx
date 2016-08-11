package com.lynx.core.cast;

import static org.junit.Assert.*;

import org.junit.Test;

import com.lynx.core.cast.StringDoubleCaster;

public class StringDoubleCasterTest {

	private StringDoubleCaster caster = new StringDoubleCaster();

	@Test
	public void testCast() {
		assertFalse(caster.cast("test").isPresent());
		assertEquals(caster.cast("33.3").get(), new Double(33.3));
		assertFalse(caster.cast(null).isPresent());
	}

}
