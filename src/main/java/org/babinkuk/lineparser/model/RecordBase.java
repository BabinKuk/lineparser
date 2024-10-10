package org.babinkuk.lineparser.model;

import java.util.ArrayList;
import java.util.List;

import org.babinkuk.lineparser.generic.staticformat.*;
import org.babinkuk.lineparser.generic.staticformat.formatter.StringTrimmer;
import org.babinkuk.lineparser.generic.staticformat.parser.CalendarParser;
import org.babinkuk.lineparser.generic.staticformat.validator.RegexValidator;
import org.babinkuk.lineparser.model.RecordTypeOne.FIELDS;

public abstract class RecordBase extends ParsedRecordAbstract {
	
	public static final ObjectParser cM = new StringTrimmer(new RegexValidator("[^\\s]+.*", null)); // mandatory string
	public static final ObjectParser cO = new StringTrimmer(new RegexValidator(".*", null)); // optional string
	public static final ObjectParser ddmmyyyyM = new CalendarParser(new RegexValidator("[0-9]{4}-[0-9]{2}-[0-9]{2}", null), "yyyy-MM-dd", CalendarParser.PROCESS_0_EXCEPTION); // mandatory date
	public static final ObjectParser ddmmyyyyO = new CalendarParser(new RegexValidator("[0-9]{4}-[0-9]{2}-[0-9]{2}", null), "yyyy-MM-dd", CalendarParser.PROCESS_0_NULL); // optional date
	
	public abstract ProcessData parseRecord();
	public abstract ProcessData constructRecord(ProcessData processData) throws RecordUnrecognizedException, ParseException;
	
	public RecordBase(int fieldNum) {
		super(fieldNum);
	}
	
	public List<String> getErrors() {
		List<String> errors = new ArrayList<String>();
		int i = 0;
		
		for (ParseException paex : getExceptionObjects()) {
			if (paex != null) {
				String error = FIELDS.values()[i].name() + " : " + paex.getMessage();
				errors.add(error);
			}
			++i;
		}
		
		return errors;
	}

	

}
