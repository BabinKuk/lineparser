package org.babinkuk.lineparser.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.babinkuk.lineparser.common.ApiResponse;
import org.babinkuk.lineparser.generic.staticformat.LineCollectionParser;
import org.babinkuk.lineparser.generic.staticformat.LineParser;
import org.babinkuk.lineparser.generic.staticformat.ParseException;
import org.babinkuk.lineparser.generic.staticformat.ParsedRecord;
import org.babinkuk.lineparser.generic.staticformat.RecordUnrecognizedException;
import org.babinkuk.lineparser.generic.staticformat.parsedobject.ParsedString;
import org.babinkuk.lineparser.model.RecordBase;
import org.babinkuk.lineparser.model.RecordTypeOne;
import org.babinkuk.lineparser.model.RecordTypeTwo;
import org.babinkuk.lineparser.model.RecordTypeTwo.FIELDS;
import org.babinkuk.lineparser.model.ProcessData;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class LineParserServiceImpl implements LineParserService {

	private final Logger log = LogManager.getLogger(getClass());
	
	public LineParserServiceImpl() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public ApiResponse parseLine(ProcessData processData) throws ParseException, RecordUnrecognizedException {
		//log.info("parseLine {}", requestData);
		
		ApiResponse response = new ApiResponse();
		response.setStatus(HttpStatus.OK);
		response.setMessage("PARSING SUCCESS");
		
		LineCollectionParser lineCollectionParser = new LineCollectionParser();
		lineCollectionParser.addLineParser(RecordTypeOne.getLineParser());
		lineCollectionParser.addLineParser(RecordTypeTwo.getLineParser());
		// TODO add more
		//lineCollectionParser.addLineParser(RecordTypeOne.getLineParser());
		log.info(lineCollectionParser.toString());
		for (LineParser lp : lineCollectionParser.getLineParserList()) {
			log.info("lp {}:{}", lp.getParsedRecordFactory().getClass().getSimpleName(), lp.getSegmentParserCollection().size());
		}
		
		ProcessData rd;
		ParsedRecord parsedRecord;
		
		try {
			parsedRecord = lineCollectionParser.parseLine(processData.getLine());
			
			if (parsedRecord instanceof RecordBase) {
				log.info("parsedRecord : {}", parsedRecord.getClass().getSimpleName());
				rd = ((RecordBase) parsedRecord).parse();
				
				if (!rd.getErrors().isEmpty()) {
					response.setMessage("PARSING FAILED");
					response.setErrors(rd.getErrors());
				} else {
					response.setResponseData(rd);
				}
			} else {
				String message = String.format("ERROR PARSING MESSAGE, RECORD UNRECOGNIZED : %s", processData.getLine());
				log.error(message);
				throw new ParseException(message);
			}
		} catch (RecordUnrecognizedException e) {
			String message = String.format("ERROR PARSING MESSAGE, RECORD UNRECOGNIZED : %s", processData.getLine());
			log.error(message);
			//e.printStackTrace();
			throw new RecordUnrecognizedException(message);
		} catch (ParseException e) {
			String message = String.format("ERROR PARSING MESSAGE : %s", processData.getLine());
			log.error(message);
			//e.printStackTrace();
			throw new ParseException(message);
		}
		
		return response;
	}

	@Override
	public ApiResponse constructLine(ProcessData processData) throws RecordUnrecognizedException, ParseException {
		
		ApiResponse response = new ApiResponse();
		response.setStatus(HttpStatus.OK);
		response.setMessage("CONSTRUCTING SUCCESS");
		
		LineCollectionParser lineCollectionParser = new LineCollectionParser();
		lineCollectionParser.addLineParser(RecordTypeOne.getLineParser());
		lineCollectionParser.addLineParser(RecordTypeTwo.getLineParser());
		
		ParsedRecord parsedRecord;
		
		try {
			if ("100".equalsIgnoreCase(processData.getSifra())) {
				parsedRecord = new RecordTypeOne();
			}
			else if ("200".equalsIgnoreCase(processData.getSifra())) {
				parsedRecord = new RecordTypeTwo();
			}
			else {
				String message = String.format("ERROR PARSING MESSAGE, RECORD UNRECOGNIZED : %s", processData.getLine());
				log.error(message);
				throw new ParseException(message);
			}
			
			log.info("parsedRecord : {}", parsedRecord.getClass().getSimpleName());
			
			parsedRecord = ((RecordBase) parsedRecord).construct(processData);
			
			String line = lineCollectionParser.constructLine(parsedRecord);
			log.info("line {}", line);
			processData.setLine(line);
			response.setResponseData(processData);
			
		} catch (RecordUnrecognizedException e) {
			String message = String.format("ERROR PARSING MESSAGE, RECORD UNRECOGNIZED : %s", processData.getLine());
			log.error(message);
			//e.printStackTrace();
			throw new RecordUnrecognizedException(message);
		} catch (ParseException e) {
			String message = String.format("ERROR PARSING MESSAGE : %s", e.getMessage());
			log.error(message);
			//e.printStackTrace();
			throw new ParseException(message);
		}
		
		return response;
	}

}
