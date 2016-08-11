package com.lynx.core.cast;

import static org.junit.Assert.*;

import org.junit.Test;

import com.lynx.core.cast.StringDoubleCaster;
import com.lynx.core.cast.StringIntegerCaster;
import com.lynx.core.cast.StringCasterFactory;
import com.lynx.core.cast.StringStringCaster;

public class StringCasterFacotryTest {

	private StringCasterFactory factory = new StringCasterFactory();

	@Test
	public void testGetCaster() {
		assertEquals(factory.getCaster(Integer.class).get().getClass(), StringIntegerCaster.class);
		assertEquals(factory.getCaster(Double.class).get().getClass(), StringDoubleCaster.class);
		assertEquals(factory.getCaster(String.class).get().getClass(), StringStringCaster.class);
		assertFalse(factory.getCaster(Object.class).isPresent());
		assertFalse(factory.getCaster(null).isPresent());
	}

}
