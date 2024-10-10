package org.babinkuk.lineparser.generic.staticformat;

import java.util.ArrayList;
import java.util.List;

public class UnrecognizedRecord implements ParsedRecord {
	
	private String line = "";
	
	public UnrecognizedRecord() {
		// TODO Auto-generated constructor stub
	}
	
	public UnrecognizedRecord(String line) {
		if (line != null) {
			this.line = line;
		}
	}

	@Override
	public ParsedObject getParsedObject(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ParseException getParsedException(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ParsedObject[] getParsedObjects() {
		// TODO Auto-generated method stub
		return new ParsedObject[0];
	}

	@Override
	public void setParsedObjects(ParsedObject[] parsedObjects) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String[] getSourceObjects() {
		// TODO Auto-generated method stub
		return new String[0];
	}

	@Override
	public void setSourceObjects(String[] sourceObjects) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getSourceObject(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ParseException[] getExceptionObjects() {
		// TODO Auto-generated method stub
		return new ParseException[0];
	}

	@Override
	public void setExceptionObjects(ParseException[] exceptionObjects) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getExceptionObjectCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ParsedRecord createParsedRecordInstance() {
		// TODO Auto-generated method stub
		return new UnrecognizedRecord();
	}

	@Override
	public List getErrorList() {
		// TODO Auto-generated method stub
		return new ArrayList<String>();
	}

	@Override
	public void clearErrorList() {
		// TODO Auto-generated method stub
	}

	@Override
	public void addError(Object error) {
		// TODO Auto-generated method stub
	}

	@Override
	public boolean equals(Object compareValue, int index) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isLogicalGroupValid(String groupName) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String toString() {
		return "UnrecognizedRecord [line=" + line + "]";
	}

	@Override
	public ParseException getExceptionObject(int i) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
