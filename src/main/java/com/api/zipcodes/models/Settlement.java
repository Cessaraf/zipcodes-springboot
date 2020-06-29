package com.api.zipcodes.models;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 
 * @author cesar
 *
 */
public class Settlement implements Serializable {

	private static final long serialVersionUID = 1L;

	@JsonIgnore
	private String id_settlement;
	private String name;
	private String zone_type;
	private String settlement_type;
	@JsonIgnore
	private String zipCode;
	
	public Settlement() {
		super();
	}

	public Settlement(String id_settlement, String name, String zone_type, String settlement_type, String zipCode) {
		super();
		this.id_settlement = id_settlement;
		this.name = name;
		this.zone_type = zone_type;
		this.settlement_type = settlement_type;
		this.zipCode = zipCode;
	}

	public String getId_settlement() {
		return id_settlement;
	}

	public void setId_settlement(String id_settlement) {
		this.id_settlement = id_settlement;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getZone_type() {
		return zone_type;
	}

	public void setZone_type(String zone_type) {
		this.zone_type = zone_type;
	}

	public String getSettlement_type() {
		return settlement_type;
	}

	public void setSettlement_type(String settlement_type) {
		this.settlement_type = settlement_type;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	
	
}
