package org.babinkuk.lineparser.generic.staticformat;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.babinkuk.lineparser.generic.staticformat.parsedobject.ParsedNull;
import org.babinkuk.lineparser.generic.staticformat.parsedobject.ParsedString;

/**
 * funkcionalost za parsiranje linije odredjenog formata u objekt ParsedRecord
 * te izradu iste linije iz ParsedRecord
 */
public class LineParser {
	
	private final Logger log = LogManager.getLogger(getClass());
	
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
				log.info(segment);
				
			} catch (IndexOutOfBoundsException e) {
				throw new ParseException(line, e, e.toString());
			}
			
			return op.parse(new ParsedString(segment), len);
		}

		protected String getSegment(String line) throws ParseException {
			try {
				//log.info(line);
				return line.substring(pos, pos + len);
			} catch (IndexOutOfBoundsException e) {
				throw new ParseException(line, e, e.toString());
			}	
		}
		
		protected String construct(ParsedObject parsedObject, String line) throws ParseException {
			
//			log.info("{}:{}:{}:{}:{}", parsedObject, line, pos, len, op.getClass());
//			if (line.length() < (pos + len)) {
//				log.info("< (pos + len)");
//				line = line.substring(0, pos + len);
//			}
			
			ParsedObject po = op.construct(parsedObject, len);
			
			String parsedString;
			parsedString = po.toString();
			
			return line += po.toString();
		}
		
		protected String construct(String parsedString, String line) throws ParseException {
//			if (line.length() < (pos + len)) {
//				line = line.substring(0, pos + len);
//			}
			
			return line += parsedString;
		}
	}
	
	ParsedRecord parsedRecordFactory = new DefaultParsedRecord();
	
	// ObjectParser za validaciju tipa linije
	ObjectParser lineIdFilter;
	
	// lista parsera za validaciju polja u liniji
	ArrayList<SegmentParser> segmentParserCollection;
	
	public ArrayList<SegmentParser> getSegmentParserCollection() {
		return segmentParserCollection;
	}

	public int getSegmentPositionCounter() {
		return segmentPositionCounter;
	}

	int segmentPositionCounter;
	
	// constructor koji prima ObjectParser za validaciju tipa linije
	public LineParser(ObjectParser lineIdFilter) {
		this.lineIdFilter = lineIdFilter;
		clearParsers();
	}
	
	/**
	 * dodavanje parsera za odredjeno polje, prima objectparser i duzinu polja
	 * 
	 * @param op
	 * @param len
	 */
	public void addParser(ObjectParser op, int len) {
		segmentParserCollection.add(new SegmentParser(op, segmentPositionCounter, len));
		segmentPositionCounter += len;
		log.info("addParser to segmentParserCollection {}:{}:{}", op.getClass().getSimpleName(), len, segmentPositionCounter);
	}
	
	/**
	 * dodavanje parsera za odredjeno polje, prima objectparser, poziciju i duzinu polja
	 * 
	 * @param op
	 * @param pos
	 * @param len
	 */
	public void addParser(ObjectParser op, int pos, int len) {
		log.info("addParser {}:{}:{}", op, pos, len);
		segmentParserCollection.add(new SegmentParser(op, pos, len));
	}
	
	/**
	 * dodavanje parsera za parsiranje polja
	 * prima Object[][] gdje unutarnje polje sadrzi objectparser i duzinu polja
	 * 
	 * @param Object[][] parserArray
	 */
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
	
	/**
	 * instanciranje ParsedRecord koji vraca parser
	 * 
	 * @return ParsedRecord
	 */
	public ParsedRecord getParsedRecordFactory() {
		return parsedRecordFactory;
	}
	
	/**
	 * postavljanje ParsedRecordFactory objekta sto je instanca ParsedRecord klase
	 * 
	 * @param parsedRecordFactory
	 */
	public void setParsedRecordFactory(ParsedRecord parsedRecordFactory) {
		this.parsedRecordFactory = parsedRecordFactory;
	}
	
	/**
	 * parsiranje linije, u slucaju greske baca ParseException
	 * 
	 * @param line
	 * @return ParsedRecord
	 * @throws ParseException
	 */
	public ParsedRecord parse(String line) throws ParseException {
		if (line == null) {
			throw new ParseException(line, null, "Invalid line argument");
		}
		
		ParsedObject[] parsedObjects = new ParsedObject[segmentParserCollection.size()];
		String[] sourceObjects = new String[segmentParserCollection.size()];
		ParseException[] exceptionObjects = new ParseException[segmentParserCollection.size()];
		
		for (SegmentParser sp : segmentParserCollection) {
			log.info("SegmentParser sp {}:{}:{}:{}", sp.getClass(), sp.op, sp.pos, sp.len);
		}
		
		int i = 0;
		for (SegmentParser sp : segmentParserCollection) {
			try {
				sourceObjects[i] = sp.getSegment(line);
				parsedObjects[i] = sp.parse(line);
				exceptionObjects[i] = null;
				
			} catch (ParseException pex) {
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
	
	/**
	 * izrada linije, u slucaju greske baca ParseException
	 * 
	 * @param parsedRecord
	 * @return String
	 * @throws ParseException
	 */
	public String construct(ParsedRecord parsedRecord) throws ParseException {
		
		String line = "";
		
		int i = 0;
		for (SegmentParser sp : segmentParserCollection) {
			
			ParsedObject parsedObject = parsedRecord.getParsedObject(i);
			String sourceObject = parsedRecord.getSourceObject(i);
			ParseException parseException = parsedRecord.getExceptionObject(i);
			
			try {
				if (parseException != null) {
					line = sp.construct(sourceObject, line);
				} else {
					line = sp.construct(parsedObject, line);
				}
				
				i++;
			} catch (Exception ex) {
				throw new ParseException("ParsedRecord in ParsedRecord (" + parsedRecord.createParsedRecordInstance().getClass().toString() + " at index " + i + " construction exception, source string : \"" + parsedRecord.toString() + "\"", ex);
			}
		}
		
		return line;
	}
	
	/**
	 * provjera da li zadana linija odgovara formatu
	 * 
	 * @param line
	 * @return ParsedObject
	 * @throws ParseException
	 */
	public ParsedObject checkLine(String line) throws ParseException {
		log.info("checkLine {}", line);
		return lineIdFilter.parse(new ParsedString(line), line.length());
	}
}
