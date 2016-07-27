package core.namespace;

import static org.junit.Assert.*;

import org.junit.Test;

public class NamespaceTest {

    private Namespace ns1 = new Namespace("a.b.c.d");
    private Namespace ns2 = new Namespace("e.f.g");

    @Test
    public void testGetKey() {
	assertEquals(ns1.getKey(), "a.b.c.d");
	assertEquals(ns2.getKey(), "e.f.g");
    }

    @Test
    public void testMerge() {
	Namespace ns3 = ns1.merge(ns2);
	assertEquals(ns3.getKey(), "a.b.c.d.e.f.g");
	
	ns3 = ns1.append("k");
	assertEquals(ns3.getKey(), "a.b.c.d.k");
	
	ns3 = ns2.prepend("l");
	assertEquals(ns3.getKey(), "l.e.f.g");
    }
    
    @Test
    public void testSegment() {
	assertEquals(ns1.getFirstSegment(), "a");
	assertEquals(ns1.getLastSegment(), "d");
	assertEquals(ns1.getSubkey(1, 4), "b.c.d");
	assertEquals(ns1.getSubkey(0, 4), "a.b.c.d");
	assertEquals(ns1.getSubkey(0, 3), "a.b.c");
	assertEquals(ns1.getSubkey(1, 3), "b.c");
    }

    @Test
    public void testGetResource() {
	Resource r1 = ns1.getResource("c.d");
	assertEquals(r1.getNamespace(), ns1.merge(new Namespace("c")));
	assertEquals(r1.getId(), "d");
    }
    
    
    @Test
    public void testEquals() {
	Namespace ns3 = new Namespace("a.b.c.d");
	assertEquals(ns1, ns3);
    }

    @Test
    public void testHashCode() {
	Namespace ns3 = new Namespace("a.b.c.d");
	assertEquals(ns1.hashCode(), ns3.hashCode());
    }

}
