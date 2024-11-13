package org.babinkuk.lineparser.generic.staticformat;

import java.util.ArrayList;

/**
 * funkcionalost za parsiranje vise vrsta linija odredjenog formata 
 * koje se nalaze u listi lineParserList, a koja sadrzi LineParser objekte
 */
public class LineCollectionParser {
	
	public static final int UNRECOGNIZED_LINE = 0;
	
	private ArrayList<LineParser> lineParserList;
	
	public LineCollectionParser() {
		lineParserList = new ArrayList<LineParser>();
		clearLineParserList();
	}
	
	public LineCollectionParser(ArrayList<LineParser> lineParserList) {
		this.lineParserList = lineParserList;
		clearLineParserList();
	}
	
	public void clearLineParserList() {
		lineParserList = new ArrayList<LineParser>();
	}
	
	public void addLineParser(LineParser lp) {
		lineParserList.add(lp);
	}
	
	public ArrayList<LineParser> getLineParserList() {
		return lineParserList;
	}

	public void setLineParserList(ArrayList<LineParser> lineParserList) {
		this.lineParserList = lineParserList;
	}

	/**
	 * parsiranje linije, u slucaju greske baca ParseException, RecordUnrecognizedException
	 * 
	 * @param line
	 * @return ParsedRecord
	 * @throws ParseException
	 * @throws RecordUnrecognizedException
	 */
	public ParsedRecord parseLine(String line) throws ParseException, RecordUnrecognizedException {
		
		try {
			for (LineParser lp : lineParserList) {
				try {
					lp.checkLine(line);
				} catch (ParseException pex) {
					continue;
				}
				return lp.parse(line);
			}
			
		} catch (Exception e) {
			throw new ParseException(e);
		}
		
		throw new RecordUnrecognizedException(line);
	}
	
	/**
	 * izrada linije, u slucaju greske baca ParseException, RecordUnrecognizedException
	 * 
	 * @param parsedRecord
	 * @return String
	 * @throws ParseException
	 * @throws RecordUnrecognizedException
	 */
	public String constructLine(ParsedRecord parsedRecord) throws ParseException, RecordUnrecognizedException {
		
		try {
			for (LineParser lp : lineParserList) {
				if (lp.getParsedRecordFactory().getClass().getCanonicalName().equals(parsedRecord.getClass().getCanonicalName())) {
					return lp.construct(parsedRecord);
				}
			}
		} catch (Exception ex) {
			throw new ParseException(ex);
		}
		
		throw new RecordUnrecognizedException();
	}
	
	@Override
	public String toString() {
		return "LineCollectionParser [lineParserList=" + lineParserList.size() + "]";
	}
}
