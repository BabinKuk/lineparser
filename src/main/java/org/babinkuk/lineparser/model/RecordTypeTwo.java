package org.babinkuk.lineparser.model;

import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.babinkuk.lineparser.generic.staticformat.LineParser;
import org.babinkuk.lineparser.generic.staticformat.ObjectParser;
import org.babinkuk.lineparser.generic.staticformat.ParseException;
import org.babinkuk.lineparser.generic.staticformat.ParsedObject;
import org.babinkuk.lineparser.generic.staticformat.ParsedRecord;
import org.babinkuk.lineparser.generic.staticformat.RecordUnrecognizedException;
import org.babinkuk.lineparser.generic.staticformat.validator.RegexValidator;

public class RecordTypeTwo extends RecordBase {

	private final Logger log = LogManager.getLogger(getClass());
	
	public static List<FieldMetaData> fieldMetaDataList = new ArrayList<FieldMetaData>();
	
	public static enum FIELDS {
		SIFRA(3, new RegexValidator("200", null)),
		AKCIJA(2, new RegexValidator("50", null)),
		DATUM(10, ddmmyyyyO),
		REZERVA(10, cO);
		
		public int length;
		public ObjectParser objectParser;
		
		private FIELDS(int l, ObjectParser op) {
			this.length = l;
			this.objectParser = op;
			
			fieldMetaDataList.add(new FieldMetaData(this.name(), this.length, this.objectParser));
		}
	}
	
	@Override
	public List<FieldMetaData> getFeldMetaData() {
		return fieldMetaDataList;
	}
	
	public RecordTypeTwo() {
		super(FIELDS.values().length);
	}
	
	public ParsedRecord createParsedRecordInstance() {
		return new RecordTypeTwo();
	}
	
	@Override
	public ParseException getParsedException(int index) {
		return null;
	}
	
	public static LineParser getLineParser() {
		
		Object[][] recordParsedArray = new Object[FIELDS.values().length][2];
		for (FIELDS f : FIELDS.values()) {
			recordParsedArray[f.ordinal()] = new Object[] {f.objectParser, f.length};
		}
		
		LineParser lp = new LineParser(new RegexValidator("(200).{22}", null));
		lp.addParserArray(recordParsedArray);
		lp.setParsedRecordFactory(new RecordTypeTwo());
		
		return lp;
	}

	public ProcessData parse() {
		
		ParsedObject sifra = getParsedObject(RecordTypeTwo.FIELDS.SIFRA.ordinal());
		ParsedObject akc = getParsedObject(RecordTypeTwo.FIELDS.AKCIJA.ordinal());
		ParsedObject datum = getParsedObject(RecordTypeTwo.FIELDS.DATUM.ordinal());
		ParsedObject reserve = getParsedObject(RecordTypeTwo.FIELDS.REZERVA.ordinal());
		
		List<String> errors = getErrors();
		
		ProcessData rd = new ProcessData(sifra.toString(), akc.toString(), datum.toString(), reserve.toString());
		
		if (!errors.isEmpty()) {
			rd.setErrors(errors);
		}
		
		return rd;
	}

	@Override
	public RecordTypeTwo construct(ProcessData processData) throws RecordUnrecognizedException, ParseException {

		List<String> errors = getErrors();
		
		RecordTypeTwo parsedRecord = new RecordTypeTwo();
		
		try {
			RecordTypeTwo record = (RecordTypeTwo) constructRecord(parsedRecord, processData);
			log.info("record {}", record);
			
		} catch (java.text.ParseException e) {
			log.error("error {}", e.getMessage());
			throw new ParseException(e.getMessage());
		}
		catch (ParseException e) {
			// TODO: handle exception
			log.error("ParseException {}", e.getMessage());
			throw new ParseException(e.getMessage());
		}
		catch (Exception e) {
			// TODO: handle exception
			log.error("Exception {}", e.getMessage());
			throw new ParseException(e.getMessage());
		}
		
		return parsedRecord;
	}
}
