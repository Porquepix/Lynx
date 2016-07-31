package com.lynx.core.namespace;

import static org.junit.Assert.*;

import org.junit.Test;

import com.lynx.core.namespace.Extension;

public class ExtensionTest {

    @Test
    public void testToString() {
	assertEquals(Extension.JSON.toString(), "json");
    }

}
