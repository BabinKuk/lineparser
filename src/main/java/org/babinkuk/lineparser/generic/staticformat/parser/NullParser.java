package org.babinkuk.lineparser.generic.staticformat.parser;

import org.babinkuk.lineparser.generic.staticformat.ObjectParser;
import org.babinkuk.lineparser.generic.staticformat.ParseException;
import org.babinkuk.lineparser.generic.staticformat.ParsedObject;
import org.babinkuk.lineparser.generic.staticformat.parsedobject.ParsedNull;
import org.babinkuk.lineparser.generic.staticformat.parsedobject.ParsedString;

public class NullParser implements ObjectParser {

	public NullParser() {
	}
	
	@Override
	public ParsedObject parse(ParsedObject input, int sourceLength) throws ParseException {
		return new ParsedNull();
	}

	@Override
	public ParsedObject construct(ParsedObject output, int sourceLength) throws ParseException {
		return new ParsedString("");
	}

}
