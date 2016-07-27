package core.validation;

import static org.junit.Assert.*;

import org.junit.Test;

public class TypeValidatorTest {

    private Validator v1 = new TypeValidator(Integer.class);
    private Validator v2 = new TypeValidator(String.class);
    private Validator v3 = new TypeValidator(Object.class);
    
    @Test
    public void testValidate() {
	assertTrue(v1.validate(3));
	assertTrue(v1.validate(100));
	assertFalse(v1.validate('b'));
	assertFalse(v1.validate("2"));
	assertFalse(v1.validate(new Object()));
	
	assertTrue(v2.validate("test"));
	assertTrue(v2.validate(""));
	assertFalse(v2.validate('b'));
	assertFalse(v2.validate(2));
	assertFalse(v2.validate(new Object()));
	
	assertFalse(v3.validate("test"));
	assertFalse(v3.validate(2));
	assertFalse(v3.validate('b'));
	assertTrue(v3.validate(new Object()));
    } 
    
}
