package com.api.zipcodes.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author cesar
 *
 */
public class Municipality implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private List<String> zipCodes = new ArrayList<>();
	private String municipality;
	private String mnpio;
	private String locality;
	private FederalEntity cestado;	
	private List<Settlement> settlements = new ArrayList<>();	
	
	public Municipality() {
		super();
	}

	public Municipality(List<String> zipCodes, String municipality, String mnpio, FederalEntity cestado,
			List<Settlement> settlements, String locality) {
		super();
		this.zipCodes = zipCodes;
		this.municipality = municipality;
		this.mnpio = mnpio;
		this.cestado = cestado;
		this.settlements = settlements;
		this.locality = locality;
	}
	
	
	public List<String> getZipCodes() {
		return zipCodes;
	}
	public void setZipCodes(List<String> zipCodes) {
		this.zipCodes = zipCodes;
	}
	public String getMunicipality() {
		return municipality;
	}
	public void setMunicipality(String municipality) {
		this.municipality = municipality;
	}
	public String getMnpio() {
		return mnpio;
	}
	public void setMnpio(String mnpio) {
		this.mnpio = mnpio;
	}
	public FederalEntity getCestado() {
		return cestado;
	}
	public void setCestado(FederalEntity cestado) {
		this.cestado = cestado;
	}
	public List<Settlement> getSettlements() {
		return settlements;
	}
	public void setSettlements(List<Settlement> settlements) {
		this.settlements = settlements;
	}

	public String getLocality() {
		return locality;
	}

	public void setLocality(String locality) {
		this.locality = locality;
	}
	
	
}
