package org.babinkuk.lineparser.generic.staticformat.parsedobject;

import org.babinkuk.lineparser.generic.staticformat.ParsedObject;

public class ParsedString implements ParsedObject {
	
	String value = null;
	
	public ParsedString() throws Exception {
		throw new Exception("Invalid constructor");
	}
	
	public ParsedString(String value) {
		this.value = value;
	}

	@Override
	public int compareTo(Object o) {
		return value.compareTo(o.toString());
	}

	@Override
	public String getValue() {
		return value;
	}
	
	public String toString() {
		return value;
	}

}
