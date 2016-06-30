package core.key;

import org.junit.Test;

import junit.framework.TestCase;

public class ContentKeyTest extends TestCase {

	private ContentKey ck1 = new ContentKey("a.b.c.d.e");
	private ContentKey ck2 = new ContentKey("g.h");
	
	@Test
	public void testGetFileKey() {
		FileKey fk = new FileKey("a.b.c.d");
		assertEquals(ck1.getFileKey(), fk);
	}
	
	@Test
	public void testGetContentId() {
		assertEquals(ck1.getContentId(), "e");
		assertEquals(ck2.getContentId(), "h");
	}
	
	@Test
	public void testEquals() {
		ContentKey ck3 = new ContentKey("a.b.c.d.e");
		assertEquals(ck1, ck3);
	}
	
}
