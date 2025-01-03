package org.babinkuk.lineparser.generic.staticformat;

/**
 * Definira funkconalnost koju moraju implementirati svi parseri za odredjene tipove polja</br>
 * Parseri koji mogu embedati druge parsere pri parsiranju prvo njih pozivaju,
 * a pri rekonstrukciji polja ih zovu nakon vlastite obrade  
 */
public interface ObjectParser {
	
	/*
	 * metoda za parsiranje objekta u ParsedObject
	 * prima ParseObject za parsiranje i duzinu izvornih podataka
	 */
	public ParsedObject parse(ParsedObject input, int sourceLength) throws ParseException;
	
	/*
	 * metoda za izradu objekta ParsedObject
	 * prima ParseObject za rekonstrukcju i duzinu izvornih podataka
	 */
	public ParsedObject construct(ParsedObject output, int sourceLength) throws ParseException;
}
