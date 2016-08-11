package com.lynx.core.game.save;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;

public class SaveReader implements Closeable {
	
	private ObjectInputStream in;
	
	public SaveReader(InputStream in) throws IOException {
		this.in = new ObjectInputStream(in);
	}
	
	public Save read() throws ClassNotFoundException, IOException {
		return (Save) in.readObject();
	}
	
	public Save end() throws ClassNotFoundException, IOException {
		Save save = read();
		close();
		return save;
	}

	@Override
    public void close() throws IOException {
		in.close();
    }

}
