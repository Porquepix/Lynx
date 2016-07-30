package core.validation;

import static org.junit.Assert.*;

import org.junit.Test;

public class InValidatorTest {

    private Validator v1 = new InValidator(new Object[] { 'a', 't', 'c', 'g' });
    private Validator v2 = new InValidator(new Object[] { "test", 2016, 1.67 });

    @Test
    public void testValidate() {
	assertTrue(v1.validate('a'));
	assertTrue(v1.validate('c'));
	assertFalse(v1.validate('b'));
	assertFalse(v1.validate("2"));
	assertFalse(v1.validate(new Object()));

	assertTrue(v2.validate("test"));
	assertTrue(v2.validate(2016));
	assertTrue(v2.validate(1.67));
	assertFalse(v2.validate('b'));
	assertFalse(v2.validate("2"));
	assertFalse(v2.validate(new Object()));
    }

}
