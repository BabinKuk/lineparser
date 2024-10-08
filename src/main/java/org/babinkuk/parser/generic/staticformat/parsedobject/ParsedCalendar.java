package org.babinkuk.parser.generic.staticformat.parsedobject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.babinkuk.parser.generic.staticformat.ParsedObject;

public class ParsedCalendar implements ParsedObject {
	
	Calendar value = null;
	String dateFormat = null;
	
	public ParsedCalendar() throws Exception {
		throw new Exception("Invalid constructor");
	}
	
	public ParsedCalendar(Calendar value, String dateFormat) {
		this.value = value;
		this.dateFormat = dateFormat;
	}

	@Override
	public int compareTo(Object o) {
		if (o instanceof Calendar) {
			Calendar c = (Calendar) o;
			return value.compareTo(c);
		} else if (o instanceof Date) {
			Date d = (Date) o;
			Calendar c = Calendar.getInstance();
			c.setTime(d);
			return value.compareTo(c);
		} else {
			try {
				SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
				Date d = sdf.parse(o.toString());
				Calendar c = Calendar.getInstance();
				c.setTime(d);
				return value.compareTo(c);	
			} catch (ParseException e) {
				return -1;
			}
		}
	}

	@Override
	public Object getValue() {
		return value;
	}
	
	public String toString() {
		if (value == null) {
			return null;
		}
		
		return value.toString();
	}

}
