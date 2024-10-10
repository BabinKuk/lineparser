package org.babinkuk.lineparser.model;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import org.babinkuk.lineparser.generic.staticformat.LineCollectionParser;
import org.babinkuk.lineparser.generic.staticformat.LineParser;
import org.babinkuk.lineparser.generic.staticformat.ObjectParser;
import org.babinkuk.lineparser.generic.staticformat.ParseException;
import org.babinkuk.lineparser.generic.staticformat.ParsedObject;
import org.babinkuk.lineparser.generic.staticformat.ParsedRecord;
import org.babinkuk.lineparser.generic.staticformat.RecordUnrecognizedException;
import org.babinkuk.lineparser.generic.staticformat.parsedobject.ParsedCalendar;
import org.babinkuk.lineparser.generic.staticformat.parsedobject.ParsedString;
import org.babinkuk.lineparser.generic.staticformat.validator.RegexValidator;
import org.babinkuk.lineparser.model.RecordTypeOne.FIELDS;

public class RecordTypeTwo extends RecordBase {

	public static enum FIELDS {
		SIFRA(3, new RegexValidator("200", null)),
		AKCIJA(2, new RegexValidator("50", null)),
		DATUM(10, ddmmyyyyO),
		REZERVA(10, cO);
		
		public int l;
		public ObjectParser op;
		
		private FIELDS(int l, ObjectParser op) {
			this.l = l;
			this.op = op;
		}
	}
	
	public RecordTypeTwo() {
		super(FIELDS.values().length);
	}
	
	public ParsedRecord createParsedRecordInstance() {
		return new RecordTypeTwo();
	}
	
	@Override
	public ParseException getParsedException(int index) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public static LineParser getLineParser() {
		
		Object[][] recordParsedArray = new Object[FIELDS.values().length][2];
		for (FIELDS f : FIELDS.values()) {
			recordParsedArray[f.ordinal()] = new Object[] {f.op, f.l};
		}
		
		LineParser lp = new LineParser(new RegexValidator("(200).{22}", null));
		lp.addParserArray(recordParsedArray);
		lp.setParsedRecordFactory(new RecordTypeTwo());
		
		return lp;
	}

	public ProcessData parseRecord() {
		
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
	public ProcessData constructRecord(ProcessData processData) throws RecordUnrecognizedException, ParseException {

		RecordTypeTwo parsedRecord = new RecordTypeTwo();
		List<String> errors = getErrors();
		
		LineCollectionParser lineCollectionParser = new LineCollectionParser();
		lineCollectionParser.addLineParser(getLineParser());
		
		parsedRecord.getParsedObjects()[FIELDS.SIFRA.ordinal()] = new ParsedString(processData.getSifra());
		parsedRecord.getParsedObjects()[FIELDS.AKCIJA.ordinal()] = new ParsedString(processData.getAkcija());
		
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
		try {
			cal.setTime(sdf.parse(processData.getDatum()));
		} catch (java.text.ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		parsedRecord.getParsedObjects()[FIELDS.DATUM.ordinal()] = new ParsedCalendar(cal, "yyyy-MM-dd");
		parsedRecord.getParsedObjects()[FIELDS.REZERVA.ordinal()] = new ParsedString(processData.getRezerva());
		
		String line = lineCollectionParser.construct(parsedRecord);
		
		ProcessData rd = new ProcessData();
		rd.setLine(line);
		
		if (!errors.isEmpty()) {
			rd.setErrors(errors);
		}
		
		return rd;
	}
}
