package org.babinkuk.lineparser.generic.staticformat;

import java.util.ArrayList;
import java.util.List;

public class DefaultParsedRecord implements ParsedRecord {
	
	ParsedObject[] parsedObjects = new ParsedObject[0];
	String[] sourceObjects = new String[0];
	ParseException[] exceptionObjects = new ParseException[0];
	ArrayList erroList = new ArrayList(0);
		
	@Override
	public ParsedObject getParsedObject(int index) {
		return parsedObjects[index];
	}

	@Override
	public ParseException getExceptionObject(int index) {
		return exceptionObjects[index];
	}

	@Override
	public int getCount() {
		return parsedObjects.length;
	}
	
	@Override
	public int getExceptionObjectCount() {
		int c = 0;
		for (Object o : exceptionObjects) {
			if (o != null) {
				c++;
			}
		}
		return c;
	}

	@Override
	public ParseException[] getExceptionObjects() {
		return exceptionObjects;
	}

	@Override
	public void setParsedObjects(ParsedObject[] parsedObjects) {
		this.parsedObjects = parsedObjects;
	}

	@Override
	public ParsedObject[] getParsedObjects() {
		return parsedObjects;
	}

	@Override
	public void setSourceObjects(String[] sourceObjects) {
		this.sourceObjects = sourceObjects;
	}

	@Override
	public String[] getSourceObjects() {
		return sourceObjects;
	}

	@Override
	public String getSourceObject(int index) {
		return sourceObjects[index];
	}

	@Override
	public void setExceptionObjects(ParseException[] exceptionObjects) {
		this.exceptionObjects = exceptionObjects;
	}

	@Override
	public ParsedRecord createParsedRecordInstance() {
		return new DefaultParsedRecord();
	}

	@Override
	public ParseException getParsedException(int index) {
		return null;
	}

	@Override
	public List getErrorList() {
		return erroList;
	}

	@Override
	public void clearErrorList() {
		erroList.clear();
	}

	@Override
	public void addError(Object error) {
		erroList.add(error);
	}

	@Override
	public boolean equals(Object compareValue, int index) {
		return true;
	}

	@Override
	public boolean isLogicalGroupValid(String groupName) {
		return false;
	}
}
