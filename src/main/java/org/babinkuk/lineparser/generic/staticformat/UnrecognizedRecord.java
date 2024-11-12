package org.babinkuk.lineparser.generic.staticformat;

import java.util.ArrayList;
import java.util.List;

public class UnrecognizedRecord implements ParsedRecord {
	
	private String line = "";
	
	public UnrecognizedRecord() {
	}
	
	public UnrecognizedRecord(String line) {
		if (line != null) {
			this.line = line;
		}
	}

	@Override
	public ParsedObject getParsedObject(int index) {
		return null;
	}

	@Override
	public ParseException getParsedException(int index) {
		return null;
	}

	@Override
	public ParsedObject[] getParsedObjects() {
		return new ParsedObject[0];
	}

	@Override
	public void setParsedObjects(ParsedObject[] parsedObjects) {
	}

	@Override
	public String[] getSourceObjects() {
		return new String[0];
	}

	@Override
	public void setSourceObjects(String[] sourceObjects) {
	}

	@Override
	public String getSourceObject(int index) {
		return null;
	}

	@Override
	public ParseException[] getExceptionObjects() {
		return new ParseException[0];
	}

	@Override
	public void setExceptionObjects(ParseException[] exceptionObjects) {
	}

	@Override
	public int getCount() {
		return 0;
	}

	@Override
	public int getExceptionObjectCount() {
		return 0;
	}

	@Override
	public ParsedRecord createParsedRecordInstance() {
		return new UnrecognizedRecord();
	}

	@Override
	public List getErrorList() {
		return new ArrayList<String>();
	}

	@Override
	public void clearErrorList() {
	}

	@Override
	public void addError(Object error) {
	}

	@Override
	public boolean equals(Object compareValue, int index) {
		return true;
	}

	@Override
	public boolean isLogicalGroupValid(String groupName) {
		return false;
	}

	@Override
	public String toString() {
		return "UnrecognizedRecord [line=" + line + "]";
	}

	@Override
	public ParseException getExceptionObject(int i) {
		return null;
	}
	
}
