package org.babinkuk.parser.generic.staticformat.parsedobject;

import java.math.BigDecimal;

import org.babinkuk.parser.generic.staticformat.ParsedObject;

public class ParsedBigDecimal implements ParsedObject {
	
	BigDecimal value = null;
	
	public ParsedBigDecimal() throws Exception {
		throw new Exception("Invalid constructor");
	}
	
	public ParsedBigDecimal(BigDecimal value) {
		this.value = value;
	}

	@Override
	public int compareTo(Object o) {
		if (o instanceof BigDecimal) {
			BigDecimal i = (BigDecimal) o;
			return value.compareTo(i);
		} else {
			try {
				BigDecimal i = new BigDecimal(o.toString());
				return value.compareTo(i);	
			} catch (NumberFormatException e) {
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
