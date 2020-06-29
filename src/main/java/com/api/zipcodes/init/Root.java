package com.api.zipcodes.init;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name = "NewDataSet")
@XmlAccessorType(XmlAccessType.FIELD)
public class Root {

    @XmlElement(name="table")
    private List<Table> tables = new ArrayList<>();
    
	public List<Table> getTables() {
		return tables;
	}

	public void setTables(List<Table> animals) {
		this.tables = animals;
	}

	@Override
	public String toString() {
		return "Root [tables=" + tables + "]";
	}
    
}
