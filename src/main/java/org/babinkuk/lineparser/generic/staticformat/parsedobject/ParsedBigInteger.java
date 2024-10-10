package org.babinkuk.lineparser.generic.staticformat.parsedobject;

import java.math.BigInteger;

import org.babinkuk.lineparser.generic.staticformat.ParsedObject;

public class ParsedBigInteger implements ParsedObject {
	
	BigInteger value = null;
	
	public ParsedBigInteger() throws Exception {
		throw new Exception("Invalid constructor");
	}
	
	public ParsedBigInteger(BigInteger value) {
		this.value = value;
	}

	@Override
	public int compareTo(Object o) {
		if (o instanceof BigInteger) {
			BigInteger i = (BigInteger) o;
			return value.compareTo(i);
		} else {
			try {
				BigInteger i = new BigInteger(o.toString());
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
