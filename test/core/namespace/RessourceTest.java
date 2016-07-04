package core.namespace;

import static org.junit.Assert.*;

import org.junit.Test;

public class RessourceTest {

    private Ressource res1 = new Ressource("a.b.c.d.e");
    private Ressource res2 = new Ressource("g.h");

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
	Ressource res3 = new Ressource("a.b.c.d.e");
	assertEquals(res1, res3);
    }

}
