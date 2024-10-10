package org.babinkuk.lineparser.service;

import org.babinkuk.lineparser.common.ApiResponse;
import org.babinkuk.lineparser.generic.staticformat.ParseException;
import org.babinkuk.lineparser.generic.staticformat.RecordUnrecognizedException;
import org.babinkuk.lineparser.model.ProcessData;

public interface LineParserService {
	
	/**
	 * parse line
	 * @throws ParseException 
	 * @throws RecordUnrecognizedException 
	 * 
	 */
	public ApiResponse parseLine(ProcessData processData) throws RecordUnrecognizedException, ParseException;
	
	/**
	 * construct line
	 * @throws ParseException 
	 * @throws RecordUnrecognizedException 
	 * 
	 */
	public ApiResponse constructLine(ProcessData processData) throws RecordUnrecognizedException, ParseException;
	
}
