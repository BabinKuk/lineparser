package org.babinkuk.parser.generic.staticformat;

public class ParseException extends Exception {

	Object recevedObject = null;
	Object reportObject = null;
	
	public ParseException() {
		// TODO Auto-generated constructor stub
	}
	
	public ParseException(String message) {
		super(message);
	}
	
	public ParseException(Throwable cause) {
		super(cause);
	}
	
	public ParseException(String message, Throwable cause) {
		super(message, cause);
	}

	public ParseException(Object recevedObject, Object reportObject, String description) {
		super(description);
		this.recevedObject = recevedObject;
		this.reportObject = reportObject;
	}

	public Object getRecevedObject() {
		return recevedObject;
	}

	public Object getReportObject() {
		return reportObject;
	}
	
}
