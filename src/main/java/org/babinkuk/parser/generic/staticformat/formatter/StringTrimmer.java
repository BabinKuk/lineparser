package org.babinkuk.parser.generic.staticformat.formatter;

import org.babinkuk.parser.generic.staticformat.ObjectParser;
import org.babinkuk.parser.generic.staticformat.ParseException;
import org.babinkuk.parser.generic.staticformat.ParsedObject;
import org.babinkuk.parser.generic.staticformat.parsedobject.ParsedNull;
import org.babinkuk.parser.generic.staticformat.parsedobject.ParsedString;

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
	public ParsedObject construct(ParsedObject output, int sourceLength) {
		ParsedObject parsedObject = null;
		
//		StringBuffer outputStringBuffer = new StringBuffer();
//		
//		if (output.toString() != null) {
//			outputStringBuffer.append(output.toString());
//		}
//		
//		while (outputStringBuffer.length() < sourceLength) {
//			outputStringBuffer.append(" ");
//		}
//		
//		parsedObject = new ParsedString(outputStringBuffer.toString());
//		
//		if (embeddedParser != null) {
//			parsedObject = embeddedParser.construct(parsedObject, sourceLength);
//		}
		
		return parsedObject;
	}

}
