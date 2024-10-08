package org.babinkuk.parser.generic.staticformat.parsedobject;

import org.babinkuk.parser.generic.staticformat.ParsedObject;

public class ParsedShort implements ParsedObject {
	
	Short value = null;
	
	public ParsedShort() throws Exception {
		throw new Exception("Invalid constructor");
	}
	
	public ParsedShort(Short value) {
		this.value = value;
	}

	@Override
	public int compareTo(Object o) {
		if (o instanceof Short) {
			Short i = (Short) o;
			return value.compareTo(i);
		} else {
			try {
				Short i = Short.parseShort(o.toString());
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
