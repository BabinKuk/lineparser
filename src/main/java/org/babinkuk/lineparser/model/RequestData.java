package org.babinkuk.lineparser.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(value = Include.NON_EMPTY)
public class RequestData {
	
	private String line;

	public RequestData() {
	}
	
	public RequestData(String line) {
		super();
		this.setLine(line);
	}

	public String getLine() {
		return line;
	}

	public void setLine(String line) {
		this.line = line;
	}

	@Override
	public String toString() {
		return "RequestData [line=" + line + "]";
	}
}
