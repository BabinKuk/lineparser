package org.babinkuk.parser.generic.staticformat.parsedobject;

import org.babinkuk.parser.generic.staticformat.ParsedObject;

public class ParsedInteger implements ParsedObject {
	
	Integer value = null;
	
	public ParsedInteger() throws Exception {
		throw new Exception("Invalid constructor");
	}
	
	public ParsedInteger(int value) {
		this.value = value;
	}

	@Override
	public int compareTo(Object o) {
		if (o instanceof Integer) {
			Integer i = (Integer) o;
			return value.compareTo(i);
		} else {
			try {
				Integer i = Integer.parseInt(o.toString());
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
