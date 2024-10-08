package org.babinkuk.parser.generic.staticformat.parsedobject;

import org.babinkuk.parser.generic.staticformat.ParsedObject;

public class ParsedNull implements ParsedObject {

	Object value = null;
	
	public ParsedNull() {
	}

	@Override
	public int compareTo(Object o) {
		if (o instanceof ParsedNull) {
			return 0;
		} else {
			return 1;
		}
	}

	@Override
	public Object getValue() {
		return value;
	}
	
	public String toString() {
		return null;
	}

}
