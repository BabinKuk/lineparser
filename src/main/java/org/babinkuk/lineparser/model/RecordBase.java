package org.babinkuk.lineparser.model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.babinkuk.lineparser.generic.staticformat.*;
import org.babinkuk.lineparser.generic.staticformat.formatter.StringTrimmer;
import org.babinkuk.lineparser.generic.staticformat.parsedobject.ParsedCalendar;
import org.babinkuk.lineparser.generic.staticformat.parsedobject.ParsedString;
import org.babinkuk.lineparser.generic.staticformat.parser.CalendarParser;
import org.babinkuk.lineparser.generic.staticformat.validator.RegexValidator;

public abstract class RecordBase extends ParsedRecordAbstract {
	
	private final Logger log = LogManager.getLogger(getClass());
	
	public static final ObjectParser cM = new StringTrimmer(new RegexValidator("[^\\s]+.*", null)); // mandatory string
	public static final ObjectParser cO = new StringTrimmer(new RegexValidator(".*", null)); // optional string
	public static final ObjectParser ddmmyyyyM = new CalendarParser(new RegexValidator("[0-9]{4}-[0-9]{2}-[0-9]{2}", null), "yyyy-MM-dd", CalendarParser.PROCESS_0_EXCEPTION); // mandatory date
	public static final ObjectParser ddmmyyyyO = new CalendarParser(new RegexValidator("[0-9]{4}-[0-9]{2}-[0-9]{2}", null), "yyyy-MM-dd", CalendarParser.PROCESS_0_NULL); // optional date
	
	public abstract List<FieldMetaData> getFeldMetaData();
	public abstract ProcessData parse();
	public abstract RecordBase construct(ProcessData processData) throws RecordUnrecognizedException, ParseException;
	
	public RecordBase(int fieldNum) {
		super(fieldNum);
	}
	
	public List<String> getErrors() {
		List<String> errors = new ArrayList<String>();
		int index = 0;
		
		for (ParseException paex : getExceptionObjects()) {
			if (paex != null) {
				FieldMetaData f = getFeldMetaData().get(index);
				String error = f.fieldName + " : " + paex.getMessage();
				errors.add(error);
			}
			++index;
		}
		
		return errors;
	}
	
	protected RecordBase constructRecord(RecordBase record, ProcessData processData) throws RecordUnrecognizedException, ParseException, java.text.ParseException {
		
		int index = 0;
		
		// create record and validate fields
		for (FieldMetaData f : getFeldMetaData()) {
			log.info("f {} : {} : {} : {}", index, f.fieldName, f.length, f.objectParser);
			
			switch (f.fieldName) {
			case "SIFRA": 
				record.getParsedObjects()[index] = new ParsedString(processData.getSifra());
				// validation
				ParsedObject sifra = f.objectParser.parse(record.getParsedObjects()[index], f.length);
				break;
			case "AKCIJA": 
				record.getParsedObjects()[index] = new ParsedString(processData.getAkcija());
				// validation
				ParsedObject akc = f.objectParser.parse(record.getParsedObjects()[index], f.length);
				break;			
			case "DATUM": 
				Calendar cal = Calendar.getInstance();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
				cal.setTime(sdf.parse(processData.getDatum()));
				// validation
				record.getParsedObjects()[index] = new ParsedCalendar(cal, "yyyy-MM-dd");
				break;
			case "REZERVA": 
				String reserve = StringUtils.isNotBlank(processData.getRezerva()) ? "" : processData.getRezerva();
				record.getParsedObjects()[index] = new ParsedString(reserve);
				break;
			default:
				throw new IllegalArgumentException("Unexpected value: " + f.fieldName);
			}
			log.info("constructRecord {}", record.getParsedObjects()[index].getValue());
			
			index++;
		}
		
		return record;
	}
}
