package org.babinkuk.lineparser.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(value = Include.NON_EMPTY)
public class ResponseData {
	
	private String sifra;
	private String akcija;
	private String datum;
	private String rezerva;
	private List<String> errors = new ArrayList<String>();

	public ResponseData() {
	}
	
	public ResponseData(String sifra, String akcija, String datum, String rezerva) {
		super();
		this.setSifra(sifra);
		this.setAkcija(akcija);
		this.setDatum(datum);
		this.setRezerva(rezerva);
	}

	public String getSifra() {
		return sifra;
	}

	public void setSifra(String sifra) {
		this.sifra = sifra;
	}

	public String getAkcija() {
		return akcija;
	}

	public void setAkcija(String akcija) {
		this.akcija = akcija;
	}

	public String getRezerva() {
		return rezerva;
	}

	public void setRezerva(String rezerva) {
		this.rezerva = rezerva;
	}

	public String getDatum() {
		return datum;
	}

	public void setDatum(String datum) {
		this.datum = datum;
	}

	public List<String> getErrors() {
		return errors;
	}

	public void setErrors(List<String> errors) {
		this.errors = errors;
	}

}
