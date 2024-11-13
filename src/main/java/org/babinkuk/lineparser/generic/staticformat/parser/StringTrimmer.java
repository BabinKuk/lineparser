package org.babinkuk.lineparser.generic.staticformat.parser;

import org.babinkuk.lineparser.generic.staticformat.ObjectParser;
import org.babinkuk.lineparser.generic.staticformat.ParseException;
import org.babinkuk.lineparser.generic.staticformat.ParsedObject;
import org.babinkuk.lineparser.generic.staticformat.parsedobject.ParsedNull;
import org.babinkuk.lineparser.generic.staticformat.parsedobject.ParsedString;

public class StringTrimmer implements ObjectParser {
	
	ObjectParser embeddedParser;
	
	public StringTrimmer() {
		this.embeddedParser = null;
	}
	
	public StringTrimmer(ObjectParser embeddedParser) {
		this.embeddedParser = embeddedParser;
	}
	
	@Override
	public ParsedObject parse(ParsedObject input, int sourceLength) throws ParseException {

		ParsedObject childValue;
		
		if (embeddedParser != null) {
			childValue = embeddedParser.parse(input, sourceLength);
			if (childValue == null || childValue instanceof ParsedNull) {
				throw new ParseException(input, null, "Invalid child parser return value!");
			}
		} else {
			childValue = input;
			if (childValue == null || childValue instanceof ParsedNull) {
				throw new ParseException(input, null, "Invalid argument!");
			}
		}
		
		String inputString = childValue.toString();
		
		return new ParsedString(inputString.trim());
	}

	@Override
	public ParsedObject construct(ParsedObject output, int sourceLength) throws ParseException {
		ParsedObject parsedObject = null;
		
		String outputString = new String();
		
		if (output.toString() != null) {
			outputString += output.toString();
		}
		
		while (outputString.length() < sourceLength) {
			outputString += " ";
		}
		
		parsedObject = new ParsedString(outputString);
		
		if (embeddedParser != null) {
			parsedObject = embeddedParser.construct(parsedObject, sourceLength);
		}
		
		return parsedObject;
	}

}
