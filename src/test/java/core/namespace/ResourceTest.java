package core.namespace;

import static org.junit.Assert.*;

import org.junit.Test;

public class ResourceTest {

    private Resource res1 = new Resource("a.b.c.d.e");
    private Resource res2 = new Resource("g.h");

    @Test
    public void testGetNamespace() {
	Namespace fk = new Namespace("a.b.c.d");
	assertEquals(res1.getNamespace(), fk);
    }

    @Test
    public void testGetId() {
	assertEquals(res1.getId(), "e");
	assertEquals(res2.getId(), "h");
    }

    @Test
    public void testEquals() {
	Resource res3 = new Resource("a.b.c.d.e");
	assertEquals(res1, res3);
    }

}
