package org.babinkuk.lineparser.generic.staticformat;

public interface ParserCallback extends Comparable<Object> {
	
	public void parserCallback(String str, Object parsedObject);	
}
