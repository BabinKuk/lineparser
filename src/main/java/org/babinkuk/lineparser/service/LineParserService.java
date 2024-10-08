package org.babinkuk.lineparser.service;

import org.babinkuk.lineparser.common.ApiResponse;
import org.babinkuk.lineparser.model.RequestData;
import org.babinkuk.parser.generic.staticformat.ParseException;
import org.babinkuk.parser.generic.staticformat.RecordUnrecognizedException;

public interface LineParserService {
	
	/**
	 * parse line
	 * @throws ParseException 
	 * @throws RecordUnrecognizedException 
	 * 
	 */
	public ApiResponse parseLine(RequestData requestData) throws RecordUnrecognizedException, ParseException;
	
}
