package org.babinkuk.parser.generic.staticformat;

import java.util.ArrayList;
import java.util.List;

public abstract class ParsedRecordAbstract implements ParsedRecord {
	
	protected ParsedObject[] parsedObjects;
	protected String[] sourceObjects;
	protected ParseException[] exceptionObjects;
	protected ArrayList<String> errorList;
	
	public ParsedRecordAbstract() {
	}

	public ParsedRecordAbstract(int fieldNum) {
		parsedObjects = new ParsedObject[fieldNum];
		sourceObjects = new String[fieldNum];
		exceptionObjects = new ParseException[fieldNum];
		
		errorList = new ArrayList<String>(0);
	}

	@Override
	public ParsedObject getParsedObject(int index) {
		return parsedObjects[index];
	}
	
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
	
	public ParsedObject[] getParsedObjects() {
		return parsedObjects;
	}

	@Override
	public String[] getSourceObjects() {
		return sourceObjects;
	}

	@Override
	public void setSourceObjects(String[] sourceObjects) {
		this.sourceObjects = sourceObjects;
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List getErrorList() {
		return errorList;
	}

	@Override
	public void clearErrorList() {
		errorList.clear();
	}

	@Override
	public void addError(Object error) {
		errorList.add((String) error);
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
