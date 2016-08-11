package com.lynx.core.validation;

import static org.junit.Assert.*;

import org.junit.Test;

import com.lynx.core.validation.RangeValidator;
import com.lynx.core.validation.Validator;

public class RangeValidatorTest {

	private Validator v1 = new RangeValidator(0, 10);
	private Validator v2 = new RangeValidator(-10, 0);

	@Test
	public void testValidate() {
		assertTrue(v1.validate(3));
		assertFalse(v1.validate(100));
		assertTrue(v1.validate("abcde"));
		assertFalse(v1.validate("abcdefghijk"));
		assertFalse(v1.validate(new Object()));

		assertTrue(v2.validate(-5));
		assertTrue(v2.validate(-10));
		assertFalse(v2.validate(0));
		assertFalse(v2.validate("abcde"));
		assertFalse(v2.validate(""));
	}

}
