package org.babinkuk.lineparser.generic.staticformat.parser;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.babinkuk.lineparser.generic.staticformat.ObjectParser;
import org.babinkuk.lineparser.generic.staticformat.ParseException;
import org.babinkuk.lineparser.generic.staticformat.ParsedObject;
import org.babinkuk.lineparser.generic.staticformat.parsedobject.ParsedCalendar;
import org.babinkuk.lineparser.generic.staticformat.parsedobject.ParsedNull;
import org.babinkuk.lineparser.generic.staticformat.parsedobject.ParsedString;

public class CalendarParser implements ObjectParser {
	
	public static final int PROCESS_0_ACCEPT = 0;
	public static final int PROCESS_0_NULL = 1;
	public static final int PROCESS_0_EXCEPTION = 2;
	
	ObjectParser embeddedParser;
	String df;
	int process;
	Calendar oldest, newest;
	boolean oldestInclusive, newestInclusive;
	
	
	public CalendarParser(ObjectParser embeddedParser, String df, int process) {
		super();
		this.embeddedParser = embeddedParser;
		this.df = df;
		this.process = process;
		this.oldest = null;
		this.newest = null;
		this.oldestInclusive = false;
		this.newestInclusive = false;
	}

	public CalendarParser(ObjectParser embeddedParser, String df, int process, String oldest, String newest) throws Exception {
		
		this.embeddedParser = embeddedParser;
		this.df = df;
		this.process = process;
		
		DateFormat formatter = new SimpleDateFormat(df);
		
		if (oldest != null) {
			this.oldest = Calendar.getInstance();
			this.oldest.setTime(formatter.parse(oldest));
		} else {
			this.oldest = null;
		}
		
		if (newest != null) {
			this.newest = Calendar.getInstance();
			this.newest.setTime(formatter.parse(oldest));
		} else {
			this.newest = null;
		}
	}
	
	public CalendarParser(ObjectParser embeddedParser, String df, int process, String oldest, String newest,
			boolean oldestInclusive, boolean newestInclusive) throws Exception {
		
		this.embeddedParser = embeddedParser;
		this.df = df;
		this.process = process;
		
		DateFormat formatter = new SimpleDateFormat(df);
		
		if (oldest != null) {
			this.oldest = Calendar.getInstance();
			this.oldest.setTime(formatter.parse(oldest));
		} else {
			this.oldest = null;
		}
		
		if (newest != null) {
			this.newest = Calendar.getInstance();
			this.newest.setTime(formatter.parse(oldest));
		} else {
			this.newest = null;
		}
		
		this.oldestInclusive = oldestInclusive;
		this.newestInclusive = newestInclusive;
	}

	@Override
	public ParsedObject parse(ParsedObject input, int sourceLength) throws ParseException {

		if (df == null) {
			throw new ParseException(null, null, "Invalid init data!");
		}
		
		ParsedObject childValue;
		
		if (embeddedParser != null) {
			childValue = embeddedParser.parse(input, sourceLength);
			if (childValue == null || childValue instanceof ParsedNull) {
				throw new ParseException(null, null, "Invalid child parser data!");
			}
		} else {
			childValue = input;
			if (childValue == null || childValue instanceof ParsedNull) {
				throw new ParseException(null, null, "Invalid child argument!");
			}
		}
		
		String inputString = childValue.toString();
		
		if (inputString.replaceAll("0", "").trim().equals("")) {
			if (process == PROCESS_0_ACCEPT) {
				// do nothing
			}
			else if (process == PROCESS_0_NULL) {
				return new ParsedNull();
			}
			else if (process == PROCESS_0_EXCEPTION) {
				throw new ParseException(input, null, null);
			}
			else {
				throw new ParseException(input, null, null);
			}
		}
		
		try {
			Calendar calendar = Calendar.getInstance();
			DateFormat formatter = new SimpleDateFormat(df);
			Date date = (Date) formatter.parse(inputString);
			calendar.setTime(date);
			
			if (!inputString.equals(formatter.format(calendar.getTime()))) {
				throw new ParseException(childValue, calendar, "Date parsing failed : " + inputString + ", parsed value " + formatter.format(calendar.getTime()));
			}
			if (oldest != null && oldestInclusive && calendar.compareTo(oldest) < 0) {
				throw new ParseException(childValue, calendar, "Date older than allowed");
			}
			if (oldest != null && !oldestInclusive && calendar.compareTo(oldest) <= 0) {
				throw new ParseException(childValue, calendar, "Date older than allowed");
			}
			if (newest != null && newestInclusive && calendar.compareTo(newest) > 0) {
				throw new ParseException(childValue, calendar, "Date newer than allowed");
			}
			if (newest != null && !newestInclusive && calendar.compareTo(newest) >= 0) {
				throw new ParseException(childValue, calendar, "Date newer than allowed");
			}
			
			return new ParsedCalendar(calendar, df);
		} catch (Exception e) {
			throw new ParseException(childValue, e, "Date parse failed: " + e.toString());
		}
	}

	@Override
	public ParsedObject construct(ParsedObject output, int sourceLength) throws ParseException {
		
		ParsedObject parsedObject = null;
		
		if (output instanceof ParsedCalendar && output.getValue() != null) {
			ParsedCalendar parsedCalendar = (ParsedCalendar) output;

			DateFormat formatter = new SimpleDateFormat(df);
			parsedObject = new ParsedString(formatter.format(((Calendar) parsedCalendar.getValue()).getTime()));
		}
		else if (output instanceof ParsedNull || output.getValue() == null) {
			String outputString = new String();
			for (int i=0; i<sourceLength; i++) {
				outputString += "0";
			}

			parsedObject = new ParsedString(outputString);
		}
		else if (output.getValue().toString() == "" || output.getValue().toString() == null) {
			String outputString = new String();
			for (int i=0; i<sourceLength; i++) {
				outputString += " ";
			}

			parsedObject = new ParsedString(outputString);
		}
		else {
			throw new ParseException("Calendar construct invalid!");
		}
		
		if (embeddedParser == null) {
			parsedObject = embeddedParser.construct(parsedObject, sourceLength);
		}
		
		return parsedObject;
	}

}
