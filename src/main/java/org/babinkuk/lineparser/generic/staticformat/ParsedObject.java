package org.babinkuk.lineparser.generic.staticformat;

/**
 * interface koji definira funkcionalnost
 * koje moraju biti implementirane u sve ParsedObject objekte
 */
public interface ParsedObject extends Comparable<Object> {
	
	/**
	 * vraca vrijednost koju sadrzi
	 * npr. ParsedBigDec vraca BigDecimal
	 * 
	 * @return Object
	 */
	public Object getValue();
	
	/**
	 * implement toString()
	 */
}
