package com.lynx.core.game.save;

import java.io.Closeable;
import java.io.Flushable;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

public class SaveWriter implements Closeable, Flushable {

	private ObjectOutputStream out;
	
	public SaveWriter(OutputStream out) throws IOException {
		this.out = new ObjectOutputStream(out);
    }
	
	public void write(Save save) throws IOException {
		out.writeObject(save);
	}
	
	public void end(Save save) throws IOException {
		write(save);
		flush();
		close();
	}
	
	@Override
    public void flush() throws IOException {
	    out.flush();
    }

	@Override
    public void close() throws IOException {
	    out.close();
    }

}
