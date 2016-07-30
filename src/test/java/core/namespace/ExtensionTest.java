package core.namespace;

import static org.junit.Assert.*;

import org.junit.Test;

public class ExtensionTest {

    @Test
    public void testToString() {
	assertEquals(Extension.JSON.toString(), "json");
    }

}
