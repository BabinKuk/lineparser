package org.babinkuk.parser.generic.staticformat;

import java.util.ArrayList;

import org.babinkuk.parser.generic.staticformat.parsedobject.ParsedNull;
import org.babinkuk.parser.generic.staticformat.parsedobject.ParsedString;

/**
 * funkcionalost za parsiranje linije odredjenog formata u objekt ParsedRecord
 * te izradu iste nije iz ParsedRecord
 */
public class LineParser {
	
	protected class SegmentParser {
		ObjectParser op;
		int pos;
		int len;
		
		protected SegmentParser() {
		}

		protected SegmentParser(ObjectParser op, int pos, int len) {
			this.op = op;
			this.pos = pos;
			this.len = len;
		}
		
		protected ParsedObject parse(String line) throws ParseException {
			if (op == null) {
				throw new ParseException(line, null, "Invalida init data");
			}
			
			if (line == null) {
				throw new ParseException(line, null, "Invalida argument data");
			}
			
			String segment;
			try {
				segment = line.substring(pos, pos + len);
			} catch (IndexOutOfBoundsException e) {
				throw new ParseException(line, e, e.toString());
			}
			
				return op.parse(new ParsedString(segment), len);
		}

		protected String getSegment(String line) throws ParseException {
			try {
				return line.substring(pos, pos + len);
			} catch (IndexOutOfBoundsException e) {
				throw new ParseException(line, e, e.toString());
			}
					
		}
		
//		protected void construct(ParsedObject parsedObject, StringBuffer lineBuffer) throws ParseException {
//			
//		}
	}
	
	ParsedRecord parsedRecordFactory = new DefaultParsedRecord();
	
	// ObjectParser za validaciju tipa linije
	ObjectParser lineIdFilter;
	
	// lista parsera za validaciju polja u liniji
	ArrayList<SegmentParser> segmentParserCollection;
	int segmentPositionCounter;
	
	// constructor koji prima ObjectParser za validaciju tipa linije
	public LineParser(ObjectParser lineIdFilter) {
		this.lineIdFilter = lineIdFilter;
		
		clearParsers();
	}
	
	// dodavanje parsera za sljedeće polje, prima objectparser i duzinu polja
	public void addParser(ObjectParser op, int len) {
		segmentParserCollection.add(new SegmentParser(op, segmentPositionCounter, len));
		segmentPositionCounter += len;
	}
	
	// dodavanje parsera za odredjeno polje, prima objectparser, poziciju i duzinu polja
	public void addParser(ObjectParser op, int pos, int len) {
		segmentParserCollection.add(new SegmentParser(op, pos, len));
	}
	
	// dodavanje parsera za parsiranje polja
	// prima Object[][] gdje unutarnje polje sadrzi objectparser i duzinu polja
	public void addParserArray(Object[][] parserArray) {
		if (parserArray == null) {
			return;
		}
		
		for (Object[] parser : parserArray) {
			if (parser == null) {
				continue;
			}
			
			addParser((ObjectParser) parser[0], (Integer) parser[1]);
		}
	}
	
	public void clearParsers() {
		segmentParserCollection = new ArrayList<SegmentParser>();
		segmentPositionCounter = 0;
	}
	
	// instanciranje ParsedRecord koji vraca parser
	public ParsedRecord getParsedRecordFactory() {
		return parsedRecordFactory;
	}
	
	// postavljanje ParsedRecordFactory objekta sto je instanca ParsedRecord klase
	public void setParsedRecordFactory(ParsedRecord parsedRecordFactory) {
		this.parsedRecordFactory = parsedRecordFactory;
	}
	
	// parsiranje linije, u slucaju greske baca ParseException
	public ParsedRecord parse(String line) throws ParseException {
		
		if (line == null) {
			throw new ParseException(line, null, "Invalid line argument");
		}
		
		ParsedObject[] parsedObjects = new ParsedObject[segmentParserCollection.size()];
		String[] sourceObjects = new String[segmentParserCollection.size()];
		ParseException[] exceptionObjects = new ParseException[segmentParserCollection.size()];
		
		boolean state = true;
		int i = 0;
		for (SegmentParser sp : segmentParserCollection) {
			try {
				sourceObjects[i] = sp.getSegment(line);
				parsedObjects[i] = sp.parse(line);
				exceptionObjects[i] = null;
				
			} catch (ParseException pex) {
				state = false;
				parsedObjects[i] = new ParsedNull();
				exceptionObjects[i] = pex;
			}
			i++;
		}
		
		ParsedRecord parsedRecord = parsedRecordFactory.createParsedRecordInstance();
		
		parsedRecord.setParsedObjects(parsedObjects);
		parsedRecord.setSourceObjects(sourceObjects);
		parsedRecord.setExceptionObjects(exceptionObjects);
		
		return parsedRecord;
	}
	
//	// izrada linije, u slucaju greske baca ParseException
//	public String construct(ParsedRecord parsedRecord) throws ParseException {
//		
//		StringBuffer lineBuffer = new StringBuffer();
//		
//		int i = 0;
//		for (SegmentParser sp : segmentParserCollection) {
//			
//			ParsedObject parsedObject = parsedRecord.getParsedObject(i) ;
//			String sourceObject = parsedRecord.getSourceObject(i);
//			ParseException parseEeception = parsedRecord.getExceptionObject(i);
//			
//			try {
//				if (parseEeception != null) {
//					sp.construct(sourceObject, lineBuffer);
//				} else {
//					sp.construct(parsedObject, lineBuffer);
//				}
//				
//				i++;
//			} catch (Exception ex) {
//				throw new ParseException("ParsedRecord in ParsedRecord (" + parsedRecord.createParsedRecordInstance().getClass().toString() + " at index " + i + " construction exception, source string : \"" + parsedRecord.toString() + "\"", ex);
//			}
//		}
//		
//		return lineBuffer;
//	}
	
	// provjera da li zadana linija odgovara formatu
	public ParsedObject checkLine(String line) throws ParseException {
		return lineIdFilter.parse(new ParsedString(line), line.length());
	}
}
