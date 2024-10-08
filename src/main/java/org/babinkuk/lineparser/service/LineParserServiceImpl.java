package org.babinkuk.lineparser.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.babinkuk.lineparser.common.ApiResponse;
import org.babinkuk.lineparser.model.RecordBase;
import org.babinkuk.lineparser.model.RecordTypeOne;
import org.babinkuk.lineparser.model.RecordTypeTwo;
import org.babinkuk.lineparser.model.RequestData;
import org.babinkuk.lineparser.model.ResponseData;
import org.babinkuk.parser.generic.staticformat.LineCollectionParser;
import org.babinkuk.parser.generic.staticformat.ParseException;
import org.babinkuk.parser.generic.staticformat.ParsedRecord;
import org.babinkuk.parser.generic.staticformat.RecordUnrecognizedException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class LineParserServiceImpl implements LineParserService {

	private final Logger log = LogManager.getLogger(getClass());
	
	public LineParserServiceImpl() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public ApiResponse parseLine(RequestData requestData) throws ParseException, RecordUnrecognizedException {
		// TODO Auto-generated method stub
		log.info("parseLine {}", requestData);
		
		ApiResponse response = new ApiResponse();
		response.setStatus(HttpStatus.OK);
		response.setMessage("PARSING SUCCESS");
		
		LineCollectionParser lineCollectionParser = new LineCollectionParser();
		lineCollectionParser.addLineParser(RecordTypeOne.getLineParser());
		lineCollectionParser.addLineParser(RecordTypeTwo.getLineParser());
		// TODO add more
		//lineCollectionParser.addLineParser(RecordTypeOne.getLineParser());
		
		ResponseData rd;
		ParsedRecord parsedRecord;
		
		try {
			parsedRecord = lineCollectionParser.parseLine(requestData.getLine());
			
			if (parsedRecord instanceof RecordBase) {
				
				log.info("parsedRecord : {}", parsedRecord.getClass());
				
				rd = ((RecordBase) parsedRecord).processRecord();
				
				if (!rd.getErrors().isEmpty()) {
					response.setMessage("PARSING FAILED");
					response.setErrors(rd.getErrors());
				} else {
					response.setResponseData(rd);
				}
				
			} else {
				String message = String.format("ERROR PARSING MESSAGE, RECORD UNRECOGNIZED : %s", requestData.getLine());
				log.error(message);
				throw new ParseException(message);
			}
		} catch (RecordUnrecognizedException e) {
			String message = String.format("ERROR PARSING MESSAGE, RECORD UNRECOGNIZED : %s", requestData.getLine());
			log.error(message);
			//e.printStackTrace();
			throw new RecordUnrecognizedException(message);
		} catch (ParseException e) {
			String message = String.format("ERROR PARSING MESSAGE : %s", requestData.getLine());
			log.error(message);
			//e.printStackTrace();
			throw new ParseException(message);
		}
		
		return response;
	}

}
