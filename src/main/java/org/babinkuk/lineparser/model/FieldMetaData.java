package org.babinkuk.lineparser.model;

import org.babinkuk.lineparser.generic.staticformat.ObjectParser;

public class FieldMetaData {
	
	public String fieldName = null;
	//TODO public String tableName = null;
	public int length;
	public ObjectParser objectParser;
	
	public FieldMetaData(String fieldName, int length, ObjectParser objectParser/*, String tableName*/) {
		this.fieldName = fieldName;
		this.length = length;
		this.objectParser = objectParser;
		//this.tableName = tableName;
	}
}
