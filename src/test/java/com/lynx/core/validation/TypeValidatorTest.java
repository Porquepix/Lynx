package com.lynx.core.validation;

import static org.junit.Assert.*;

import org.junit.Test;

import com.lynx.core.validation.TypeValidator;
import com.lynx.core.validation.Validator;

public class TypeValidatorTest {

	private Validator v1 = new TypeValidator(Integer.class);
	private Validator v2 = new TypeValidator(String.class);
	private Validator v3 = new TypeValidator(Object.class);
	private Validator v4 = new TypeValidator(Void.class);
	private Validator v5 = new TypeValidator(Number.class, false);

	@Test
	public void testValidate() {
		assertTrue(v1.validate(3));
		assertTrue(v1.validate(100));
		assertFalse(v1.validate('b'));
		assertFalse(v1.validate("2"));
		assertFalse(v1.validate(new Object()));
		assertFalse(v1.validate(null));

		assertTrue(v2.validate("test"));
		assertTrue(v2.validate(""));
		assertFalse(v2.validate('b'));
		assertFalse(v2.validate(2));
		assertFalse(v2.validate(new Object()));
		assertFalse(v2.validate(null));

		assertFalse(v3.validate("test"));
		assertFalse(v3.validate(2));
		assertFalse(v3.validate('b'));
		assertTrue(v3.validate(new Object()));
		assertFalse(v3.validate(null));

		assertTrue(v4.validate(null));
		assertFalse(v4.validate(1));
		assertFalse(v4.validate("a1"));

		assertTrue(v5.validate(3));
		assertTrue(v5.validate(100.14));
		assertFalse(v5.validate("1"));
		assertFalse(v5.validate(new Object()));
	}

}
