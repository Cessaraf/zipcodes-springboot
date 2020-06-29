package com.api.zipcodes.models;

/**
 * 
 * @author cesar
 *
 */
public class FederalEntity {

	private String entity;
	private String name;
	
	public FederalEntity() {
		super();
	}

	public FederalEntity(String entity, String name) {
		super();
		this.entity = entity;
		this.name = name;
	}

	public String getEntity() {
		return entity;
	}

	public void setEntity(String entity) {
		this.entity = entity;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return this.entity + " " + this.name;
	} 
	
	
}
