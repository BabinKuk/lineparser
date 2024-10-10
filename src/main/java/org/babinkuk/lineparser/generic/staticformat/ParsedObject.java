package org.babinkuk.lineparser.generic.staticformat;

/**
 * interface koji definira funkcionalnost
 * koje moraju biti implementirane u sve ParsedObject objekte
 */
public interface ParsedObject extends Comparable {
	
	/**
	 * vraca vrijednost koju sadrzi
	 * npr. ParsedBigDec vraca BigDecimal
	 * @return
	 */
	public Object getValue();
	
	/**
	 * implement toString()
	 */
}
