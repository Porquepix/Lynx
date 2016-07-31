package com.lynx.core;

import static org.junit.Assert.*;

import org.junit.Test;

import com.lynx.core.Core;

public abstract class CoreTest {

    @Test
    public void testSingleton() {
	assertSame(Core.getInstance(), Core.getInstance());
    }

}
