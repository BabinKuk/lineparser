package org.babinkuk.lineparser.generic.staticformat;

import java.util.List;

public interface ParsedRecord {
	
	public ParsedObject getParsedObject(int index);
	public ParseException getParsedException(int index);
	
	public ParsedObject[] getParsedObjects();
	public void setParsedObjects(ParsedObject[] parsedObjects);
	
	public String[] getSourceObjects();
	public void setSourceObjects(String[] sourceObjects);
	public String getSourceObject(int index);
	
	public ParseException[] getExceptionObjects();
	public void setExceptionObjects(ParseException[] exceptionObjects);
	public ParseException getExceptionObject(int i);
	
	public int getCount();
	public int getExceptionObjectCount();
	
	public ParsedRecord createParsedRecordInstance();
	
	public List getErrorList();
	public void clearErrorList();
	public void addError(Object error);
	
	public boolean equals(Object compareValue, int index);
	
	// grupa polja koja spadaju u jedan tip greske
	// polja se mogu preklapati medju grupama
	public boolean isLogicalGroupValid(String groupName);

}
