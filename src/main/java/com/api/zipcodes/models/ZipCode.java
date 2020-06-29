package com.api.zipcodes.models;

import java.util.List;

/**
 * 
 * @author cesar
 *
 */
public class ZipCode {

	private String zip_code;
	private String federal_entity;
	private List<Settlement> settlements;
	private String municipality;
	private String locality;
	
	public ZipCode() {
		super();
	}

	public ZipCode(String code) {
		super();
		this.zip_code = code;
	}
	
	public ZipCode(String zip_code, String locality, String federal_entity, List<Settlement> settlements,
			String municipality) {
		super();
		this.zip_code = zip_code;
		this.federal_entity = federal_entity;
		this.settlements = settlements;
		this.municipality = municipality;
		this.locality = locality;
	}
	
	public String getFederal_entity() {
		return federal_entity;
	}

	public void setFederal_entity(String federal_entity) {
		this.federal_entity = federal_entity;
	}

	public List<Settlement> getSettlements() {
		return settlements;
	}

	public void setSettlements(List<Settlement> settlements) {
		this.settlements = settlements;
	}

	public String getMunicipality() {
		return municipality;
	}

	public void setMunicipality(String municipality) {
		this.municipality = municipality;
	}

	public String getZip_code() {
		return zip_code;
	}

	public void setZip_code(String zip_code) {
		this.zip_code = zip_code;
	}

	public String getLocality() {
		return locality;
	}

	public void setLocality(String locality) {
		this.locality = locality;
	}
	
	
}
