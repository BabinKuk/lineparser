package org.babinkuk.parser.generic.staticformat.parser;

import org.babinkuk.parser.generic.staticformat.ObjectParser;
import org.babinkuk.parser.generic.staticformat.ParseException;
import org.babinkuk.parser.generic.staticformat.ParsedObject;
import org.babinkuk.parser.generic.staticformat.parsedobject.ParsedNull;
import org.babinkuk.parser.generic.staticformat.parsedobject.ParsedString;

public class NullParser implements ObjectParser {

	public NullParser() {
	}
	
	@Override
	public ParsedObject parse(ParsedObject input, int sourceLength) throws ParseException {
		// TODO Auto-generated method stub
		return new ParsedNull();
	}

	@Override
	public ParsedObject construct(ParsedObject output, int sourceLength) throws ParseException {
		return new ParsedString("");
	}

}
