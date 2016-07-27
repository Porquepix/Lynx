package core.validation;

import static org.junit.Assert.*;

import org.junit.Test;

public class GlobalValidatorBuilderTest {
    
    private Validator v1 = new GlobalValidatorBuilder().type(Integer.class).range(0, 3).build();
    private Validator v2 = new GlobalValidatorBuilder().build();
    private Validator v3 = new GlobalValidatorBuilder().in("abcde", 2, 3.14).build();
    
    @Test
    public void testValidator1() {
	assertTrue(v1.validate(2));
	assertFalse(v1.validate(-1));
	assertFalse(v1.validate("2"));
	assertFalse(v1.validate(new Object()));
    } 
    
    @Test
    public void testValidator2() {
	assertTrue(v2.validate(2));
	assertTrue(v2.validate(-1));
	assertTrue(v2.validate("2"));
	assertTrue(v2.validate(new Object()));
    } 
    
    @Test
    public void testValidator3() {
	assertTrue(v3.validate("abcde"));
	assertTrue(v3.validate(2));
	assertTrue(v3.validate(3.14));
	assertFalse(v3.validate(-1));
	assertFalse(v3.validate(new Object()));
    } 

}
