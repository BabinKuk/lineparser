package org.babinkuk.lineparser.model;

import java.util.List;

import org.babinkuk.parser.generic.staticformat.LineParser;
import org.babinkuk.parser.generic.staticformat.ObjectParser;
import org.babinkuk.parser.generic.staticformat.ParseException;
import org.babinkuk.parser.generic.staticformat.ParsedObject;
import org.babinkuk.parser.generic.staticformat.ParsedRecord;
import org.babinkuk.parser.generic.staticformat.validator.RegexValidator;

public class RecordTypeOne extends RecordBase {

	public static enum FIELDS {
		SIFRA(3, new RegexValidator("100", null)),
		AKCIJA(2, new RegexValidator("50", null)),
		DATUM(10, ddmmyyyyM),
		REZERVA(10, cO);
		
		public int l;
		public ObjectParser op;
		
		private FIELDS(int l, ObjectParser op) {
			this.l = l;
			this.op = op;
		}
	}
	
	public RecordTypeOne() {
		super(FIELDS.values().length);
	}
	
	public ParsedRecord createParsedRecordInstance() {
		return new RecordTypeOne();
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
		
		LineParser lp = new LineParser(new RegexValidator("(100).{22}", null));
		lp.addParserArray(recordParsedArray);
		lp.setParsedRecordFactory(new RecordTypeOne());
		
		return lp;
	}
	
	public ResponseData processRecord() {

		ParsedObject sifra = getParsedObject(RecordTypeOne.FIELDS.SIFRA.ordinal());
		ParsedObject akc = getParsedObject(RecordTypeOne.FIELDS.AKCIJA.ordinal());
		ParsedObject datum = getParsedObject(RecordTypeOne.FIELDS.DATUM.ordinal());
		ParsedObject reserve = getParsedObject(RecordTypeOne.FIELDS.REZERVA.ordinal());
		
		List<String> errors = getErrors();
		
		ResponseData rd = new ResponseData(sifra.toString(), akc.toString(), datum.toString(), reserve.toString());
		
		if (!errors.isEmpty()) {
			rd.setErrors(errors);
		}
		
		return rd;
	}

}
