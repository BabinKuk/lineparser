package org.babinkuk.lineparser.generic.staticformat.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.babinkuk.lineparser.generic.staticformat.ObjectParser;
import org.babinkuk.lineparser.generic.staticformat.ParseException;
import org.babinkuk.lineparser.generic.staticformat.ParsedObject;
import org.babinkuk.lineparser.generic.staticformat.parsedobject.ParsedNull;

public class RegexValidator implements ObjectParser {
	
	private final Logger log = LogManager.getLogger(getClass());
	
	Pattern p;
	ObjectParser embeddedParser;
	
	public RegexValidator(String p, ObjectParser op) {
		this.p = Pattern.compile(p);
		this.embeddedParser = op;
	}
	
	public boolean match(String input) {
		if (input == null) {
			return false;
		}
		
		Matcher m = p.matcher(input);
		return m.matches();
	}
	
	@Override
	public ParsedObject parse(ParsedObject input, int sourceLength) throws ParseException {
		log.info("parse {}:{}", input, sourceLength);
		
		ParsedObject childValue;
		
		log.info("embeddedParser {}:{}:{}", embeddedParser, input, sourceLength);
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
		
		if (match(childValue.toString())) {
			return childValue;
		}
		
		throw new ParseException(childValue, null, "Regex match failed -> Pattern " + p.toString() + ", input = " + input);
	}

	@Override
	public ParsedObject construct(ParsedObject output, int sourceLength) throws ParseException {
		if (embeddedParser != null) {
			return embeddedParser.construct(output, sourceLength);
		} else {
			return output;
		}
	}

}
