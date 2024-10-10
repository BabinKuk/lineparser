package org.babinkuk.lineparser.generic.staticformat.parsedobject;

import org.babinkuk.lineparser.generic.staticformat.ParsedObject;

public class ParsedByte implements ParsedObject {
	
	Byte value = null;
	
	public ParsedByte() throws Exception {
		throw new Exception("Invalid constructor");
	}
	
	public ParsedByte(Byte value) {
		this.value = value;
	}

	@Override
	public int compareTo(Object o) {
		if (o instanceof Byte) {
			Byte i = (Byte) o;
			return value.compareTo(i);
		} else {
			try {
				Byte i = Byte.parseByte(o.toString());
				return value.compareTo(i);	
			} catch (NumberFormatException e) {
				return -1;
			}
		}
	}

	@Override
	public Object getValue() {
		return value;
	}
	
	public String toString() {
		if (value == null) {
			return null;
		}
		
		return value.toString();
	}

}
