package com.lynx.core.game.save;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.google.common.hash.Hashing;

public final class Save implements Serializable {

    private static final long serialVersionUID = -296152559909850589L;
    
	private Map<Object, Object> data;
	
	protected Save() {
		data = new HashMap<>();
	}
	
	protected Save(Map<Object, Object> data) {
		this.data = data;
	}
	
	public Object read(String name) {
		return data.get(hash(name));
	}
	
	public void write(String name, Object value) {
		data.put(hash(name), value);
	}
	
	protected void delete(String name) {
		data.remove(hash(name));
	}
	
	protected Object readAndDelete(String name) {
		Object ret = read(name);
		delete(name);
		return ret;
	}
	
	private long hash(String string) {
		return Hashing.murmur3_128().hashUnencodedChars(string).asLong();
	}
	
	public long checksum() {
		return Hashing.murmur3_128().hashInt(hashCode()).asLong();
	}
	
	@Override
	public int hashCode() {
	    return data.hashCode();
	}
	
	@Override
	public String toString() {
	    return data.toString();
	}
	
}
